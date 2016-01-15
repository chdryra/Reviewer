package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.DatabaseAndroidSqLite.Implementation;



import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin
        .Api.TransactorTypeDefinitions;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb
        .Implementation.DbEntryType;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Implementation.DbEntryTypes;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Interfaces.TransactorTypeMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 12/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class TransactorTypeDefinitionsSqlLite implements TransactorTypeDefinitions {
    public static final String TEXT = "TEXT";
    public static final String REAL = "REAL";
    public static final String INTEGER = "INTEGER";
    public static final String BLOB = "BLOB";

    private static final Map<DbEntryType<?>, TransactorTypeMapping<?>> mMap;
    static{
        mMap = new HashMap<>();
        mMap.put(DbEntryTypes.TEXT, new SqlLiteTypeMapping<>(TEXT, DbEntryTypes.TEXT));
        mMap.put(DbEntryTypes.FLOAT, new SqlLiteTypeMapping<>(REAL, DbEntryTypes.FLOAT));
        mMap.put(DbEntryTypes.DOUBLE, new SqlLiteTypeMapping<>(REAL, DbEntryTypes.DOUBLE));
        mMap.put(DbEntryTypes.INTEGER, new SqlLiteTypeMapping<>(INTEGER, DbEntryTypes.INTEGER));
        mMap.put(DbEntryTypes.BOOLEAN, new SqlLiteTypeMapping<>(INTEGER, DbEntryTypes.BOOLEAN));
        mMap.put(DbEntryTypes.LONG, new SqlLiteTypeMapping<>(INTEGER, DbEntryTypes.LONG));
        mMap.put(DbEntryTypes.BYTE_ARRAY, new SqlLiteTypeMapping<>(BLOB, DbEntryTypes.BYTE_ARRAY));
    }

    @Override
    public <T> TransactorTypeMapping<T> getType(DbEntryType<T> entryType) {
        return (TransactorTypeMapping<T>) mMap.get(entryType);
    }

    private static class SqlLiteTypeMapping<T> implements TransactorTypeMapping {
        private String mType;
        private DbEntryType<T> mDbType;

        public SqlLiteTypeMapping(String type, DbEntryType<T> dbType) {
            mType = type;
            mDbType = dbType;
        }

        @Override
        public String getTransactorTypeName() {
            return mType;
        }

        @Override
        public DbEntryType getDbEntryType() {
            return mDbType;
        }
    }
}
