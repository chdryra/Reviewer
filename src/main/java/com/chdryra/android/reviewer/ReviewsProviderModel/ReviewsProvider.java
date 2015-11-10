package com.chdryra.android.reviewer.ReviewsProviderModel;

import com.chdryra.android.reviewer.Model.ReviewData.MdIdableList;
import com.chdryra.android.reviewer.Model.ReviewData.MdReviewId;
import com.chdryra.android.reviewer.Model.ReviewStructure.Review;
import com.chdryra.android.reviewer.Model.TagsModel.TagsManager;

/**
 * Created by: Rizwan Choudrey
 * On: 30/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewsProvider {
    //abstract
    Review getReview(MdReviewId id);

    MdIdableList<Review> getReviews();

    TagsManager getTagsManager();

    void registerObserver(ReviewsProviderObserver observer);

    void unregisterObserver(ReviewsProviderObserver observer);
}
