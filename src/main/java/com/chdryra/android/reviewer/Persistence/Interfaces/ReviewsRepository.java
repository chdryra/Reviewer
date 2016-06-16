/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Persistence.Interfaces;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Persistence.Implementation.RepositoryResult;

/**
 * Created by: Rizwan Choudrey
 * On: 30/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewsRepository {
    void getReviews(RepositoryCallback callback);

    void getReferences(RepositoryCallback callback);

    void getReview(ReviewId reviewId, RepositoryCallback callback);

    void getReference(ReviewId reviewId, RepositoryCallback callback);

    void getReviews(DataAuthor author, RepositoryCallback callback);

    ReviewsRepository getReviews(DataAuthor author);

    void getReferences(DataAuthor author, RepositoryCallback callback);

    TagsManager getTagsManager();
    
    void registerObserver(ReviewsRepositoryObserver observer);

    void unregisterObserver(ReviewsRepositoryObserver observer);

    interface RepositoryCallback {
        void onRepositoryCallback(RepositoryResult result);
    }
}
