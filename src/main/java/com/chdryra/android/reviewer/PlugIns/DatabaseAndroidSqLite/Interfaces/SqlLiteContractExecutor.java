/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 31 March, 2015
 */

package com.chdryra.android.reviewer.PlugIns.DatabaseAndroidSqLite.Interfaces;

import android.database.sqlite.SQLiteDatabase;

import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbContract;

/**
 * Created by: Rizwan Choudrey
 * On: 31/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface SqlLiteContractExecutor {
    void createDatabase(DbContract contract, SQLiteDatabase db);

    void upgradeDatabase(DbContract contract, SQLiteDatabase db, int oldVersion, int newVersion);
}
