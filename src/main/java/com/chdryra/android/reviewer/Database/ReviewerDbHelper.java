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
    private static final ReviewerDbContract CONTRACT   = ReviewerDbContract.getContract();
    private static final DbCreator          DB_CREATOR = new DbCreator(CONTRACT);

    public ReviewerDbHelper(Context context) {
        super(context, CONTRACT.getDatabaseName(), null, CONTRACT.getVersionNumber());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        DB_CREATOR.createDatabase(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        DB_CREATOR.upgradeDatabase(db, oldVersion, newVersion);
    }
}
