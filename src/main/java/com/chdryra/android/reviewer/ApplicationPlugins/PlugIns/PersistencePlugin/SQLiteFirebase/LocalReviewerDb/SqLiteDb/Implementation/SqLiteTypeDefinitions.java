/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.SqLiteDb.Implementation;



import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Implementation.DbEntryType;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 12/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class SqLiteTypeDefinitions {
    private static final String TEXT = "TEXT";
    private static final String REAL = "REAL";
    private static final String INTEGER = "INTEGER";
    private static final String BLOB = "BLOB";

    private static final Map<DbEntryType<?>, String> mMap;
    static{
        mMap = new HashMap<>();
        mMap.put(DbEntryType.TEXT, TEXT);
        mMap.put(DbEntryType.FLOAT, REAL);
        mMap.put(DbEntryType.DOUBLE, REAL);
        mMap.put(DbEntryType.INTEGER, INTEGER);
        mMap.put(DbEntryType.BOOLEAN, INTEGER);
        mMap.put(DbEntryType.LONG, INTEGER);
        mMap.put(DbEntryType.BYTE_ARRAY, BLOB);
    }

    public String getSqlTypeName(DbEntryType<?> entryType) {
        return mMap.get(entryType);
    }
}
