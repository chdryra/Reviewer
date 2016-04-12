/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Persistence.Factories;

import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Persistence.Implementation.ReviewsSourceAuthored;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsFeed;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepositoryMutable;

/**
 * Created by: Rizwan Choudrey
 * On: 16/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewsFeed {
    ReviewsRepositoryMutable mMainRepo;

    public FactoryReviewsFeed(ReviewsRepositoryMutable mainRepo) {
        mMainRepo = mainRepo;
    }

    public ReviewsFeed newFeed(FactoryReviews reviewsFactory) {
        return new ReviewsSourceAuthored(mMainRepo, reviewsFactory);
    }
}
