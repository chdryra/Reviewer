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

public class ReviewImageHelper extends ImageHelper{

	private static final String TAG = "ImageHelper";
	private static HashMap<String, ReviewImageHelper> sReviewImageHelpers = new HashMap<String, ReviewImageHelper>();
	private static final String IMAGE_DIRECTORY = "Reviewer";
	private static final String ERROR_NO_STORAGE_MESSAGE = "No storage available!";
	
	private long fileCounter = 0;
	private Review mReview;
	
	public static ReviewImageHelper getInstance(Review mReview) {
		if(!sReviewImageHelpers.containsKey(mReview.getID().toString()))
			sReviewImageHelpers.put(mReview.getID().toString(), new ReviewImageHelper(mReview));
		
		return sReviewImageHelpers.get(mReview.getID().toString());
	}
	
	private ReviewImageHelper(Review review) {
		mReview = review;
	}
			
	public void deleteImage() {
		mReview.setImage(null);
		setImageFilePath(null);
	}
	
	public void createNewImageFile() throws IOException{
	    String imageFileName = mReview.getSubject() + "_" + fileCounter++;
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
			mReview.setImage(bitmap);
			if(bitmap == null) {
				mThumbnailView.setImageResource(R.drawable.ic_menu_camera);
			}
			else {
				mThumbnailView.setImageBitmap(bitmap);
			}
		}	
	}
}
