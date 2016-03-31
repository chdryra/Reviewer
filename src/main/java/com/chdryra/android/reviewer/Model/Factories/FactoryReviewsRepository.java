/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.Factories;

import android.content.Context;

import com.chdryra.android.mygenerallibrary.QueueCache;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ModelContext;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Implementation
        .ReviewsRepositoryMutableCached;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Implementation.ReviewsSourceImpl;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces.ReviewsRepository;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces
        .ReviewsRepositoryMutable;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces.ReviewsSource;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.Api.FactoryPersistentCache;
import com.chdryra.android.reviewer.Utils.FactoryReviewsCache;
import com.chdryra.android.reviewer.Utils.ReviewsCache;

/**
 * Created by: Rizwan Choudrey
 * On: 16/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewsRepository {
    private FactoryReviewsCache mCacheFactory;

    public FactoryReviewsRepository(FactoryReviewsCache cacheFactory) {
        mCacheFactory = cacheFactory;
    }

    public ReviewsSource newReviewsSource(ReviewsRepository repository,
                                          FactoryReviews reviewsFactory) {
        return new ReviewsSourceImpl(repository, reviewsFactory);
    }

    public ReviewsRepositoryMutable newCachedMutableRepository(ReviewsRepositoryMutable archive) {
        return new ReviewsRepositoryMutableCached(mCacheFactory.newCache(), archive);
    }
}
