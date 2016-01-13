package com.chdryra.android.reviewer.PlugIns.Persistence.Api;

/**
 * Created by: Rizwan Choudrey
 * On: 12/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface RowValueTypeDefinitions {
    RowValueType getTextType();

    RowValueType getFloatType();

    RowValueType getDoubleType();

    RowValueType getLongType();

    RowValueType getIntegerType();

    RowValueType getBooleanType();

    RowValueType getByteArrayType();
}
