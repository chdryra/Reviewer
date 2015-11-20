package com.chdryra.android.reviewer.Models.ReviewsProviderModel.Implementation;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.IdableCollection;
import com.chdryra.android.reviewer.Models.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Models.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Models.ReviewsProviderModel.Interfaces.ReviewsProviderObserver;
import com.chdryra.android.reviewer.Models.ReviewsProviderModel.Interfaces.ReviewsRepository;

/**
 * Created by: Rizwan Choudrey
 * On: 17/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class StaticReviewsRepository implements ReviewsRepository {
    private IdableCollection<Review> mReviews;
    private TagsManager mTagsManager;

    public StaticReviewsRepository(IdableCollection<Review> reviews, TagsManager tagsManager) {
        mReviews = reviews;
        mTagsManager = tagsManager;
    }

    @Override
    public Review getReview(String reviewId) {
        Review result = null;
        for(Review review : mReviews) {
            if(review.getReviewId().equals(reviewId)) {
                result = review;
                break;
            }
        }

        return result;
    }

    @Override
    public IdableCollection<Review> getReviews() {
        return mReviews;
    }

    @Override
    public TagsManager getTagsManager() {
        return mTagsManager;
    }

    //static list so unnecessary
    @Override
    public void registerObserver(ReviewsProviderObserver observer) {

    }

    @Override
    public void unregisterObserver(ReviewsProviderObserver observer) {

    }
}
