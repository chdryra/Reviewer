package com.chdryra.android.reviewer.Model.Implementation.ReviewsRepositoryModel;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.Api.TableTransactor;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.ReviewerDb;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.RowReview;
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
    public void addReview(Review review) {
        TableTransactor db = mDatabase.beginWriteTransaction();
        boolean success = mDatabase.addReviewToDb(review, db);
        mDatabase.endTransaction(db);

        if (success) notifyOnAddReview(review);
    }

    @Override
    public Review getReview(ReviewId reviewId) {
        TableTransactor db = mDatabase.beginReadTransaction();

        String colName = RowReview.REVIEW_ID.getName();
        String colVal = reviewId.toString();
        ArrayList<Review> reviews = mDatabase.loadReviewsWhere(db, colName, colVal);

        mDatabase.endTransaction(db);

        if(reviews.size() > 1) {
            throw new IllegalStateException("There is more than 1 review with id " + reviewId);
        }

        return reviews.size() == 1 ? reviews.get(0) : null;
    }

    @Override
    public ArrayList<Review> getReviews() {
        TableTransactor db = mDatabase.beginReadTransaction();

        String colName = RowReview.PARENT_ID.getName();
        ArrayList<Review> reviews = mDatabase.loadReviewsWhere(db, colName, null);

        mDatabase.endTransaction(db);

        return reviews;
    }

    @Override
    public void removeReview(ReviewId reviewId) {
        TableTransactor db = mDatabase.beginWriteTransaction();
        boolean success = mDatabase.deleteReviewFromDb(reviewId.toString(), db);
        mDatabase.endTransaction(db);
        if (success) notifyOnDeleteReview(reviewId);
    }

    @Override
    public void registerObserver(ReviewsRepositoryObserver observer) {
        mObservers.add(observer);
    }

    @Override
    public void unregisterObserver(ReviewsRepositoryObserver observer) {
        mObservers.remove(observer);
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
