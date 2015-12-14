package com.chdryra.android.reviewer.Model.Implementation.ReviewsRepositoryModel;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Database.Interfaces.ReviewerPersistence;
import com.chdryra.android.reviewer.Database.Interfaces.ReviewerPersistenceObserver;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel
        .ReviewsRepositoryMutable;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel
        .ReviewsRepositoryObserver;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.TagsManager;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 30/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewerPersistenceRepository implements ReviewsRepositoryMutable,
        ReviewerPersistenceObserver {
    private ReviewerPersistence mDatabase;
    private ArrayList<ReviewsRepositoryObserver> mObservers;

    public ReviewerPersistenceRepository(ReviewerPersistence database) {
        mDatabase = database;
        mDatabase.registerObserver(this);
        mObservers = new ArrayList<>();
    }

    @Override
    public void addReview(Review review) {
        mDatabase.addReview(review);
    }

    @Override
    public void removeReview(ReviewId reviewId) {
        mDatabase.removeReview(reviewId);
    }

    @Override
    public Review getReview(ReviewId reviewId) {
        return mDatabase.getReview(reviewId);
    }

    @Override
    public Iterable<Review> getReviews() {
        return mDatabase.getReviews();
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
    public void onReviewAdded(Review review) {
        for (ReviewsRepositoryObserver observer : mObservers) {
            observer.onReviewAdded(review);
        }
    }

    @Override
    public void onReviewRemoved(ReviewId reviewId) {
        for (ReviewsRepositoryObserver observer : mObservers) {
            observer.onReviewRemoved(reviewId);
        }
    }
}
