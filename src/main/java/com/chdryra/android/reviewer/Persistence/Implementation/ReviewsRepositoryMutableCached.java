/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Persistence.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Persistence.Interfaces.CallbackRepositoryMutable;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepositoryMutable;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsCache;

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
    public void addReview(Review review, CallbackRepositoryMutable callback) {
        getCache().add(review);
        getArchive().addReview(review, callback);
    }

    @Override
    public void removeReview(ReviewId reviewId, CallbackRepositoryMutable callback) {
        getCache().remove(reviewId);
        getArchive().removeReview(reviewId, callback);
    }
}
