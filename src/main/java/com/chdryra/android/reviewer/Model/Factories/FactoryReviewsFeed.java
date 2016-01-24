/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.Factories;

import com.chdryra.android.reviewer.Model.Implementation.ReviewsRepositoryModel.ReviewsSourceAuthoredMutable;
import com.chdryra.android.reviewer.Model.Implementation.TreeMethods.Interfaces.TreeFlattener;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel.ReviewsFeedMutable;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel.ReviewsRepositoryMutable;

/**
 * Created by: Rizwan Choudrey
 * On: 16/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewsFeed {
    FactoryReviews mReviewFactory;
    TreeFlattener mFlattener;

    public FactoryReviewsFeed(FactoryReviews reviewFactory,
                              TreeFlattener flattener) {
        mReviewFactory = reviewFactory;
        mFlattener = flattener;
    }

    public ReviewsFeedMutable newMutableFeed(ReviewsRepositoryMutable sourceAndDestination) {
        return new ReviewsSourceAuthoredMutable(sourceAndDestination, mReviewFactory, mFlattener);
    }
}
