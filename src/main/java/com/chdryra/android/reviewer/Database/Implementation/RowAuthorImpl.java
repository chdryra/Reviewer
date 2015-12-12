package com.chdryra.android.reviewer.Database.Implementation;

import android.content.ContentValues;
import android.database.Cursor;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumUserId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.UserId;
import com.chdryra.android.reviewer.Database.Interfaces.RowAuthor;

/**
 * Created by: Rizwan Choudrey
 * On: 09/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RowAuthorImpl implements RowAuthor {
    private String mUserId;
    private String mName;

    //Constructors
    public RowAuthorImpl(DataAuthor author) {
        mUserId = author.getUserId().toString();
        mName = author.getName();
    }

    //Via reflection
    public RowAuthorImpl() {
    }

    public RowAuthorImpl(Cursor cursor) {
        mUserId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_ID));
        mName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AUTHOR_NAME));
    }

    //Overridden

    @Override
    public String getName() {
        return mName;
    }

    @Override
    public UserId getUserId() {
        return new DatumUserId(mUserId);
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
