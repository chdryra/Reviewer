/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 27 April, 2015
 */

package com.chdryra.android.reviewer.test.TestUtils;

import android.app.Instrumentation;
import android.content.Context;

import com.chdryra.android.reviewer.Database.ReviewerDb;
import com.chdryra.android.reviewer.Model.ReviewIdableList;
import com.chdryra.android.reviewer.Model.ReviewNode;

import java.io.File;

/**
 * Created by: Rizwan Choudrey
 * On: 27/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TestDatabase {
    private static TestDatabase    sDatabase;
    private        ReviewerDb      mDatabase;
    private        Instrumentation mInstr;

    private TestDatabase(Instrumentation instr) {
        mInstr = instr;
        mDatabase = ReviewerDb.getTestDatabase(getDbContext());
    }

    public static ReviewerDb getDatabase(Instrumentation instr) {
        return get(instr).getDatabase();
    }

    public static void recreateDatabase(Instrumentation instr) {
        TestDatabase tdb = get(instr);
        tdb.deleteDatabaseIfNecessary();
        ReviewIdableList<ReviewNode> nodes = TestReviews.getReviews(instr);
        ReviewerDb db = tdb.getDatabase();
        for (ReviewNode node : nodes) {
            db.addReviewTreeToDb(node);
        }
    }

    private static TestDatabase get(Instrumentation instr) {
        if (sDatabase == null) sDatabase = new TestDatabase(instr);
        return sDatabase;
    }

    private ReviewerDb getDatabase() {
        return mDatabase;
    }

    private void deleteDatabaseIfNecessary() {
        Context context = getDbContext();
        File db = context.getDatabasePath(mDatabase.getDatabaseName());
        if (db.exists()) context.deleteDatabase(mDatabase.getDatabaseName());
    }

    private Context getDbContext() {
        return mInstr.getTargetContext();
    }
}
