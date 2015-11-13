package com.chdryra.android.reviewer.ReviewsProviderModel;

import com.chdryra.android.reviewer.Interfaces.Data.IdableCollection;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure.Review;
import com.chdryra.android.reviewer.Models.TagsModel.TagsManager;

/**
 * Created by: Rizwan Choudrey
 * On: 30/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewsRepository {
    //abstract
    Review getReview(String id);

    IdableCollection<Review> getReviews();

    TagsManager getTagsManager();

    void registerObserver(ReviewsProviderObserver observer);

    void unregisterObserver(ReviewsProviderObserver observer);
}
