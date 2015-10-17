package com.chdryra.android.reviewer.ReviewsProviderModel;

import com.chdryra.android.reviewer.Model.ReviewData.IdableList;
import com.chdryra.android.reviewer.Model.ReviewData.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewStructure.Review;
import com.chdryra.android.reviewer.Model.TagsModel.TagsManager;

/**
 * Created by: Rizwan Choudrey
 * On: 17/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class StaticReviewsProvider implements ReviewsProvider {
    private IdableList<Review> mReviews;
    private TagsManager mTagsManager;

    public StaticReviewsProvider(IdableList<Review> reviews, TagsManager tagsManager) {
        mReviews = reviews;
        mTagsManager = tagsManager;
    }

    @Override
    public Review getReview(ReviewId id) {
        return mReviews.get(id);
    }

    @Override
    public IdableList<Review> getReviews() {
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
