package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.DatabaseAndroidSqLite.Implementation;

import android.content.ContentValues;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.DatabaseAndroidSqLite.Interfaces.RowConverter;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.RowTag;

/**
 * Created by: Rizwan Choudrey
 * On: 12/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class RowTagConverter implements RowConverter<RowTag> {
    @Override
    public ContentValues convert(RowTag row) {
        ContentValues values = new ContentValues();
        values.put(RowTag.COLUMN_TAG, row.getTag());
        values.put(RowTag.COLUMN_REVIEWS, row.getReviewIdsString());

        return values;
    }
}
