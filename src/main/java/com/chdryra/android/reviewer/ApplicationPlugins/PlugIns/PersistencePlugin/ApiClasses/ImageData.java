/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.ApiClasses;

import android.graphics.Bitmap;
import android.util.Base64;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;

import java.io.ByteArrayOutputStream;

/**
 * Created by: Rizwan Choudrey
 * On: 16/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ImageData {
    private String bitmap;
    private long date;
    private String caption;
    private boolean cover;

    public ImageData() {
    }

    public ImageData(DataImage image) {
        Bitmap bitmap = image.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        bitmap.recycle();
        byte[] byteArray = stream.toByteArray();
        this.bitmap = Base64.encodeToString(byteArray, Base64.DEFAULT);
        date = image.getDate().getTime();
        caption = image.getCaption();
        cover = image.isCover();
    }

    public String getBitmap() {
        return bitmap;
    }

    public long getDate() {
        return date;
    }

    public String getCaption() {
        return caption;
    }

    public boolean isCover() {
        return cover;
    }
}
