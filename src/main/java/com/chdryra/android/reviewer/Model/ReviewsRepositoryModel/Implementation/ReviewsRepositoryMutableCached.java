/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces.RepositoryMutableCallback;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces.ReviewsRepositoryMutable;
import com.chdryra.android.reviewer.Utils.ReviewsCache;

/**
 * Created by: Rizwan Choudrey
 * On: 31/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewsRepositoryMutableCached
        extends ReviewsRepositoryCached<ReviewsRepositoryMutable>
        implements ReviewsRepositoryMutable {

    public ReviewsRepositoryMutableCached(ReviewsCache cache, ReviewsRepositoryMutable archive) {
        super(cache, archive);
    }

    @Override
    public void addReview(Review review, RepositoryMutableCallback callback) {
        getCache().add(review);
        getArchive().addReview(review, callback);
    }

    @Override
    public void removeReview(ReviewId reviewId, RepositoryMutableCallback callback) {
        getCache().remove(reviewId);
        getArchive().removeReview(reviewId, callback);
    }
}
