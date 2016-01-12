package com.chdryra.android.reviewer.PlugIns.DatabaseAndroidSqLite.Implementation;

import android.content.ContentValues;

import com.chdryra.android.reviewer.PlugIns.DatabaseAndroidSqLite.Interfaces.RowConverter;
import com.chdryra.android.reviewer.Database.Interfaces.RowImage;

/**
 * Created by: Rizwan Choudrey
 * On: 12/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class RowImageConverter implements RowConverter<RowImage> {
    @Override
    public ContentValues convert(RowImage row) {
        ContentValues values = new ContentValues();
        values.put(RowImage.COLUMN_IMAGE_ID, row.getRowId());
        values.put(RowImage.COLUMN_REVIEW_ID, row.getReviewId().toString());
        values.put(RowImage.COLUMN_BITMAP, row.getBitmapByteArray());
        if (row.getDate() != null)
            values.put(RowImage.COLUMN_IMAGE_DATE, row.getDate().getTime());
        values.put(RowImage.COLUMN_CAPTION, row.getCaption());
        values.put(RowImage.COLUMN_IS_COVER, row.isCover());

        return values;
    }
}
