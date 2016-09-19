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
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Api
        .PersistencePlugin;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.reviewer.Persistence.Factories.FactoryReviewsCache;
import com.chdryra.android.reviewer.Persistence.Factories.FactoryReviewsRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsCache;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepository;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReleasePersistenceContext extends PersistenceContextBasic {

    public ReleasePersistenceContext(ModelContext model,
                                     DataValidator validator,
                                     PersistencePlugin plugin) {

        setReposFactory(newRepoFactory(model, validator, plugin));

        setUsersManager(plugin.newUsersManager());

        setLocalRepository(plugin.newLocalPersistence(model, validator, getRepoFactory()));

        ReviewsCache cache = getRepoFactory().newCache();
        setBackendRepository(plugin.newBackendPersistence(model, validator, getRepoFactory(), cache));

        ReviewsRepository cachedRepo = getRepoFactory().newCachedRepo(getBackendRepository(),
                cache, model.getReviewsFactory(), model.getTagsManager());
        setReviewsSource(getRepoFactory().newReviewsSource(cachedRepo,
                getUsersManager().getAuthorsRepository(), model.getReviewsFactory()));
    }

    @NonNull
    private FactoryReviewsRepository newRepoFactory(ModelContext model,
                                                    DataValidator validator,
                                                    PersistencePlugin plugin) {
        FactoryReviewsCache cacheFactory = new FactoryReviewsCache(model, validator, plugin.newCacheFactory());
        return new FactoryReviewsRepository(cacheFactory);
    }
}