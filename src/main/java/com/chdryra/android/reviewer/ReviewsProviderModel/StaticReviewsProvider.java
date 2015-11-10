package com.chdryra.android.reviewer.ReviewsProviderModel;

import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdIdableCollection;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdReviewId;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure.Review;
import com.chdryra.android.reviewer.Models.TagsModel.TagsManager;

/**
 * Created by: Rizwan Choudrey
 * On: 17/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class StaticReviewsProvider implements ReviewsProvider {
    private MdIdableCollection<Review> mReviews;
    private TagsManager mTagsManager;

    public StaticReviewsProvider(MdIdableCollection<Review> reviews, TagsManager tagsManager) {
        mReviews = reviews;
        mTagsManager = tagsManager;
    }

    @Override
    public Review getReview(MdReviewId id) {
        return mReviews.get(id);
    }

    @Override
    public MdIdableCollection<Review> getReviews() {
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
