package com.chdryra.android.reviewer.Database.GenericDb.Implementation;

import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbColumnDef;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.StorageType;

/**
 * Created by: Rizwan Choudrey
 * On: 14/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DbColumnDefImpl implements DbColumnDef{
    private String mColumnName;
    private StorageType mType;
    private ValueNullable mNullable;

    public DbColumnDefImpl(String columnName, StorageType type, ValueNullable nullable) {
        mColumnName = columnName;
        mType = type;
        mNullable = nullable;
    }

    @Override
    public String getName() {
        return mColumnName;
    }

    @Override
    public StorageType getType() {
        return mType;
    }

    @Override
    public ValueNullable getNullable() {
        return mNullable;
    }
}
