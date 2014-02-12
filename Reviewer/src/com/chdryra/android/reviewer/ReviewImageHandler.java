package com.chdryra.android.reviewer;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageButton;

public class ReviewImageHandler extends ImageHandler{

	private static final String TAG = "ImageHelper";

	private static HashMap<String, ReviewImageHandler> sReviewImageHandlers = new HashMap<String, ReviewImageHandler>();
	
	private Review mReview;
	private Bitmap mThumbnail;
	
	public static ReviewImageHandler getInstance(Review mReview) {
		if(!sReviewImageHandlers.containsKey(mReview.getID().toString()))
			sReviewImageHandlers.put(mReview.getID().toString(), new ReviewImageHandler(mReview));
		
		return sReviewImageHandlers.get(mReview.getID().toString());
	}
	
	private ReviewImageHandler(Review review) {
		mReview = review;
	}
			
	public void deleteImage() {
		mReview.setImage(null);
		setImageFilePath(null);
	}
	
	public void createNewImageFile() {
		String timeStamp = SimpleDateFormat.getDateInstance().format(new Date());
	    String imageFileName = mReview.getSubject() + "_" + timeStamp;
	    String path = null;
	    
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            path = Environment.getExternalStorageDirectory().getName() + File.separatorChar + imageFileName + ".jpg";
            File file = new File(path);
            try {
                if( !file.exists() ) {
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                }
            } catch (IOException e) {
                Log.e(TAG, "Could not create file.", e);
            }
            Log.i(TAG, path);
        } else {
        	Log.i(TAG, "No external storage");
        }
        
        setImageFilePath(path);
	}
	
	public void setReviewImage(Context context, ImageButton thumbnailView) {
		int maxWidth = (int)context.getResources().getDimension(R.dimen.imageMaxWidth);				
		int maxHeight = (int)context.getResources().getDimension(R.dimen.imageMaxHeight);
		
		BitmapLoaderTask loader = new BitmapLoaderTask(thumbnailView);
		loader.execute(maxWidth, maxHeight);
	}

	public Bitmap getThumbnail() {
		return mThumbnail;
	}
	
	private class BitmapLoaderTask extends AsyncTask<Integer, Void, Bitmap> {		
		private ImageButton mThumbnailView;
		
		public BitmapLoaderTask(ImageButton thumbnailView) {
			mThumbnailView = thumbnailView;
		}
		
		@Override
	    protected Bitmap doInBackground(Integer... params) {
	        int maxWidth = params[0];
	        int maxHeight = params[1];
	        Bitmap bitmap = getBitmap(maxWidth, maxHeight);
	        
	        maxWidth = mThumbnailView.getLayoutParams().width;				
			maxHeight = mThumbnailView.getLayoutParams().height;
			
			mThumbnail = ImageHandler.resizeImage(bitmap, maxWidth, maxHeight);	        		        
			return bitmap;
	    }
		
		@Override
		protected void onPostExecute(Bitmap bitmap) {
			mReview.setImage(bitmap);
			if(bitmap == null)
				mThumbnailView.setImageResource(R.drawable.ic_menu_camera);
			else
				mThumbnailView.setImageBitmap(mThumbnail);
		}	
	}
}
