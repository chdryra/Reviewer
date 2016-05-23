/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Persistence;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.ApplicationContexts.Implementation.PersistenceContextBasic;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ModelContext;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Api.PersistencePlugin;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.Persistence.Factories.FactoryReviewsCache;
import com.chdryra.android.reviewer.Persistence.Factories.FactoryReviewsFeed;
import com.chdryra.android.reviewer.Persistence.Factories.FactoryReviewsRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsCache;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepositoryMutable;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReleasePersistenceContext extends PersistenceContextBasic {

    public ReleasePersistenceContext(ModelContext model,
                                     DataValidator validator,
                                     PersistencePlugin plugin) {

        setLocalRepository(plugin.newLocalPersistence(model, validator));

        setUsersManager(plugin.newUsersManager());

        FactoryReviewsRepository repoFactory = newRepoFactory(model, validator, plugin);
        ReviewsCache reviewsCache = repoFactory.newCache();
        setBackendRepository(plugin.newBackendPersistence(model, validator, reviewsCache));

        ReviewsRepositoryMutable cachedRepo = repoFactory.newCachedRepo(getBackendRepository(), reviewsCache);
        setFeedFactory(new FactoryReviewsFeed(cachedRepo));
        setReviewsSource(repoFactory.newReviewsSource(cachedRepo, model.getReviewsFactory()));
    }

    @NonNull
    private FactoryReviewsRepository newRepoFactory(ModelContext model,
                                                    DataValidator validator,
                                                    PersistencePlugin plugin) {
        FactoryReviewsCache cacheFactory = new FactoryReviewsCache(model, validator, plugin.newCacheFactory());
        return new FactoryReviewsRepository(cacheFactory);
    }
}