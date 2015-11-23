package com.chdryra.android.reviewer.Models.ReviewsProviderModel.Interfaces;

import com.chdryra.android.reviewer.Models.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Models.TagsModel.Interfaces.TagsManager;

/**
 * Created by: Rizwan Choudrey
 * On: 30/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewsRepositoryMutable extends ReviewsRepository{
    public void addReview(Review review);

    public void deleteReview(String reviewId);

    @Override
    Review getReview(String id);

    @Override
    Iterable<Review> getReviews();

    @Override
    TagsManager getTagsManager();

    @Override
    void registerObserver(ReviewsProviderObserver observer);

    @Override
    void unregisterObserver(ReviewsProviderObserver observer);
}
