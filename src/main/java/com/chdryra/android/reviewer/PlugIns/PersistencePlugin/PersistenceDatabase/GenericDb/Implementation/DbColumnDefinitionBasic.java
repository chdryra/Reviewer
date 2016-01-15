package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Implementation;


import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb
        .Interfaces.DbColumnDefinition;

/**
 * Created by: Rizwan Choudrey
 * On: 14/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class DbColumnDefinitionBasic implements DbColumnDefinition {
    private String mColumnName;
    private DbEntryType<?> mType;

    public DbColumnDefinitionBasic(String columnName, DbEntryType<?> type) {
        mColumnName = columnName;
        mType = type;
    }

    @Override
    public String getName() {
        return mColumnName;
    }

    @Override
    public DbEntryType<?> getType() {
        return mType;
    }
}
