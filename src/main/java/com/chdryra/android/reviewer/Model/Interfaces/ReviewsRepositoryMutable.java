package com.chdryra.android.reviewer.Model.Interfaces;

/**
 * Created by: Rizwan Choudrey
 * On: 30/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewsRepositoryMutable extends ReviewsRepository{
    void addReview(Review review);

    void deleteReview(String reviewId);

    @Override
    Review getReview(String id);

    @Override
    Iterable<Review> getReviews();

    @Override
    TagsManager getTagsManager();

    @Override
    void registerObserver(ReviewsRepositoryObserver observer);

    @Override
    void unregisterObserver(ReviewsRepositoryObserver observer);
}
