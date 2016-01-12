/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 April, 2015
 */

package com.chdryra.android.reviewer.PlugIns.DatabaseAndroidSqLite.Implementation;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.chdryra.android.reviewer.PlugIns.DatabaseAndroidSqLite.Factories.FactoryTableTransactorSqLite;
import com.chdryra.android.reviewer.PlugIns.DatabaseAndroidSqLite.Interfaces.SqlLiteContractExecutor;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.FactoryDbTableRow;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.ContractedTableTransactor;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbContract;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbSpecification;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.TableTransactor;

/**
 * Created by: Rizwan Choudrey
 * On: 08/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ContractedSqlLiteTransactor<T extends DbContract> extends SQLiteOpenHelper
        implements ContractedTableTransactor<T> {
    private T mContract;
    private SqlLiteContractExecutor mContractExecutor;
    private FactoryTableTransactorSqLite mFactory;

    public ContractedSqlLiteTransactor(Context context,
                                       DbSpecification<T> spec,
                                       SqlLiteContractExecutor contractExecutor,
                                       FactoryTableTransactorSqLite factory) {
        super(context, spec.getDatabaseName(), null, spec.getVersionNumber());
        mContract = spec.getContract();
        mContractExecutor = contractExecutor;
        mFactory = factory;
    }

    @Override
    public TableTransactor getReadableTransactor(FactoryDbTableRow rowFactory) {
        return mFactory.newInstance(super.getReadableDatabase(), rowFactory);
    }

    @Override
    public TableTransactor getWriteableTransactor(FactoryDbTableRow rowFactory) {
        return mFactory.newInstance(super.getWritableDatabase(), rowFactory);
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
