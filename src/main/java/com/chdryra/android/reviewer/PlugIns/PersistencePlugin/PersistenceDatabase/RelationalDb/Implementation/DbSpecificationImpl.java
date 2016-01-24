package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb.Implementation;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb.Interfaces.DbContract;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb.Interfaces.DbSpecification;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DbSpecificationImpl<T extends DbContract> implements DbSpecification<T> {
    private T mContract;
    private String mDatabaseName;
    private int mVersionNumber;

    public DbSpecificationImpl(T contract, String databaseName, int versionNumber) {
        mContract = contract;
        mDatabaseName = databaseName;
        mVersionNumber = versionNumber;
    }

    @Override
    public T getContract() {
        return mContract;
    }

    @Override
    public String getDatabaseName() {
        return mDatabaseName;
    }

    @Override
    public int getVersionNumber() {
        return mVersionNumber;
    }
}
