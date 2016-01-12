package com.chdryra.android.reviewer.Database.AndroidSqLite;

import android.content.ContentValues;

import com.chdryra.android.reviewer.Database.Interfaces.RowAuthor;

/**
 * Created by: Rizwan Choudrey
 * On: 12/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
class RowAuthorConverter implements RowConverter<RowAuthor> {
    @Override
    public ContentValues convert(RowAuthor row) {
        ContentValues values = new ContentValues();
        values.put(RowAuthor.COLUMN_USER_ID, row.getRowId());
        values.put(RowAuthor.COLUMN_AUTHOR_NAME, row.getName());

        return values;
    }
}
