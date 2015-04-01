/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 30 March, 2015
 */

package com.chdryra.android.reviewer.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by: Rizwan Choudrey
 * On: 30/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewerDbHelper extends SQLiteOpenHelper {
    private DbCreator mDbCreator;

    public ReviewerDbHelper(Context context, DbContract contract) {
        super(context, contract.getDatabaseName(), null, contract.getVersionNumber());
        mDbCreator = new DbCreator(contract);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        mDbCreator.createDatabase(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        mDbCreator.upgradeDatabase(db, oldVersion, newVersion);
    }
}
