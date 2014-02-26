package com.chdryra.android.reviewer;

import java.io.File;
import java.io.IOException;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

public class ImageHandler {
	private static final String TAG = "ImageHandler";
	private static final String ERROR_CREATING_FILE_MESSAGE = "Error creating file!";
	public static enum PhotoOrientation{PORTRAIT, LANDSCAPE, SQUARE};
	
	private String mImageFilePath;
	private ExifInterface mEXIF;
	
	public ImageHandler() {
	}
	
	public ImageHandler(String imageFilePath) {
		mImageFilePath = imageFilePath;
		getEXIF();
	}
	
	public String getImageFilePath() {
		return mImageFilePath;
	}

	public void setImageFilePath(String imageFilePath) {
		mImageFilePath = imageFilePath;
		mEXIF = null;
		getEXIF();
	}

	public void createImageFile() throws IOException{
		File file = new File(mImageFilePath);
		try {
            if(!file.exists() && mImageFilePath != null) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
        } catch (IOException e) {
            throw new IOException(ERROR_CREATING_FILE_MESSAGE, e);
        }
	}

	public void deleteImageFile() {
		File file = new File(mImageFilePath);
        if(file.exists()) {
            file.delete();
        }
        mImageFilePath = null;
        mEXIF = null;
	}
	
	public ExifInterface getEXIF() {
		if(mImageFilePath != null) {
			if (mEXIF == null) {
				try {
					mEXIF = new ExifInterface(mImageFilePath);
				} catch (IOException e) {
					Log.i(TAG, "No EXIF found in " + mImageFilePath);
				}
			}	
		} else 
			mEXIF = null;
		
		return mEXIF;
	}
	
	private BitmapFactory.Options getInDecodeBoundsOptions() {
		BitmapFactory.Options options = new BitmapFactory.Options();			
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(mImageFilePath, options);
		
		return options;
	}
	
	public Bitmap getBitmap(int maxWidth, int maxHeight) {
		BitmapFactory.Options options = getInDecodeBoundsOptions();
		options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight);			
		options.inJustDecodeBounds = false;
		
		Bitmap bitmap = BitmapFactory.decodeFile(mImageFilePath, options);
		bitmap = rotateBitmapUsingExif(mImageFilePath, bitmap);
		return bitmap;
	}

	public Bitmap getBitmap() {
		Bitmap bitmap = BitmapFactory.decodeFile(mImageFilePath);
		bitmap = rotateBitmapUsingExif(mImageFilePath, bitmap);
		return bitmap;
	}

	private int calculateInSampleSize( BitmapFactory.Options options, int reqWidth, int reqHeight) {
	    final int height = options.outHeight;
	    final int width = options.outWidth;
	    int inSampleSize = 1;
	
	    if (height > reqHeight || width > reqWidth) {
	
	        final int halfHeight = height / 2;
	        final int halfWidth = width / 2;
	
	        while ((halfHeight / inSampleSize) > reqHeight
	                && (halfWidth / inSampleSize) > reqWidth) {
	            inSampleSize *= 2;
	        }
    	}
    	return inSampleSize;
	}
	
	public boolean bitmapExists() {
		BitmapFactory.Options options = getInDecodeBoundsOptions();
		BitmapFactory.decodeFile(mImageFilePath, options);
		return options.outHeight != -1;
	}
	
	public boolean hasGPSTag() {
		EXIFtoLatLngConverter converter = new EXIFtoLatLngConverter(getEXIF());
		return converter.isValid();
	}
	
	public LatLng getLatLngFromEXIF() {
		LatLng latlng = null;
		
		if (hasGPSTag()) {
			EXIFtoLatLngConverter converter = new EXIFtoLatLngConverter(mEXIF);
			latlng = converter.getLatLng();
		}

		return latlng;
	}
				
	private class EXIFtoLatLngConverter {
		private boolean mIsValid = false;
		private Double mLatitude, mLongitude;
		
		public EXIFtoLatLngConverter(ExifInterface exif) {
			String lat = exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
			String latRef = exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF);
			String lng = exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
			String lngRef = exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF);

			if(lat != null && latRef !=null && lng != null && lngRef !=null) {				
				mIsValid = true;
			
				//Latitude in [-90,+90] depending on hemisphere
				//Latitude in [-180,+180] depending on hemisphere					
				mLatitude = latRef.equals("N") ? toDegreesDecimal(lat) : -toDegreesDecimal(lat);
				mLongitude = lngRef.equals("E") ? toDegreesDecimal(lng) : -toDegreesDecimal(lng);
			}
		}

		private Double toDegreesDecimal(String DMS){
			//Want degree decimal format from rational DMS format.
			//Exif: in ""degrees, minutes, seconds"" (DMS) in rational format.
			//Degree Decimal = degrees + minutes/60 + seconds/3600.
			
			String[] sDMS = DMS.split(",", 3);
			String[] sD = sDMS[0].split("/", 2);
			String[] sM = sDMS[1].split("/", 2);
			String[] sS = sDMS[2].split("/", 2);
			
			Double degrees = Double.valueOf(sD[0])/Double.valueOf(sD[1]);
			Double minutes = Double.valueOf(sM[0])/Double.valueOf(sM[1]);
			Double seconds = Double.valueOf(sS[0])/Double.valueOf(sS[1]);
			
			Double inDegDec = Double.valueOf(degrees + (minutes/60) + (seconds/3600));
			
			return inDegDec;
		};

		public boolean isValid() {
			return mIsValid;
		}

		public LatLng getLatLng() {
			if(isValid())
				return new LatLng(mLatitude, mLongitude);
			else
				return null;
		}
	}
	
	public static String getRealPathFromURI(Context context, Uri contentUri) {
		Cursor cursor = null;
		try { 
		    String[] proj = { MediaStore.Images.Media.DATA };
		    cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
		    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		    cursor.moveToFirst();
		    return cursor.getString(column_index);
		  } finally {
		    if (cursor != null) {
		      cursor.close();
		    }
		  }
	}

	public static Bitmap resizeImage(Bitmap bitmap, int maxWidth, int maxHeight) {
		int width = bitmap.getWidth(); 
		int height = bitmap.getHeight(); 
		float ratio = (float)width/(float)height;
		
		int newWidth = Math.min(maxWidth, width);
		int newHeight= Math.min(maxHeight, height);
		
		switch (getOrientation(bitmap)) {
			case PORTRAIT:
				newWidth = (int)(ratio * (float)newHeight);
			case LANDSCAPE:
				newHeight = (int)((float)newWidth/ratio);
				break;
			default:
				newWidth = newHeight = Math.min(newWidth,newHeight);
				break;
		}
		
		float scaleWidth = ((float) newWidth) / width; 
		float scaleHeight = ((float) newHeight) / height; 

		Matrix matrix = new Matrix(); 
		matrix.postScale(scaleWidth, scaleHeight); 
		Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true); 

		return resizedBitmap; 
	}
	
	public static PhotoOrientation getOrientation(Bitmap bitmap) {			
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		
		if(width > height)
			return PhotoOrientation.LANDSCAPE;
		
		if(height > width)
			return PhotoOrientation.PORTRAIT;
		
		return PhotoOrientation.SQUARE;
	}
	
	public static Bitmap rotateBitmapUsingExif(String imageFilePath, Bitmap bitmap) {
		 ExifInterface exif = null;
		 try {
			 exif = new ExifInterface(imageFilePath);
		 } catch (IOException e) {
			 Log.i(TAG, "No EXIF found in " + imageFilePath);
		 }
		 
		 if(exif == null)
			 return bitmap;
		 
		 int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
	            
		 if (orientation == 1) {
		     return bitmap;
		 }
	
		 Matrix matrix = new Matrix();
		 switch (orientation) {
		 case 2:
			 matrix.setScale(-1, 1);
			 break;
		 case 3:
		     matrix.setRotate(180);
		     break;
		 case 4:
		     matrix.setRotate(180);
		     matrix.postScale(-1, 1);
		     break;
		 case 5:
		     matrix.setRotate(90);
		     matrix.postScale(-1, 1);
		     break;
		 case 6:
		     matrix.setRotate(90);
		     break;
		 case 7:
		     matrix.setRotate(-90);
		     matrix.postScale(-1, 1);
		     break;
		 case 8:
		     matrix.setRotate(-90);
		     break;
		 default:
		     return bitmap;
		 }
		
		 try {
		     Bitmap oriented = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		     return oriented;
		 } catch (OutOfMemoryError e) {
		     e.printStackTrace();
		     return bitmap;
		 }	        
	 }
}
