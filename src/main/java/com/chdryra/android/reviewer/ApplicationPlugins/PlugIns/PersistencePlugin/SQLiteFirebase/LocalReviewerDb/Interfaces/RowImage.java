/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces;



import android.graphics.Bitmap;
import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DateTime;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation.ColumnInfo;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Implementation.ByteArray;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Implementation.DbEntryType;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by: Rizwan Choudrey
 * On: 09/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface RowImage extends ReviewDataRow<RowImage>, DataImage {
    ColumnInfo<String> IMAGE_ID = new ColumnInfo<>("image_id", DbEntryType.TEXT);
    ColumnInfo<String> REVIEW_ID = new ColumnInfo<>("review_id", DbEntryType.TEXT);
    ColumnInfo<ByteArray> BITMAP = new ColumnInfo<>("bitmap", DbEntryType.BYTE_ARRAY);
    ColumnInfo<Boolean> IS_COVER = new ColumnInfo<>("is_cover", DbEntryType.BOOLEAN);
    ColumnInfo<Double> LATITUDE = new ColumnInfo<>("latitude", DbEntryType.DOUBLE);
    ColumnInfo<Double> LONGITUDE = new ColumnInfo<>("longitude", DbEntryType.DOUBLE);
    ColumnInfo<String> CAPTION = new ColumnInfo<>("caption", DbEntryType.TEXT);
    ColumnInfo<Long> IMAGE_DATE = new ColumnInfo<>("image_date", DbEntryType.LONG);

    byte[] getBitmapByteArray();

    @Override
    Bitmap getBitmap();

    @Override
    DateTime getDate();

    @Nullable
    @Override
    LatLng getLatLng();

    @Override
    String getCaption();

    @Override
    boolean isCover();

    @Override
    ReviewId getReviewId();

    @Override
    String getRowId();

    @Override
    String getRowIdColumnName();

    @Override
    boolean hasData(DataValidator validator);
}
