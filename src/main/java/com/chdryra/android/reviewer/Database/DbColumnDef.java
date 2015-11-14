package com.chdryra.android.reviewer.Database;

/**
 * Created by: Rizwan Choudrey
 * On: 14/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DbColumnDef {
    private String mColumnName;
    private SQL.StorageType mType;
    private boolean mIsNullable;

    DbColumnDef(String columnName, SQL.StorageType type, SQL.Nullable nullable) {
        mColumnName = columnName;
        mType = type;
        mIsNullable = nullable == SQL.Nullable.TRUE;
    }

    //public methods
    public String getName() {
        return mColumnName;
    }

    public SQL.StorageType getType() {
        return mType;
    }

    public boolean isNullable() {
        return mIsNullable;
    }
}
