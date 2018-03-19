/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Persistence.Factories;

import com.chdryra.android.corelibrary.CacheUtils.InMemoryCache;
import com.chdryra.android.corelibrary.CacheUtils.QueueCache;
import com.chdryra.android.startouch.ApplicationContexts.Interfaces.ModelContext;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Api
        .FactoryQueueCache;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.startouch.Persistence.Implementation.ReviewsCacheHybrid;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsCache;

/**
 * Created by: Rizwan Choudrey
 * On: 31/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewsCache {
    private static final int MAX_REVIEWS_FAST = 10;
    private static final int MAX_REVIEWS_SLOW = 40;

    private final ModelContext mModelContext;
    private final DataValidator mValidator;
    private final FactoryQueueCache mCacheFactory;

    public FactoryReviewsCache(ModelContext modelContext,
                               DataValidator validator,
                               FactoryQueueCache cacheFactory) {
        mModelContext = modelContext;
        mValidator = validator;
        mCacheFactory = cacheFactory;
    }

    ReviewsCache newCache() {
        QueueCache.Cache<Review> persistentCache
                = mCacheFactory.newReviewsCache(mModelContext.getReviewsFactory(), mValidator);
        QueueCache<Review> fastCache = new QueueCache<>(new InMemoryCache<Review>(),
                MAX_REVIEWS_FAST);
        QueueCache<Review> slowCache = new QueueCache<>(persistentCache, MAX_REVIEWS_SLOW);
        return new ReviewsCacheHybrid(fastCache, slowCache);
    }
}
