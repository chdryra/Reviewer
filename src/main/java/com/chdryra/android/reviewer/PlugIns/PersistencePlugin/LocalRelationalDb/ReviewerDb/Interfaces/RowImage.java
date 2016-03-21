/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb.ReviewerDb.Interfaces;

import android.graphics.Bitmap;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb.RelationalDb
        .Implementation.ByteArray;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb.RelationalDb
        .Implementation.DbEntryType;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb.ReviewerDb
        .Implementation.ColumnInfo;

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
    ColumnInfo<String> CAPTION = new ColumnInfo<>("caption", DbEntryType.TEXT);
    ColumnInfo<Long> IMAGE_DATE = new ColumnInfo<>("image_date", DbEntryType.LONG);

    byte[] getBitmapByteArray();

    @Override
    Bitmap getBitmap();

    @Override
    DataDate getDate();

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
