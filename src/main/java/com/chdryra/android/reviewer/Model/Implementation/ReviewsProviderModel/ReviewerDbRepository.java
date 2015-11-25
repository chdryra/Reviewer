package com.chdryra.android.reviewer.Model.Implementation.ReviewsProviderModel;

import com.chdryra.android.reviewer.Database.Interfaces.ReviewerDb;
import com.chdryra.android.reviewer.Database.Interfaces.ReviewerDbObserver;
import com.chdryra.android.reviewer.Model.Interfaces.Review;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsProviderObserver;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryMutable;
import com.chdryra.android.reviewer.Model.Interfaces.TagsManager;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 30/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewerDbRepository implements ReviewsRepositoryMutable, ReviewerDbObserver {
    private ReviewerDb mDatabase;
    private ArrayList<ReviewsProviderObserver> mObservers;

    //Constructors
    public ReviewerDbRepository(ReviewerDb database) {
        mDatabase = database;
        mObservers = new ArrayList<>();
    }

    //Overridden

    @Override
    public void addReview(Review review) {
        mDatabase.addReviewToDb(review);
    }

    @Override
    public void deleteReview(String reviewId) {
        mDatabase.deleteReviewFromDb(reviewId);
    }

    @Override
    public Review getReview(String reviewId) {
        return mDatabase.loadReviewFromDb(reviewId);
    }

    @Override
    public Iterable<Review> getReviews() {
        return mDatabase.loadReviewsFromDb();
    }

    @Override
    public TagsManager getTagsManager() {
        return mDatabase.getTagsManager();
    }

    @Override
    public void registerObserver(ReviewsProviderObserver observer) {
        mObservers.add(observer);
    }

    @Override
    public void unregisterObserver(ReviewsProviderObserver observer) {
        mObservers.remove(observer);
    }

    @Override
    public void onReviewAdded(Review review) {
        for (ReviewsProviderObserver observer : mObservers) {
            observer.onReviewAdded(review);
        }
    }

    @Override
    public void onReviewDeleted(String reviewId) {
        for (ReviewsProviderObserver observer : mObservers) {
            observer.onReviewRemoved(reviewId);
        }
    }
}
