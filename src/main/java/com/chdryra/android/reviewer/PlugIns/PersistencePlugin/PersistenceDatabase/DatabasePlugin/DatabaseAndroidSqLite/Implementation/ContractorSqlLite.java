/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 April, 2015
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.DatabaseAndroidSqLite.Implementation;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.Api.ContractorDb;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Interfaces.DbContract;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Interfaces.DbSpecification;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.TableTransactor;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.DatabaseAndroidSqLite.Factories.FactoryTransactorSqLite;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.DatabaseAndroidSqLite.Interfaces.SqlLiteContractExecutor;

/**
 * Created by: Rizwan Choudrey
 * On: 08/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ContractorSqlLite<T extends DbContract> extends SQLiteOpenHelper
        implements ContractorDb<T> {
    private T mContract;
    private SqlLiteContractExecutor mContractExecutor;
    private FactoryTransactorSqLite mFactory;

    public ContractorSqlLite(Context context,
                             DbSpecification<T> spec,
                             SqlLiteContractExecutor contractExecutor,
                             FactoryTransactorSqLite factory) {
        super(context, spec.getDatabaseName(), null, spec.getVersionNumber());
        mContract = spec.getContract();
        mContractExecutor = contractExecutor;
        mFactory = factory;
    }

    @Override
    public TableTransactor getReadableTransactor() {
        return mFactory.newTransactor(super.getReadableDatabase());
    }

    @Override
    public TableTransactor getWriteableTransactor() {
        return mFactory.newTransactor(super.getWritableDatabase());
    }

    @Override
    public T getContract() {
        return mContract;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        mContractExecutor.createDatabase(mContract, db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        mContractExecutor.upgradeDatabase(mContract, db, oldVersion, newVersion);
    }
}
