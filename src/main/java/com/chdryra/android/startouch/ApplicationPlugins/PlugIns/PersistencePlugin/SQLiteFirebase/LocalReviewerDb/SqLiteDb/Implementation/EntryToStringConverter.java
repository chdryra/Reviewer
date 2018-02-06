/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.SqLiteDb.Implementation;


import android.support.annotation.Nullable;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Implementation.DbEntryType;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Interfaces.RowEntry;


/**
 * Created by: Rizwan Choudrey
 * On: 15/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class EntryToStringConverter {

    @Nullable
    public <T> String convert(RowEntry<?, T> entry) {
        Object value = entry.getValue();
        if(value == null) return null;
        DbEntryType<?> type = entry.getEntryType();

        if(type.equals(DbEntryType.TEXT)) {
            return (String)value;
        } else if(type.equals(DbEntryType.BOOLEAN)) {
            return (Boolean)value ? "1" : "0";
        } else if(type.equals(DbEntryType.INTEGER)) {
            return String.valueOf((int) value);
        } else if(type.equals(DbEntryType.FLOAT)) {
            return String.valueOf((float)value);
        } else if(type.equals(DbEntryType.DOUBLE)) {
            return String.valueOf((double)value);
        } else if(type.equals(DbEntryType.LONG)) {
            return String.valueOf((long)value);
        } else {
            throw new IllegalArgumentException("EntryType: " + type + " not supported");
        }
    }
}
