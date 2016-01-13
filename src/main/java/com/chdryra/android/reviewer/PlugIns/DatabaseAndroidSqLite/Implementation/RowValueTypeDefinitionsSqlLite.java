package com.chdryra.android.reviewer.PlugIns.DatabaseAndroidSqLite.Implementation;

import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.RowValueTypeDefinitions;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.RowValueType;
import com.chdryra.android.reviewer.Database.GenericDb.Implementation.RowValueTypeImpl;

/**
 * Created by: Rizwan Choudrey
 * On: 12/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class RowValueTypeDefinitionsSqlLite implements RowValueTypeDefinitions {
    private static final RowValueType TEXT = new RowValueTypeImpl("TEXT");
    public static final RowValueTypeImpl REAL = new RowValueTypeImpl("REAL");
    public static final RowValueTypeImpl INTEGER = new RowValueTypeImpl("INTEGER");
    public static final RowValueTypeImpl BLOB = new RowValueTypeImpl("BLOB");

    @Override
    public RowValueType getStringType() {
        return TEXT;
    }

    @Override
    public RowValueType getFloatType() {
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
}
