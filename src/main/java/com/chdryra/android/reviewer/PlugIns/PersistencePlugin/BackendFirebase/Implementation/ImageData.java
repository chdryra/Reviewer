/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.BackendFirebase.Implementation;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;

import java.io.ByteArrayOutputStream;

/**
 * Created by: Rizwan Choudrey
 * On: 16/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ImageData {
    private String mBitmap;
    private long mDate;
    private String mCaption;
    private boolean mIsCover;

    public ImageData() {
    }

    public ImageData(DataImage image) {
        Bitmap bitmap = image.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        bitmap.recycle();
        byte[] byteArray = stream.toByteArray();
        mBitmap = Base64.encodeToString(byteArray, Base64.DEFAULT);
        mDate = image.getDate().getTime();
        mCaption = image.getCaption();
        mIsCover = image.isCover();
    }

    public String getBitmap() {
        return mBitmap;
    }

    public long getDate() {
        return mDate;
    }

    public String getCaption() {
        return mCaption;
    }

    public boolean isCover() {
        return mIsCover;
    }

    //Here as a reminder...
    private Bitmap decode() {
        byte[] imageAsBytes = Base64.decode(mBitmap, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }
}
