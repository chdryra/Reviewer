/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Implementation;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Interfaces.DbContract;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Interfaces.DbSpecification;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DbSpecificationImpl<T extends DbContract> implements DbSpecification<T> {
    private final T mContract;
    private final String mDatabaseName;
    private final int mVersionNumber;

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
