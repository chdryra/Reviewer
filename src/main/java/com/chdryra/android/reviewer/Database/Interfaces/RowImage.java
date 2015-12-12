package com.chdryra.android.reviewer.Database.Interfaces;

import android.content.ContentValues;
import android.graphics.Bitmap;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 09/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface RowImage extends ReviewDataRow, DataImage {
    String COLUMN_IMAGE_ID = "image_id";
    String COLUMN_REVIEW_ID = "review_id";
    String COLUMN_BITMAP = "bitmap";
    String COLUMN_IMAGE_DATE = "image_date";
    String COLUMN_CAPTION = "caption";
    String COLUMN_IS_COVER = "is_cover";

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
    ContentValues getContentValues();

    @Override
    boolean hasData(DataValidator validator);
}
