/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

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

import com.chdryra.android.mygenerallibrary.FunctionPointer;
import com.chdryra.android.mygenerallibrary.ImageHelper;

public class HelperReviewImage extends ImageHelper{

	private static final String TAG = "ImageHelper";
	private static final HashMap<String, HelperReviewImage> sHelperReviewImages = new HashMap<String, HelperReviewImage>();
	private static final String IMAGE_DIRECTORY = "Reviewer";
	private static final String ERROR_NO_STORAGE_MESSAGE = "No storage available!";
	
	private long fileCounter = 0;
	
	private final ControllerReviewNode mController;
	
	public static HelperReviewImage getInstance(ControllerReviewNode controller) {
		if(!sHelperReviewImages.containsKey(controller.getID()))
			sHelperReviewImages.put(controller.getID(), new HelperReviewImage(controller));

		return sHelperReviewImages.get(controller.getID());
	}

	private HelperReviewImage(ControllerReviewNode controller) {
		mController = controller;
	}

    boolean createNewImageFile() throws IOException{
	    String imageFileName = mController.getSubject() + "_" + fileCounter++;
	    String path;
	    boolean success;
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
        	File dcimDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
            File reviewerDir = new File(dcimDir, IMAGE_DIRECTORY);
            File file = new File(reviewerDir, imageFileName + ".jpg");
            if(file.exists()) {
            	return createNewImageFile();
            }
            path = file.toString();
            setImageFilePath(path);
            success = createImageFile();
        } else {
        	throw new IOException(ERROR_NO_STORAGE_MESSAGE);
        }

        return success;
	}
	
	public void addReviewImage(Context context, GVImageList imageList, FunctionPointer<Void> updateUI) {
		int maxWidth = (int)context.getResources().getDimension(R.dimen.imageMaxWidth);				
		int maxHeight = (int)context.getResources().getDimension(R.dimen.imageMaxHeight);
		
		BitmapLoaderTask loader = new BitmapLoaderTask(imageList, updateUI);
		loader.execute(maxWidth, maxHeight);
	}

	private class BitmapLoaderTask extends AsyncTask<Integer, Void, Bitmap> {		
		private final GVImageList mImageList;
		private final FunctionPointer<Void> mUpdateUI;
		
		public BitmapLoaderTask(GVImageList imageList, FunctionPointer<Void> updateUI) {
			mImageList = imageList;
			mUpdateUI = updateUI;
		}
		
		@Override
	    protected Bitmap doInBackground(Integer... params) {
			return getBitmap(params[0], params[1]);
	    }
		
		@Override
		protected void onPostExecute(Bitmap bitmap) {
			mImageList.add(bitmap, getLatLngFromEXIF());
			mUpdateUI.execute(null);
		}	
	}
	
	public Intent getImageChooserIntents(Activity activity) {
		//Set up image file
        try {
            if(!createNewImageFile()) {
                //TODO handle this better
                Log.i(TAG, "Problems creating image file...");
                return null;
            }
		} catch (IOException e) {
			Log.e(TAG, e.getMessage(), e);
			e.printStackTrace();
			return null;
		}

		File imageFile = new File(getImageFilePath());
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
		chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[cameraIntents.size()]));

		return chooserIntent;
	}
	
	public boolean processOnActivityResult(Activity activity, int resultCode, Intent data) {
		//Returns true if bitmap exists.
		switch (resultCode) {
		case Activity.RESULT_OK:
			final boolean isCamera;
	        if(data == null)
	            isCamera = true;
	        else {
	        	final String action = data.getAction();
                isCamera = action != null && action.equals(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
	        }
	        
	        if(!isCamera) {
	        	Uri uri = data.getData();
	        	String path = ImageHelper.getRealPathFromURI(activity, uri);
	        	setImageFilePath(path);
	        }
	     
	        if(bitmapExists())
	        {
	        	if(isCamera) {
		    		Uri imageUri = Uri.fromFile(new File(getImageFilePath()));
		        	activity.sendBroadcast(
		        			new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, imageUri));
	        	}
	        	
	        	return true;
	        }
					
	        return false;

		case Activity.RESULT_CANCELED:
			if(!bitmapExists())
				deleteImageFile();
			
			return false;
			
		default:
			return false;
		}
	}
}
