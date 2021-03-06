/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.chdryra.android.corelibrary.FileUtils.FileIncrementor;
import com.chdryra.android.corelibrary.Imaging.BitmapLoader;
import com.chdryra.android.corelibrary.Imaging.ImageHelper;
import com.chdryra.android.corelibrary.OtherUtils.ActivityResultCode;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.ImageChooser;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDate;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.startouch.R;
import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 12/11/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class ImageChooserImpl implements ImageChooser {
    private static final String TAG = "ImageChooser";
    private static final String ERROR_CREATE = "Couldn't create file";
    private static final String ERROR_NO_STORAGE = "No storage available";

    private final Context mContext;
    private final FileIncrementor mFileIncrementor;
    private String mCaptureFile;

    //Constructors
    public ImageChooserImpl(Context context, FileIncrementor fileIncrementor) {
        mContext = context;
        mFileIncrementor = fileIncrementor;
    }

    //public methods
    @Override
    public Intent getChooserIntents() {
        try {
            createNewCaptureFile();
            return ImageHelper.getImageChooserIntents(mContext, mCaptureFile);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }

        return new Intent();
    }

    @Override
    public boolean chosenImageExists(ActivityResultCode resultCode, Intent data) {
        //Returns true if bitmap exists.
        if (resultCode.equals(ActivityResultCode.OK)) {
            final boolean isCamera;

            if (data == null) {
                isCamera = true;
            } else {
                //Check inline data
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = null;
                if(extras != null) imageBitmap = (Bitmap) extras.get("data");
                if(imageBitmap != null) return true;

                final String action = data.getAction();
                isCamera = action != null && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
            }

            if (!isCamera) {
                deleteCreatedCaptureFile();
                Uri uri = data.getData();
                if(uri != null) mCaptureFile = getImagePathFromUri(mContext, uri);
            }

            if (ImageHelper.bitmapExists(mCaptureFile)) {
                if (isCamera) {
                    //Update android gallery
                    Uri imageUri = Uri.fromFile(new File(mCaptureFile));
                    Intent scanFile = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, imageUri);
                    mContext.sendBroadcast(scanFile);
                }

                return true;
            } else {
                if (isCamera) deleteCreatedCaptureFile();
                return false;
            }
        } else if (resultCode.equals(ActivityResultCode.CANCEL) &&
                !ImageHelper.bitmapExists(mCaptureFile)) {
            deleteCreatedCaptureFile();
            return false;
        }

        return false;
    }

    @Override
    public void getChosenImage(final ImageChooserListener listener) {
        if (mCaptureFile != null) {
            int maxWidth = (int) mContext.getResources().getDimension(R.dimen.imageMaxWidth);
            int maxHeight = (int) mContext.getResources().getDimension(R.dimen.imageMaxHeight);

            final BitmapLoader.LoadListener loadListener = new BitmapLoader.LoadListener() {
                //Overridden
                @Override
                public void onBitmapLoaded(Bitmap bitmap) {
                    ExifInterface exif = ImageHelper.getExif(mCaptureFile);
                    LatLng ll = ImageHelper.getLatLngFromExif(exif);
                    Date date = ImageHelper.getDateTimeFromEXIF(exif);
                    GvDate gvDate;
                    if (date == null) {
                        gvDate = new GvDate();
                    } else {
                        gvDate = new GvDate(date.getTime());
                    }
                    GvImage image = new GvImage(bitmap, gvDate, ll, "", false);
                    listener.onChosenImage(image);
                }
            };

            BitmapLoader loader = new BitmapLoader(mCaptureFile, loadListener);
            loader.load(maxWidth, maxHeight);
        }
    }

    private String getImagePathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            if (cursor != null) {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return "";
    }

    private void createNewCaptureFile() throws IOException {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            try {
                mCaptureFile = mFileIncrementor.createNewFile().getAbsolutePath();
            } catch (IOException e) {
                throw new IOException(ERROR_CREATE);
            }
        } else {
            throw new IOException(ERROR_NO_STORAGE);
        }
    }

    private void deleteCreatedCaptureFile() {
        mFileIncrementor.deleteLastFile();
        mCaptureFile = null;
    }
}
