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
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

public class ImageHelper {

	private static final String TAG = "ImageHelper";
	private static HashMap<String, ImageHelper> sImageHelpers = new HashMap<String, ImageHelper>();
	public static enum PhotoOrientation{PORTRAIT, LANDSCAPE, SQUARE};
	
	private String mImageTitle;
	private File mImageFile;
	private Bitmap mBitmap;
	private ExifInterface mEXIF;
	
	public static ImageHelper getInstance(String imageTitle) {
		if(!sImageHelpers.containsKey(imageTitle))
			sImageHelpers.put(imageTitle, new ImageHelper(imageTitle));
		
		return sImageHelpers.get(imageTitle);
	}
	
	private ImageHelper(String imageTitle) {
		mImageTitle = imageTitle;
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
	
	public void deleteImage() {
		mImageFile = null;
		mEXIF = null;
		mBitmap = null;
	}

	public File getImageFile() {
		return mImageFile;
	}

	public void setImageFile(File imageFile) {
		if(mImageFile != null)
			deleteImage();
		
		mImageFile = imageFile;
	}
	
	public void createNewImageFile() {
		if(mImageFile != null)
			deleteImage();
		
		String timeStamp = SimpleDateFormat.getDateInstance().format(new Date());
	    String imageFileName = mImageTitle + "_" + timeStamp;
	    
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String path = Environment.getExternalStorageDirectory().getName() + File.separatorChar + imageFileName + ".jpg";
            mImageFile = new File(path);
            try {
                if( !mImageFile.exists() ) {
                    mImageFile.getParentFile().mkdirs();
                    mImageFile.createNewFile();
                }

            } catch (IOException e) {
                Log.e(TAG, "Could not create file.", e);
            }
            Log.i(TAG, path);
        } else {
        	Log.i(TAG, "No external storage");
        }
	}
	
	public Uri getUri() {
		return Uri.fromFile(mImageFile);
	}

	public Bitmap getBitmap(Context context) {
		if(mBitmap == null)
			loadBitmap(context);
		
		return mBitmap;
	}
	
	private void loadBitmap(Context context) {
		try {
			mBitmap = Media.getBitmap(context.getContentResolver(), getUri());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public boolean bitmapExists(Context context) {
		return getBitmap(context) != null;
	}
	
	private PhotoOrientation getOrientation(Context context) {			
		if(!bitmapExists(context))
			return null;
		
		int width = getBitmap(context).getWidth();
		int height = getBitmap(context).getHeight();
		
		if(width > height)
			return PhotoOrientation.LANDSCAPE;
		
		if(height > width)
			return PhotoOrientation.PORTRAIT;
		
		return PhotoOrientation.SQUARE;
	}
			
	public Bitmap resizeImage(Context context, int maxWidth, int maxHeight) {
		if(!bitmapExists(context))
			return null;
		
		int width = getBitmap(context).getWidth(); 
		int height = getBitmap(context).getHeight(); 
		float ratio = (float)width/(float)height;
		
		int newWidth = Math.min(maxWidth, width);
		int newHeight= Math.min(maxHeight, height);
		
		switch (getOrientation(context)) {
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
		Bitmap resizedBitmap = Bitmap.createScaledBitmap(getBitmap(context), newWidth, newHeight, true); 

		return resizedBitmap; 
	}

	public boolean hasGPSTag() {
		if(mEXIF == null)
		try {
			mEXIF = new ExifInterface(mImageFile.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		EXIFtoLatLngConverter converter = new EXIFtoLatLngConverter(mEXIF);
		return converter.isValid();
	}
	
	public LatLng getLatLngFromImage() {
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
	
}
