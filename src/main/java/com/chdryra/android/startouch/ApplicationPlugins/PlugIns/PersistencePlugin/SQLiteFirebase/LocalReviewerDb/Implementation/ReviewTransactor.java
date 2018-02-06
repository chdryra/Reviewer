/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation;



import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Api.TableTransactor;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces.ReviewDeleterDb;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces.ReviewInserter;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces.ReviewLoader;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces.ReviewerDb;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces.ReviewerDbReadable;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces.RowReview;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.Review;

/**
 * Created by: Rizwan Choudrey
 * On: 24/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewTransactor implements ReviewInserter, ReviewDeleterDb, ReviewLoader{
    private final ReviewLoader mLoader;
    private final ReviewInserter mInserter;
    private final ReviewDeleterDb mDeleter;

    public ReviewTransactor(ReviewLoader loader, ReviewInserter inserter, ReviewDeleterDb deleter) {
        mLoader = loader;
        mInserter = inserter;
        mDeleter = deleter;
    }

    @Override
    public boolean deleteReviewFromDb(RowReview row, ReviewerDb db, TableTransactor transactor) {
        return mDeleter.deleteReviewFromDb(row, db, transactor);
    }

    @Override
    public void addReviewToDb(Review review, ReviewerDb db, TableTransactor transactor) {
        mInserter.addReviewToDb(review, db, transactor);
    }

    @Override
    public Review loadReview(RowReview reviewRow, ReviewerDbReadable database, TableTransactor db) {
        return mLoader.loadReview(reviewRow, database, db);
    }
}
