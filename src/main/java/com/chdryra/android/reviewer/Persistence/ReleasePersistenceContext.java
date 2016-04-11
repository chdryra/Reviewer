/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Persistence;

import android.content.Context;
import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.ApplicationContexts.Implementation.PersistenceContextBasic;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ModelContext;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.Persistence.Factories.FactoryReviewsFeed;
import com.chdryra.android.reviewer.Persistence.Factories.FactoryReviewsRepository;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Api.FactoryPersistentCache;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Api.PersistencePlugin;

import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepositoryMutable;
import com.chdryra.android.reviewer.Persistence.Factories.FactoryReviewsCache;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReleasePersistenceContext extends PersistenceContextBasic {

    public ReleasePersistenceContext(Context context,
                                     ModelContext model,
                                     DataValidator validator, PersistencePlugin plugin) {
        setLocalRepository(plugin.newLocalPersistence(context, model, validator));
        setBackendRepository(plugin.newBackendPersistence(context, model, validator));

        FactoryReviewsRepository repoFactory = getFactoryReviewsRepository(context, model, validator, plugin);
        ReviewsRepositoryMutable cachedBackend
                = repoFactory.newCachedMutableRepository(getBackendRepository());

        setFeedFactory(new FactoryReviewsFeed(cachedBackend));
        setReviewsSource(repoFactory.newReviewsSource(cachedBackend, model.getReviewsFactory()));
    }

    @NonNull
    private FactoryReviewsRepository getFactoryReviewsRepository(Context context,
                                                                 ModelContext model,
                                                                 DataValidator validator,
                                                                 PersistencePlugin plugin) {
        FactoryPersistentCache persistentCache = plugin.newCacheFactory();
        FactoryReviewsCache cacheFactory = new FactoryReviewsCache(context, model, validator, persistentCache);
        return new FactoryReviewsRepository(cacheFactory);
    }
}