package com.chdryra.android.reviewer;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Environment;
import android.util.Log;

public class ImageCaptureAndProcessingUtils {

	private static final String TAG = "ImageCaptureAndProcessingUtils";
	private static enum PhotoOrientation{PORTRAIT, LANDSCAPE, SQUARE};
	
	public static PhotoOrientation getOrientation(Bitmap image) {
		int width = image.getWidth();
		int height = image.getHeight();
		
		if(width > height)
			return PhotoOrientation.LANDSCAPE;
		
		if(height > width)
			return PhotoOrientation.PORTRAIT;
		
		return PhotoOrientation.SQUARE;
	}
	
	public static File createImageFile(Context context, String subject) throws IOException {
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    String imageFileName = subject + "_" + timeStamp;
	    
	    File photoFile = null;
	    String storageState = Environment.getExternalStorageState();
        if(storageState.equals(Environment.MEDIA_MOUNTED)) {

            String path = Environment.getExternalStorageDirectory().getName() + File.separatorChar + "Android/data/" + context.getPackageName() + "/files/photos" + imageFileName + ".jpg";

            photoFile = new File(path);
            try {
                if( !photoFile.exists() ) {
                    photoFile.getParentFile().mkdirs();
                    photoFile.createNewFile();
                }

            } catch (IOException e) {
                Log.e(TAG, "Could not create file.", e);
            }
            Log.i(TAG, path);
        } else {
        	Log.i(TAG, "No external storage");
        }
        
        return photoFile;
	}
	
	public static Bitmap resizeImage(Bitmap image, int maxWidth, int maxHeight) {
		
		int width = image.getWidth(); 
		int height = image.getHeight(); 
		float ratio = (float)width/(float)height;
		
		int newWidth = 0;
		int newHeight= 0;
		
		PhotoOrientation orientation = getOrientation(image);
		switch (orientation) {
		case PORTRAIT:
			newHeight = maxHeight;
			newWidth = (int)(ratio * (float)newHeight);
			break;
		case LANDSCAPE:
			newWidth = maxWidth;
			newHeight = (int)((float)newWidth/ratio);
			break;
		default:
			newWidth = newHeight = Math.min(maxWidth,maxHeight);
			break;
		}
		
		float scaleWidth = ((float) newWidth) / width; 
		float scaleHeight = ((float) newHeight) / height; 

		Matrix matrix = new Matrix(); 
		matrix.postScale(scaleWidth, scaleHeight); 
		Bitmap resizedBitmap = Bitmap.createBitmap(image, 0, 0, width, height, matrix, false); 

		return resizedBitmap; 
	}
	
}
