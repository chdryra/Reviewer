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

import com.chdryra.android.reviewer.ApplicationSingletons.Administrator;
import com.chdryra.android.reviewer.Database.ReviewerDb;
import com.chdryra.android.reviewer.Model.ReviewData.IdableList;
import com.chdryra.android.reviewer.Model.ReviewStructure.Review;
import com.chdryra.android.reviewer.Model.TagsModel.TagsManager;

import java.io.File;

/**
 * Created by: Rizwan Choudrey
 * On: 27/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TestDatabase {
    private static TestDatabase sDatabase;
    private ReviewerDb mDatabase;
    private Instrumentation mInstr;

    private TestDatabase(Instrumentation instr) {
        mInstr = instr;
        TagsManager tagsManager = Administrator.get(getDbContext()).getTagsManager();
        mDatabase = ReviewerDb.getTestDatabase(getDbContext(), tagsManager);
    }

    //Static methods
    public static ReviewerDb getDatabase(Instrumentation instr) {
        return get(instr).getDatabase();
    }

    public static void recreateDatabase(Instrumentation instr) {
        TestDatabase tdb = get(instr);
        tdb.deleteDatabaseIfNecessary();
        IdableList<Review> reviews = TestReviews.getReviews(instr);
        ReviewerDb db = tdb.getDatabase();
        for (Review review : reviews) {
            db.addReviewToDb(review);
        }
    }

    public static void deleteDatabase(Instrumentation instr) {
        get(instr).deleteDatabaseIfNecessary();
    }

    private static TestDatabase get(Instrumentation instr) {
        if (sDatabase == null) sDatabase = new TestDatabase(instr);
        return sDatabase;
    }

    //private methods
    private ReviewerDb getDatabase() {
        return mDatabase;
    }

    private Context getDbContext() {
        return mInstr.getTargetContext();
    }

    private void deleteDatabaseIfNecessary() {
        Context context = getDbContext();
        File db = context.getDatabasePath(mDatabase.getDatabaseName());
        if (db.exists()) context.deleteDatabase(mDatabase.getDatabaseName());
    }
}
