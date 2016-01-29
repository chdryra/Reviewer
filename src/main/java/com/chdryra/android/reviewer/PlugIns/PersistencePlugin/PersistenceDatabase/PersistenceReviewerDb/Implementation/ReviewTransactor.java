/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Implementation;


import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.TagsManager;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.Api.TableTransactor;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Interfaces.ReviewDeleter;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Interfaces.ReviewInserter;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Interfaces.ReviewLoader;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Interfaces.ReviewerDb;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Interfaces.ReviewerDbReadable;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Interfaces.RowReview;

/**
 * Created by: Rizwan Choudrey
 * On: 24/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewTransactor {
    private ReviewLoader mLoader;
    private ReviewInserter mInserter;
    private ReviewDeleter mDeleter;
    private TagsManager mTagsManager;

    public ReviewTransactor(ReviewLoader loader, ReviewInserter inserter, ReviewDeleter deleter,
                            TagsManager tagsManager) {
        mLoader = loader;
        mInserter = inserter;
        mDeleter = deleter;
        mTagsManager = tagsManager;
    }

    public boolean deleteReviewFromDb(RowReview row, ReviewerDb db, TableTransactor transactor) {
        return mDeleter.deleteReviewFromDb(row, mTagsManager, db, transactor);
    }

    public void addReviewToDb(Review review, ReviewerDb db, TableTransactor transactor) {
        mInserter.addReviewToDb(review, mTagsManager, db, transactor);
    }

    public Review loadReview(RowReview reviewRow, ReviewerDbReadable database, TableTransactor db) {
        return mLoader.loadReview(reviewRow, database, db);
    }
}
