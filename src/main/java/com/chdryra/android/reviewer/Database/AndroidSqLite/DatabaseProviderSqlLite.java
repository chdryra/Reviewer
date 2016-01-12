/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 April, 2015
 */

package com.chdryra.android.reviewer.Database.AndroidSqLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.chdryra.android.reviewer.Database.Factories.FactoryReviewerDbTableRow;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DatabaseProvider;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbContract;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbSpecification;
import com.chdryra.android.reviewer.Database.Interfaces.DatabaseInstance;

/**
 * Created by: Rizwan Choudrey
 * On: 08/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DatabaseProviderSqlLite<T extends DbContract> extends SQLiteOpenHelper
        implements DatabaseProvider<T> {
    private T mContract;
    private SqlLiteContractExecutor mDbContractExecutor;
    private FactorySqLiteDatabaseInstance mInstanceFactory;

    public DatabaseProviderSqlLite(Context context,
                                   DbSpecification<T> spec,
                                   SqlLiteContractExecutor dbContractExecutor,
                                   FactorySqLiteDatabaseInstance instanceFactory) {
        super(context, spec.getDatabaseName(), null, spec.getVersionNumber());
        mContract = spec.getContract();
        mDbContractExecutor = dbContractExecutor;
        mInstanceFactory = instanceFactory;
    }

    @Override
    public DatabaseInstance getReadableInstance(FactoryReviewerDbTableRow rowFactory) {
        return mInstanceFactory.newInstance(super.getReadableDatabase(), rowFactory);
    }

    @Override
    public DatabaseInstance getWriteableInstance(FactoryReviewerDbTableRow rowFactory) {
        return mInstanceFactory.newInstance(super.getWritableDatabase(), rowFactory);
    }

    @Override
    public T getContract() {
        return mContract;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        mDbContractExecutor.createDatabase(mContract, db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        mDbContractExecutor.upgradeDatabase(mContract, db, oldVersion, newVersion);
    }
}
