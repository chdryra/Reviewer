package com.chdryra.android.reviewer.Model.Implementation.ReviewsRepositoryModel;

import android.database.sqlite.SQLiteDatabase;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Database.Interfaces.ReviewerDb;
import com.chdryra.android.reviewer.Database.Interfaces.RowReview;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel.ReviewsRepositoryMutable;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel.ReviewsRepositoryObserver;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.TagsManager;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 30/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewerDbRepository implements ReviewsRepositoryMutable{
    private final ReviewerDb mDatabase;
    private final ArrayList<ReviewsRepositoryObserver> mObservers;

    public ReviewerDbRepository(ReviewerDb database) {
        mDatabase = database;
        mObservers = new ArrayList<>();
    }

    @Override
    public TagsManager getTagsManager() {
        return mDatabase.getTagsManager();
    }

    @Override
    public void registerObserver(ReviewsRepositoryObserver observer) {
        mObservers.add(observer);
    }

    @Override
    public void unregisterObserver(ReviewsRepositoryObserver observer) {
        mObservers.remove(observer);
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
        for (ReviewsRepositoryObserver observer : mObservers) {
            observer.onReviewAdded(review);
        }
    }

    private void notifyOnDeleteReview(ReviewId reviewId) {
        for (ReviewsRepositoryObserver observer : mObservers) {
            observer.onReviewRemoved(reviewId);
        }
    }
}
