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
    public <T> T getValue(String columnName, DbEntryType<T> entryType) {
        int columnIndex = mCursor.getColumnIndexOrThrow(columnName);
        Object value;
        Class<T> type = entryType.getTypeClass();
        if(type == String.class) {
            value = mCursor.getString(columnIndex);
        } else if(type == Boolean.class) {
            value = mCursor.getInt(columnIndex) == 1;
        } else if(type == Integer.class) {
            value = mCursor.getInt(columnIndex);
        } else if(type == Float.class) {
            value = mCursor.getFloat(columnIndex);
        } else if(type == Double.class) {
            value = mCursor.getDouble(columnIndex);
        } else if(type == Long.class) {
            value = mCursor.getLong(columnIndex);
        } else if(type == ByteArray.class) {
            value = new ByteArray(mCursor.getBlob(columnIndex));
        } else {
            throw new IllegalArgumentException("EntryType: " + type + " not supported");
        }

        return type.cast(value);
    }
}
