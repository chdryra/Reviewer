/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.util.Base64;

import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataImage;
import com.google.android.gms.maps.model.LatLng;

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
    private LatitudeLongitude latLng;
    private boolean cover;

    public ImageData() {
    }

    public ImageData(DataImage image) {
        bitmap = asString(image.getBitmap());
        date = image.getDate().getTime();
        caption = image.getCaption();
        if (image.getLatLng() != null) latLng = new LatitudeLongitude(image.getLatLng());
        cover = image.isCover();
    }

    @Nullable
    public static Bitmap asBitmap(String base64) {
        if (base64 == null || base64.length() == 0) return null;
        byte[] imageAsBytes = Base64.decode(base64, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }

    public static int size() {
        return 5;
    }

    static String asString(@Nullable Bitmap bitmap) {
        if (bitmap == null) return "";
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT);
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

    public LatitudeLongitude getLatLng() {
        return latLng;
    }

    public boolean isCover() {
        return cover;
    }

    @Nullable
    public LatLng toLatLng() {
        return latLng != null ? new LatLng(latLng.getLatitude(), latLng.getLongitude()) : null;
    }
}
