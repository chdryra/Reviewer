/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Persistence.Interfaces;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Persistence.Implementation.RepositoryResult;

/**
 * Created by: Rizwan Choudrey
 * On: 30/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewsRepositoryMutable extends ReviewsRepository{
    void addReview(Review review, RepositoryMutableCallback callback);

    void removeReview(ReviewId reviewId, RepositoryMutableCallback callback);

    @Override
    void getReview(ReviewId id, RepositoryCallback callback);

    @Override
    void getReviews(RepositoryCallback callback);

    @Override
    TagsManager getTagsManager();

    @Override
    void registerObserver(ReviewsRepositoryObserver observer);

    @Override
    void unregisterObserver(ReviewsRepositoryObserver observer);

    interface RepositoryMutableCallback {
        void onAddedToRepoCallback(RepositoryResult result);

        void onRemovedFromRepoCallback(RepositoryResult result);
    }
}
