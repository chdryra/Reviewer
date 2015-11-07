/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 April, 2015
 */

package com.chdryra.android.reviewer.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by: Rizwan Choudrey
 * On: 08/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DbHelper extends SQLiteOpenHelper {
    private DbContract mContract;
    private DbContractExecutor mDbContractExecutor;

    //Constructors
    public DbHelper(Context context, DbSpecification spec, DbContractExecutor dbContractExecutor) {
        super(context, spec.getDatabaseName(), null, spec.getVersionNumber());
        mContract = spec.getContract();
        mDbContractExecutor = dbContractExecutor;
    }

    //Overridden
    @Override
    public void onCreate(SQLiteDatabase db) {
        mDbContractExecutor.createDatabase(mContract, db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        mDbContractExecutor.upgradeDatabase(mContract, db, oldVersion, newVersion);
    }
}
