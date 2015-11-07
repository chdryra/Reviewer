package com.chdryra.android.reviewer.Database;

import android.content.ContentValues;
import android.database.Cursor;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataValidator;
import com.chdryra.android.reviewer.Model.UserData.Author;
import com.chdryra.android.reviewer.Model.UserData.UserId;

/**
 * Created by: Rizwan Choudrey
 * On: 09/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RowAuthor implements TableRow {
    public static final String COLUMN_USER_ID = "user_id";
    public  static final String COLUMN_AUTHOR_NAME = "name";

    private String mUserId;
    private String mName;
    private DataValidator mValidator;

    //Constructors
    public RowAuthor(Author author, DataValidator validator) {
        mUserId = author.getUserId().toString();
        mName = author.getName();
        mValidator = validator;
    }

    //Via reflection
    public RowAuthor() {
    }

    public RowAuthor(Cursor cursor, DataValidator validator) {
        mUserId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_ID));
        mName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AUTHOR_NAME));
        mValidator = validator;
    }

    public Author toAuthor() {
        return new Author(mName, UserId.fromString(mUserId));
    }

    //Overridden
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
    public boolean hasData() {
        return mValidator != null && mValidator.validateString(getRowId());
    }
}
