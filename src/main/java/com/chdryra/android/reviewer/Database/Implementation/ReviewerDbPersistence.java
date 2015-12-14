/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 1 April, 2015
 */

package com.chdryra.android.reviewer.Database.Implementation;

import android.database.sqlite.SQLiteDatabase;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Database.Interfaces.ReviewerDb;
import com.chdryra.android.reviewer.Database.Interfaces.ReviewerPersistence;
import com.chdryra.android.reviewer.Database.Interfaces.ReviewerPersistenceObserver;
import com.chdryra.android.reviewer.Database.Interfaces.RowReview;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.TagsManager;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 01/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewerDbPersistence implements ReviewerPersistence {
    private static final String TAG = "ReviewerDb";

    private final ReviewerDb mDatabase;
    private final ArrayList<ReviewerPersistenceObserver> mObservers;

    public ReviewerDbPersistence(ReviewerDb database) {
        mDatabase = database;
        mObservers = new ArrayList<>();
    }

    @Override
    public TagsManager getTagsManager() {
        return mDatabase.getTagsManager();
    }

    @Override
    public void registerObserver(ReviewerPersistenceObserver observer) {
        mObservers.add(observer);
    }

    @Override
    public void addReview(Review review) {
        SQLiteDatabase db = mDatabase.beginReadTransaction();
        boolean success = mDatabase.addReviewToDb(review, db);
        mDatabase.endTransaction(db);

        if (success) notifyOnAddReview(review);
    }

    @Override
    public Review getReview(ReviewId reviewId) {
        SQLiteDatabase db = mDatabase.beginReadTransaction();
        ArrayList<Review> reviews = mDatabase.loadReviewsFromDbWhere(db, RowReview.COLUMN_REVIEW_ID,
                reviewId.toString());
        mDatabase.endTransaction(db);

        if(reviews.size() > 1) {
            throw new IllegalStateException("There is more than 1 review with id " + reviewId);
        }

        return reviews.size() == 1 ? reviews.get(0) : null;
    }

    @Override
    public ArrayList<Review> getReviews() {
        SQLiteDatabase db = mDatabase.beginReadTransaction();
        ArrayList<Review> reviews = mDatabase.loadReviewsFromDbWhere(db, RowReview.COLUMN_PARENT_ID,
                null);
        mDatabase.endTransaction(db);

        return reviews;
    }

    @Override
    public void removeReview(ReviewId reviewId) {
        SQLiteDatabase db = mDatabase.beginWriteTransaction();
        boolean success = mDatabase.deleteReviewFromDb(reviewId.toString(), db);
        mDatabase.endTransaction(db);
        if (success) notifyOnDeleteReview(reviewId);
    }


    private void notifyOnAddReview(Review review) {
        for (ReviewerPersistenceObserver observer : mObservers) {
            observer.onReviewAdded(review);
        }
    }

    private void notifyOnDeleteReview(ReviewId reviewId) {
        for (ReviewerPersistenceObserver observer : mObservers) {
            observer.onReviewRemoved(reviewId);
        }
    }
}
