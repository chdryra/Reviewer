package com.chdryra.android.reviewer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageButton;

import com.chdryra.android.mygenerallibrary.ImageHelper;

public class HelperReviewImage extends ImageHelper{

	private static final String TAG = "ImageHelper";
	private static HashMap<String, HelperReviewImage> sHelperReviewImages = new HashMap<String, HelperReviewImage>();
	private static final String IMAGE_DIRECTORY = "Reviewer";
	private static final String ERROR_NO_STORAGE_MESSAGE = "No storage available!";
	
	private long fileCounter = 0;
	
	private ControllerReviewNode mController;
	
	public static HelperReviewImage getInstance(ControllerReviewNode controller) {
		if(!sHelperReviewImages.containsKey(controller.getID()))
			sHelperReviewImages.put(controller.getID(), new HelperReviewImage(controller));

		return sHelperReviewImages.get(controller.getID());
	}

	private HelperReviewImage(ControllerReviewNode controller) {
		mController = controller;
	}
	
	public void deleteImage() {
		mController.setImageBitmap(null);
		setImageFilePath(null);
	}
	
	public void createNewImageFile() throws IOException{
	    String imageFileName = mController.getTitle() + "_" + fileCounter++;
	    String path = null;
	    
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
        	File dcimDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
            File reviewerDir = new File(dcimDir, IMAGE_DIRECTORY);
            File file = new File(reviewerDir, imageFileName + ".jpg");
            if(file.exists()) {
            	createNewImageFile();
            	return;
            }
            path = file.toString();
            setImageFilePath(path);
            createImageFile();
        } else {
        	throw new IOException(ERROR_NO_STORAGE_MESSAGE);
        }
        
        Log.i(TAG, "Created file " + path);
	}
	
	public void setReviewImage(Context context, ImageButton thumbnailView) {
		int maxWidth = (int)context.getResources().getDimension(R.dimen.imageMaxWidth);				
		int maxHeight = (int)context.getResources().getDimension(R.dimen.imageMaxHeight);
		
		BitmapLoaderTask loader = new BitmapLoaderTask(thumbnailView);
		loader.execute(maxWidth, maxHeight);
	}

	private class BitmapLoaderTask extends AsyncTask<Integer, Void, Bitmap> {		
		private ImageButton mThumbnailView;
		
		public BitmapLoaderTask(ImageButton thumbnailView) {
			mThumbnailView = thumbnailView;
		}
		
		@Override
	    protected Bitmap doInBackground(Integer... params) {
			return getBitmap(params[0], params[1]);
	    }
		
		@Override
		protected void onPostExecute(Bitmap bitmap) {
			mController.setImageBitmap(bitmap);
			mController.setImageLatLng(getLatLngFromEXIF());
			
			if(bitmap == null)
				mThumbnailView.setImageResource(R.drawable.ic_menu_camera);
			else
				mThumbnailView.setImageBitmap(bitmap);
		}	
	}
	
	public static Intent getImageChooserIntents(Activity activity, HelperReviewImage helperReviewImageInstance) {
		//Set up image file
		try {
			helperReviewImageInstance.createNewImageFile();
		} catch (IOException e) {
			Log.e(TAG, e.getMessage(), e);
			e.printStackTrace();
			return null;
		}
		File imageFile = new File(helperReviewImageInstance.getImageFilePath());
		Uri imageUri = Uri.fromFile(imageFile);		
		
	    //Create intents
		final List<Intent> cameraIntents = new ArrayList<Intent>();
		final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		final PackageManager packageManager = activity.getPackageManager();
		final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
		for(ResolveInfo res : listCam) {
	        final String packageName = res.activityInfo.packageName;
	        final Intent intent = new Intent(captureIntent);
	        intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
	        intent.setPackage(packageName);
	        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
	        cameraIntents.add(intent);
	    }
        
		final Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		final Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Source");		
		chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[]{}));
		
		return chooserIntent;
	}
}
