package com.chdryra.android.reviewer.Database;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DbSpecification {
    private String mDatabaseName;
    private DbContract mContract;
    private int mVersionNumber;

    public DbSpecification(String databaseName, DbContract contract, int versionNumber) {
        mDatabaseName = databaseName;
        mContract = contract;
        mVersionNumber = versionNumber;
    }

    public String getDatabaseName() {
        return mDatabaseName;
    }

    public DbContract getContract() {
        return mContract;
    }

    public int getVersionNumber() {
        return mVersionNumber;
    }
}
