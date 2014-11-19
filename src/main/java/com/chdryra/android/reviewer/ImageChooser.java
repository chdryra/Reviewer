/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 12 November, 2014
 */

package com.chdryra.android.reviewer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.chdryra.android.mygenerallibrary.ActivityResultCode;
import com.chdryra.android.mygenerallibrary.BitmapLoader;
import com.chdryra.android.mygenerallibrary.FileIncrementor;
import com.chdryra.android.mygenerallibrary.ImageHelper;
import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.io.IOException;

/**
 * Created by: Rizwan Choudrey
 * On: 12/11/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class ImageChooser {
    private static final String TAG              = "ImageChooser";
    private static final String ERROR_CREATE     = "Couldn't create file";
    private static final String ERROR_NO_STORAGE = "No storage available";

    private Activity        mActivity;
    private FileIncrementor mFileIncrementor;
    private String          mCaptureFile;

    public interface ImageChooserListener {
        public void onImageChosen(GVImageList.GVImage image);
    }

    public ImageChooser(ControllerReview controller, Activity activity) {
        mActivity = activity;
        String dir = Administrator.get(mActivity).getApplicationName();
        mFileIncrementor = new FileIncrementor(Environment.getExternalStoragePublicDirectory
                (Environment.DIRECTORY_DCIM), dir, controller.getSubject(), "jpg");
    }

    Intent getChooserIntents() {
        try {
            createNewCaptureFile();
            return ImageHelper.getImageChooserIntents(mActivity, mCaptureFile);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }

        return null;
    }

    boolean chosenImageExists(ActivityResultCode resultCode, Intent data) {
        //Returns true if bitmap exists.
        if (resultCode.equals(ActivityResultCode.OK)) {
            final boolean isCamera;

            if (data == null) {
                isCamera = true;
            } else {
                final String action = data.getAction();
                isCamera = action != null && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
            }

            if (!isCamera) {
                deleteCreatedCaptureFile();
                mCaptureFile = getImagePathFromUri(mActivity, data.getData());
            }

            if (ImageHelper.bitmapExists(mCaptureFile)) {
                if (isCamera) {
                    //Update android gallery
                    Uri imageUri = Uri.fromFile(new File(mCaptureFile));
                    mActivity.sendBroadcast(
                            new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, imageUri));
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

    void getChosenImage(final ImageChooserListener listener) {
        if (mCaptureFile != null) {
            int maxWidth = (int) mActivity.getResources().getDimension(R.dimen.imageMaxWidth);
            int maxHeight = (int) mActivity.getResources().getDimension(R.dimen.imageMaxHeight);

            final BitmapLoader.LoadListener loadListener = new BitmapLoader.LoadListener() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap) {
                    LatLng ll = ImageHelper.getLatLngFromEXIF(ImageHelper.getEXIF(mCaptureFile));
                    listener.onImageChosen(new GVImageList.GVImage(bitmap, ll));
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
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
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
