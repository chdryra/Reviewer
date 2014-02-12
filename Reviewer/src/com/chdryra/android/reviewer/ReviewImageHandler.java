package com.chdryra.android.reviewer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

public class ReviewImageHandler extends ImageHandler{

	private static final String TAG = "ImageHelper";

	private static HashMap<String, ReviewImageHandler> sReviewImageHandlers = new HashMap<String, ReviewImageHandler>();
	
	private Review mReview;
	
	public static ReviewImageHandler getInstance(Review mReview) {
		if(!sReviewImageHandlers.containsKey(mReview.getID().toString()))
			sReviewImageHandlers.put(mReview.getID().toString(), new ReviewImageHandler(mReview));
		
		return sReviewImageHandlers.get(mReview.getID().toString());
	}
	
	private ReviewImageHandler(Review review) {
		mReview = review;
		createNewImageFile();
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
	
	public void setReviewImage(Context context) {
		int maxWidth = (int)context.getResources().getDimension(R.dimen.imageMaxWidth);				
		int maxHeight = (int)context.getResources().getDimension(R.dimen.imageMaxHeight);
		
		mReview.setImage(getBitmap(maxWidth, maxHeight));
	}
}
