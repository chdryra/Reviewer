/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Persistence.Factories;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.Persistence.Implementation.ReviewsFeedImpl;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsFeed;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepository;

/**
 * Created by: Rizwan Choudrey
 * On: 16/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewsFeed {
    ReviewsRepository mMainRepo;

    public FactoryReviewsFeed(ReviewsRepository mainRepo) {
        mMainRepo = mainRepo;
    }

    public ReviewsFeed newFeed(DataAuthor author) {
        return new ReviewsFeedImpl(author, mMainRepo);
    }
}
