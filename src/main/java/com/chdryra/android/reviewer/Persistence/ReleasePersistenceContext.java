/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Persistence;

import com.chdryra.android.reviewer.ApplicationContexts.Implementation.PersistenceContextBasic;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ModelContext;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Api.Backend;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Api
        .PersistencePlugin;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.reviewer.Persistence.Factories.FactoryReviewsCache;
import com.chdryra.android.reviewer.Persistence.Factories.FactoryReviewsRepo;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsCache;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepo;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReleasePersistenceContext extends PersistenceContextBasic {

    public ReleasePersistenceContext(ModelContext model,
                                     DataValidator validator,
                                     PersistencePlugin plugin) {

        FactoryReviewsCache cacheFactory = new FactoryReviewsCache(model, validator, plugin.getCacheFactory());
        setReposFactory(new FactoryReviewsRepo(cacheFactory));

        setLocalRepo(plugin.newLocalReviewsRepo(model, validator));

        ReviewsCache cache = getRepoFactory().newCache();

        Backend backend = plugin.getBackend();
        setAccountsManager(backend.getAccounts());
        setAuthorsRepo(backend.getAuthors());
        ReviewsRepo reviews =
                backend.getReviews(model, validator, getRepoFactory(), cache);
        ReviewsRepo cachedReviews =
                getRepoFactory().newCachedRepo(reviews, cache, model.getReviewsFactory());
        setReviewsRepo(getRepoFactory().newReviewsSource(cachedReviews, getAuthorsRepo(), model.getReviewsFactory()));
    }
}