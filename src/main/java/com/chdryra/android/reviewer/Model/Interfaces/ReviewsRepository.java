package com.chdryra.android.reviewer.Model.Interfaces;

/**
 * Created by: Rizwan Choudrey
 * On: 30/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewsRepository {
    //abstract
    Review getReview(String id);

    Iterable<Review> getReviews();

    TagsManager getTagsManager();

    void registerObserver(ReviewsRepositoryObserver observer);

    void unregisterObserver(ReviewsRepositoryObserver observer);
}
