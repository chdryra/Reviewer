package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.DatabaseAndroidSqLite.Implementation;

import android.content.ContentValues;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.DatabaseAndroidSqLite.Interfaces.RowToContentValuesConverter;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.RowFact;

/**
 * Created by: Rizwan Choudrey
 * On: 12/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class RowFactConverter implements RowToContentValuesConverter<RowFact> {
    @Override
    public ContentValues convert(RowFact row) {
        ContentValues values = new ContentValues();
        values.put(RowFact.COLUMN_FACT_ID, row.getRowId());
        values.put(RowFact.COLUMN_REVIEW_ID, row.getReviewId().toString());
        values.put(RowFact.COLUMN_LABEL, row.getLabel());
        values.put(RowFact.COLUMN_VALUE, row.getValue());
        values.put(RowFact.COLUMN_IS_URL, row.isUrl());

        return values;
    }
}
