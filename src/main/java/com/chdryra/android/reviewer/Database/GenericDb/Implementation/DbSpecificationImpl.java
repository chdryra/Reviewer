package com.chdryra.android.reviewer.Database.GenericDb.Implementation;

import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbContract;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbSpecification;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DbSpecificationImpl<T extends DbContract> implements DbSpecification<T> {
    private String mDatabaseName;
    private T mContract;
    private int mVersionNumber;

    public DbSpecificationImpl(T contract, String databaseName, int versionNumber) {
        mDatabaseName = databaseName;
        mContract = contract;
        mVersionNumber = versionNumber;
    }

    @Override
    public String getDatabaseName() {
        return mDatabaseName;
    }

    @Override
    public T getContract() {
        return mContract;
    }

    @Override
    public int getVersionNumber() {
        return mVersionNumber;
    }
}
