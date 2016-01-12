package com.chdryra.android.reviewer.PlugIns.DatabaseAndroidSqLite.Implementation;

import android.database.Cursor;

import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.RowValues;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 12/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class CursorRowValues implements RowValues {
    Cursor mCursor;

    public CursorRowValues(Cursor cursor) {
        mCursor = cursor;
    }

    @Override
    public String getString(String columnName) {
        return mCursor.getString(getIndex(columnName));
    }

    @Override
    public Float getFloat(String columnName) {
        return mCursor.getFloat(getIndex(columnName));
    }

    @Override
    public Double getDouble(String columnName) {
        return mCursor.getDouble(getIndex(columnName));
    }

    @Override
    public Long getLong(String columnName) {
        return mCursor.getLong(getIndex(columnName));
    }

    @Override
    public Integer getInteger(String columnName) {
        return mCursor.getInt(getIndex(columnName));
    }

    @Override
    public Boolean getBoolean(String columnName) {
        return mCursor.getInt(getIndex(columnName)) == 1;
    }

    @Override
    public Byte[] getByteArray(String columnName) {
        ArrayList<Byte> bytes = new ArrayList<>();
        for(byte b : mCursor.getBlob(getIndex(columnName))) {
            bytes.add(b);
        }

        return (Byte[]) bytes.toArray();
    }

    private int getIndex(String columnName) {
        return mCursor.getColumnIndexOrThrow(columnName);
    }
}
