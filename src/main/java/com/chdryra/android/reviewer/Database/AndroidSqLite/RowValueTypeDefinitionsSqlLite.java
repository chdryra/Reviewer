package com.chdryra.android.reviewer.Database.AndroidSqLite;

import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.RowValueTypeDefinitions;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.RowValueType;
import com.chdryra.android.reviewer.Database.GenericDb.Implementation.RowValueTypeImpl;

/**
 * Created by: Rizwan Choudrey
 * On: 12/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class RowValueTypeDefinitionsSqlLite implements RowValueTypeDefinitions {
    @Override
    public RowValueType getStringType() {
        return new RowValueTypeImpl("TEXT");
    }

    @Override
    public RowValueType getFloatType() {
        return new RowValueTypeImpl("REAL");
    }

    @Override
    public RowValueType getLongType() {
        return getIntegerType();
    }

    @Override
    public RowValueType getBooleanType() {
        return getIntegerType();
    }

    @Override
    public RowValueType getIntegerType() {
        return new RowValueTypeImpl("INTEGER");
    }

    @Override
    public RowValueType getByteArrayType() {
        return new RowValueTypeImpl("BLOB");
    }
}
