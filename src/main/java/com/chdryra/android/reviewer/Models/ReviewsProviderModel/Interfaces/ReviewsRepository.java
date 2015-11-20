package com.chdryra.android.reviewer.Models.ReviewsProviderModel.Interfaces;

import com.chdryra.android.reviewer.Models.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Models.TagsModel.Interfaces.TagsManager;

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

    void registerObserver(ReviewsProviderObserver observer);

    void unregisterObserver(ReviewsProviderObserver observer);
}
