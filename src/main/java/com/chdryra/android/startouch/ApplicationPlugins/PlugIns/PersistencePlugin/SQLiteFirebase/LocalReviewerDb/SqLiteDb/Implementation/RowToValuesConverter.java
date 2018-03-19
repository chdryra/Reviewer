/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.SqLiteDb.Implementation;


import android.content.ContentValues;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Implementation.ByteArray;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Implementation.DbEntryType;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Interfaces.DbTableRow;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Interfaces.RowEntry;

/**
 * Created by: Rizwan Choudrey
 * On: 15/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class RowToValuesConverter {

    public <DbRow extends DbTableRow> ContentValues convert(DbTableRow<DbRow> row) {
        ContentValues values = new ContentValues();
        for (RowEntry<DbRow, ?> entry : row) {
            putEntry(entry, values);
        }

        return values;
    }

    private void putEntry(RowEntry<?, ?> entry, ContentValues values) {
        String columnName = entry.getColumnName();
        Object value = entry.getValue();
        DbEntryType<?> type = entry.getEntryType();
        if (type.equals(DbEntryType.TEXT)) {
            values.put(columnName, (String) value);
        } else if (type.equals(DbEntryType.BOOLEAN)) {
            values.put(columnName, (Boolean) value);
        } else if (type.equals(DbEntryType.INTEGER)) {
            values.put(columnName, (Integer) value);
        } else if (type.equals(DbEntryType.FLOAT)) {
            values.put(columnName, (Float) value);
        } else if (type.equals(DbEntryType.DOUBLE)) {
            values.put(columnName, (Double) value);
        } else if (type.equals(DbEntryType.LONG)) {
            values.put(columnName, (Long) value);
        } else if (type.equals(DbEntryType.BYTE_ARRAY)) {
            ByteArray array = (ByteArray) value;
            values.put(columnName, array != null ? array.getData() : null);
        } else {
            throw new IllegalArgumentException("EntryType: " + type + " not supported");
        }
    }
}
