package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.DatabaseAndroidSqLite.Implementation;



import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb.Implementation.DbEntryType;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 12/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class SqlLiteTypeDefinitions {
    public static final String TEXT = "TEXT";
    public static final String REAL = "REAL";
    public static final String INTEGER = "INTEGER";
    public static final String BLOB = "BLOB";

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
