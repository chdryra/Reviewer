package com.chdryra.android.reviewer.Database;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DbSpecification<T extends DbContract> {
    private String mDatabaseName;
    private T mContract;
    private int mVersionNumber;

    public DbSpecification(String databaseName, T contract, int versionNumber) {
        mDatabaseName = databaseName;
        mContract = contract;
        mVersionNumber = versionNumber;
    }

    public String getDatabaseName() {
        return mDatabaseName;
    }

    public T getContract() {
        return mContract;
    }

    public int getVersionNumber() {
        return mVersionNumber;
    }
}
