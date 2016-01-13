package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.DatabaseAndroidSqLite.Implementation;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.Api.RowValueTypeDefinitions;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.Api.RowValueType;

/**
 * Created by: Rizwan Choudrey
 * On: 12/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class RowValueTypeDefinitionsSqlLite implements RowValueTypeDefinitions {
    private static final RowValueType TEXT = new SqlLiteType("TEXT");
    public static final SqlLiteType REAL = new SqlLiteType("REAL");
    public static final SqlLiteType INTEGER = new SqlLiteType("INTEGER");
    public static final SqlLiteType BLOB = new SqlLiteType("BLOB");

    @Override
    public RowValueType getTextType() {
        return TEXT;
    }

    @Override
    public RowValueType getFloatType() {
        return REAL;
    }

    @Override
    public RowValueType getDoubleType() {
        return REAL;
    }

    @Override
    public RowValueType getLongType() {
        return INTEGER;
    }

    @Override
    public RowValueType getBooleanType() {
        return INTEGER;
    }

    @Override
    public RowValueType getIntegerType() {
        return INTEGER;
    }

    @Override
    public RowValueType getByteArrayType() {
        return BLOB;
    }

    private static class SqlLiteType implements RowValueType {
        private String mType;

        private SqlLiteType(String type) {
            mType = type;
        }

        @Override
        public String getTypeString() {
            return mType;
        }
    }
}
