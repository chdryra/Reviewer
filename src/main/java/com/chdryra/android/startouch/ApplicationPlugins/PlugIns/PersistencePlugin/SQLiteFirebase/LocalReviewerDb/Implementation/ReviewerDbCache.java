/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation;



import com.chdryra.android.mygenerallibrary.CacheUtils.QueueCache;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Api.TableTransactor;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Interfaces.RowEntry;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces.ReviewerDb;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces.RowReview;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumReviewId;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.Review;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by: Rizwan Choudrey
 * On: 30/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
class ReviewerDbCache implements QueueCache.Cache<Review> {
    private final ReviewerDb mDb;

    ReviewerDbCache(ReviewerDb db) {
        mDb = db;
    }

    @Override
    public void put(String id, Review review) {
        if (!id.equals(review.getReviewId().toString())) {
            throw new IllegalArgumentException("Id must be ReviewId");
        }

        TableTransactor db = mDb.beginWriteTransaction();
        mDb.addReviewToDb(review, db);
        getReview(id, db);
        mDb.endTransaction(db);
    }

    @Override
    public Review get(String id) {
        TableTransactor transactor = mDb.beginReadTransaction();
        Review review = getReview(id, transactor);
        mDb.endTransaction(transactor);

        return review;
    }

    @Override
    public Review remove(String id) {
        TableTransactor transactor = mDb.beginWriteTransaction();
        Review review = getReview(id, transactor);
        mDb.deleteReviewFromDb(new DatumReviewId(id), transactor);
        mDb.endTransaction(transactor);

        return review;
    }

    private Review getReview(String id, TableTransactor transactor) {
        RowEntry<RowReview, String> clause
                = new RowEntryImpl<>(RowReview.class, RowReview.REVIEW_ID, id);

        Collection<Review> reviews = mDb.loadReviewsWhere(mDb.getReviewsTable(), clause, transactor);

        Iterator<Review> iterator = reviews.iterator();

        Review review;
        if (iterator.hasNext()) {
            review = iterator.next();
        } else {
            throw new IllegalArgumentException("Id not found: " + id);
        }

        return review;
    }
}
