package com.chdryra.android.reviewer.Database.GenericDb.Interfaces;

/**
 * Created by: Rizwan Choudrey
 * On: 12/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface RowValueTypeDefinitions {
    RowValueType getStringType();

    RowValueType getFloatType();

    RowValueType getLongType();

    RowValueType getIntegerType();

    RowValueType getBooleanType();

    RowValueType getByteArrayType();
}
