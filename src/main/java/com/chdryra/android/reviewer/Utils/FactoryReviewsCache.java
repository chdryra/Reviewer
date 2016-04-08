/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Utils;

import android.content.Context;

import com.chdryra.android.mygenerallibrary.CacheUtils.InMemoryCache;
import com.chdryra.android.mygenerallibrary.CacheUtils.QueueCache;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ModelContext;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.Api.FactoryPersistentCache;

/**
 * Created by: Rizwan Choudrey
 * On: 31/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewsCache {
    private static final int MAX_REVIEWS_FAST = 10;
    private static final int MAX_REVIEWS_SLOW = 40;

    private Context mContext;
    private ModelContext mModelContext;
    private FactoryPersistentCache mCacheFactory;

    public FactoryReviewsCache(Context context, ModelContext modelContext, FactoryPersistentCache
            cacheFactory) {
        mContext = context;
        mModelContext = modelContext;
        mCacheFactory = cacheFactory;
    }

    public ReviewsCache newCache() {
        QueueCache.Cache<Review> persistentCache
                = mCacheFactory.newReviewsCache(mContext, mModelContext);
        QueueCache<Review> slowCache = new QueueCache<>(persistentCache, MAX_REVIEWS_SLOW);
        QueueCache<Review> fastCache = new QueueCache<>(new InMemoryCache<Review>(), MAX_REVIEWS_FAST);
        return new ReviewsCacheHybrid(slowCache, fastCache);
    }
}
