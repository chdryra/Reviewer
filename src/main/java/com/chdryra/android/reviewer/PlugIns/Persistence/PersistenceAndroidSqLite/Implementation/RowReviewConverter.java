package com.chdryra.android.reviewer.PlugIns.Persistence.PersistenceAndroidSqLite.Implementation;

import android.content.ContentValues;

import com.chdryra.android.reviewer.PlugIns.Persistence.PersistenceAndroidSqLite.Interfaces.RowConverter;
import com.chdryra.android.reviewer.Database.Interfaces.RowReview;

/**
 * Created by: Rizwan Choudrey
 * On: 12/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class RowReviewConverter implements RowConverter<RowReview> {
    @Override
    public ContentValues convert(RowReview row) {
        ContentValues values = new ContentValues();
        values.put(RowReview.COLUMN_REVIEW_ID, row.getRowId());
        values.put(RowReview.COLUMN_PARENT_ID, row.getParentId());
        values.put(RowReview.COLUMN_AUTHOR_ID, row.getAuthorId());
        values.put(RowReview.COLUMN_PUBLISH_DATE, row.getPublishDate());
        values.put(RowReview.COLUMN_SUBJECT, row.getSubject());
        values.put(RowReview.COLUMN_RATING, row.getRating());
        values.put(RowReview.COLUMN_RATING_WEIGHT, row.getRatingWeight());
        values.put(RowReview.COLUMN_RATING_IS_AVERAGE, row.isRatingIsAverage());

        return values;
    }
}
