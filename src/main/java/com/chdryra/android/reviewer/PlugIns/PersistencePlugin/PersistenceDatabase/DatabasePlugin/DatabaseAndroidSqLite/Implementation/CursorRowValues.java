package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.DatabaseAndroidSqLite.Implementation;


import android.database.Cursor;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Implementation.ByteArray;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb
        .Implementation.DbEntryType;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Interfaces.RowValues;

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
    public <T> T getValue(String columnName, DbEntryType<T> type) {
        int columnIndex = mCursor.getColumnIndexOrThrow(columnName);
        Object value;
        if(type.equals(DbEntryType.TEXT)) {
            value = mCursor.getString(columnIndex);
        } else if(type.equals(DbEntryType.BOOLEAN)) {
            value = mCursor.getInt(columnIndex) == 1;
        } else if(type.equals(DbEntryType.INTEGER)) {
            value = mCursor.getInt(columnIndex);
        } else if(type.equals(DbEntryType.FLOAT)) {
            value = mCursor.getFloat(columnIndex);
        } else if(type.equals(DbEntryType.DOUBLE)) {
            value = mCursor.getDouble(columnIndex);
        } else if(type.equals(DbEntryType.LONG)) {
            value = mCursor.getLong(columnIndex);
        } else if(type.equals(DbEntryType.BYTE_ARRAY)) {
            value = new ByteArray(mCursor.getBlob(columnIndex));
        } else {
            throw new IllegalArgumentException("EntryType: " + type + " not supported");
        }

        return type.cast(value);
    }
}
