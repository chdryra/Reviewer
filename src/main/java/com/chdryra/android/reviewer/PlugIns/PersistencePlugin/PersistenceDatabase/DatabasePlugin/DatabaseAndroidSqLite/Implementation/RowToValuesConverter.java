package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.DatabaseAndroidSqLite.Implementation;


import android.content.ContentValues;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Implementation.ByteArray;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Implementation.DbEntryType;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Interfaces.DbTableRow;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Interfaces.RowEntry;

/**
 * Created by: Rizwan Choudrey
 * On: 15/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class RowToValuesConverter {

    public ContentValues convert(DbTableRow row) {
        ContentValues values = new ContentValues();
        for(RowEntry<?> entry : row) {
            putEntry(entry, values);
        }

        return values;
    }

    private void putEntry(RowEntry<?> entry, ContentValues values) {
        String columnName = entry.getColumnName();
        Object value = entry.getValue();
        DbEntryType type = entry.getEntryType();
        if(type == DbEntryType.TEXT) {
            values.put(columnName, (String)value);
        } else if(type == DbEntryType.BOOLEAN) {
            values.put(columnName, (Boolean)value);
        } else if(type == DbEntryType.INTEGER) {
            values.put(columnName, (Integer)value);
        } else if(type == DbEntryType.FLOAT) {
            values.put(columnName, (Float)value);
        } else if(type == DbEntryType.DOUBLE) {
            values.put(columnName, (Double)value);
        } else if(type == DbEntryType.LONG) {
            values.put(columnName, (Long)value);
        } else if(type == DbEntryType.BYTE_ARRAY) {
            values.put(columnName, ((ByteArray) value).getData());
        } else {
            throw new IllegalArgumentException("EntryType: " + type + " not supported");
        }
    }
}
