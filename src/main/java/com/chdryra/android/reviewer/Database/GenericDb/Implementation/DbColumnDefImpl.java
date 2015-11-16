package com.chdryra.android.reviewer.Database.GenericDb.Implementation;

import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbColumnDef;

/**
 * Created by: Rizwan Choudrey
 * On: 14/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DbColumnDefImpl implements DbColumnDef{
    private String mColumnName;
    private SQL.StorageType mType;
    private boolean mIsNullable;

    public DbColumnDefImpl(String columnName, SQL.StorageType type, SQL.Nullable nullable) {
        mColumnName = columnName;
        mType = type;
        mIsNullable = nullable == SQL.Nullable.TRUE;
    }

    //public methods
    @Override
    public String getName() {
        return mColumnName;
    }

    @Override
    public SQL.StorageType getType() {
        return mType;
    }

    @Override
    public boolean isNullable() {
        return mIsNullable;
    }
}
