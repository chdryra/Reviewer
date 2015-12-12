package com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.TagsManager;

/**
 * Created by: Rizwan Choudrey
 * On: 30/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewsRepository {
    @Nullable
    Review getReview(ReviewId id);

    Iterable<Review> getReviews();

    TagsManager getTagsManager();
    
    void registerObserver(ReviewsRepositoryObserver observer);

    void unregisterObserver(ReviewsRepositoryObserver observer);
}
