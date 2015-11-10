package com.chdryra.android.reviewer.ReviewsProviderModel;

import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdIdableCollection;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdReviewId;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure.Review;
import com.chdryra.android.reviewer.Models.TagsModel.TagsManager;

/**
 * Created by: Rizwan Choudrey
 * On: 30/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewsProvider {
    //abstract
    Review getReview(MdReviewId id);

    MdIdableCollection<Review> getReviews();

    TagsManager getTagsManager();

    void registerObserver(ReviewsProviderObserver observer);

    void unregisterObserver(ReviewsProviderObserver observer);
}
