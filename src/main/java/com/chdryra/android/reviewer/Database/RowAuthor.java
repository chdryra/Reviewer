package com.chdryra.android.reviewer.Database;

import android.content.ContentValues;
import android.database.Cursor;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataAuthor;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataValidator;

/**
 * Created by: Rizwan Choudrey
 * On: 09/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RowAuthor implements DbTableRow, DataAuthor {
    public static final String COLUMN_USER_ID = "user_id";
    public  static final String COLUMN_AUTHOR_NAME = "name";

    private String mUserId;
    private String mName;

    //Constructors
    public RowAuthor(DataAuthor author) {
        mUserId = author.getUserId();
        mName = author.getName();
    }

    //Via reflection
    public RowAuthor() {
    }

    public RowAuthor(Cursor cursor) {
        mUserId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_ID));
        mName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AUTHOR_NAME));
    }

    //Overridden

    @Override
    public String getName() {
        return mName;
    }

    @Override
    public String getUserId() {
        return mUserId;
    }

    @Override
    public String getRowId() {
        return mUserId;
    }

    @Override
    public String getRowIdColumnName() {
        return COLUMN_USER_ID;
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, mUserId);
        values.put(COLUMN_AUTHOR_NAME, mName);

        return values;
    }

    @Override
    public boolean hasData(DataValidator validator) {
        return validator.validate(this);
    }
}
