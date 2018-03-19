/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Persistence;

import com.chdryra.android.startouch.ApplicationContexts.Implementation.PersistenceContextBasic;
import com.chdryra.android.startouch.ApplicationContexts.Interfaces.ModelContext;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Api.BackendPlugin;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Api.LocalPlugin;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Api
        .PersistencePlugin;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.startouch.Persistence.Factories.FactoryReviewsCache;
import com.chdryra.android.startouch.Persistence.Factories.FactoryReviewsRepo;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsCache;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsRepo;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReleasePersistenceContext extends PersistenceContextBasic {

    public ReleasePersistenceContext(ModelContext model,
                                     DataValidator validator,
                                     PersistencePlugin plugin) {

        LocalPlugin local = plugin.getLocal();
        FactoryReviewsCache cacheFactory = new FactoryReviewsCache(model, validator, local
                .getCacheFactory());
        setReposFactory(new FactoryReviewsRepo(cacheFactory));

        setLocalRepo(local.newReviewsRepo(model, validator));

        ReviewsCache cache = getRepoFactory().newCache();

        BackendPlugin backend = plugin.getBackend();
        setAccountsManager(backend.getAccounts());
        setAuthorsRepo(backend.getAuthors());
        ReviewsRepo reviews =
                backend.getReviews(model, validator, getRepoFactory(), cache);
        ReviewsRepo cachedReviews =
                getRepoFactory().newCachedRepo(reviews, cache, model.getReviewsFactory());
        setReviewsRepo(getRepoFactory().newReviewsNodeRepo(cachedReviews, getAuthorsRepo(), model
                .getReviewsFactory()));
    }
}