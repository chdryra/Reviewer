package com.chdryra.android.reviewer;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import com.chdryra.android.mygenerallibrary.ImageHelper;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageButton;

public class HelperReviewImage extends ImageHelper{

	private static final String TAG = "ImageHelper";
	private static HashMap<RDId, HelperReviewImage> sHelperReviewImages = new HashMap<RDId, HelperReviewImage>();
	private static final String IMAGE_DIRECTORY = "Reviewer";
	private static final String ERROR_NO_STORAGE_MESSAGE = "No storage available!";
	
	private long fileCounter = 0;
	
	private Controller mController = Controller.getInstance();
	private RDId mReviewID;
	
	public static HelperReviewImage getInstance(RDId id) {
		if(!sHelperReviewImages.containsKey(id))
			sHelperReviewImages.put(id, new HelperReviewImage(id));
		
		return sHelperReviewImages.get(id);
	}
	
	private HelperReviewImage(RDId id) {
		mReviewID = id;
	}
			
	public void deleteImage() {
		mController.setImageBitmap(mReviewID, null);
		setImageFilePath(null);
	}
	
	public void createNewImageFile() throws IOException{
	    String imageFileName = mController.getTitle(mReviewID) + "_" + fileCounter++;
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
			mController.setImageBitmap(mReviewID, bitmap);
			mController.setImageLatLng(mReviewID, getLatLngFromEXIF());
			
			if(bitmap == null)
				mThumbnailView.setImageResource(R.drawable.ic_menu_camera);
			else
				mThumbnailView.setImageBitmap(bitmap);
		}	
	}
}
