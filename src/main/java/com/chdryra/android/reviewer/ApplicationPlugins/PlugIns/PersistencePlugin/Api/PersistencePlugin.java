/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Api;

import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ModelContext;
import com.chdryra.android.reviewer.Authentication.Interfaces.AccountsManager;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.reviewer.Persistence.Factories.FactoryReviewsRepo;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepo;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepoMutable;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsCache;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsSource;

/**
 * Created by: Rizwan Choudrey
 * On: 13/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface PersistencePlugin {
    FactoryPersistentCache newCacheFactory();

    ReviewsRepoMutable newLocalPersistence(ModelContext modelContext,
                                           DataValidator validator);

    ReviewsSource newBackendPersistence(ModelContext modelContext,
                                        DataValidator validator,
                                        FactoryReviewsRepo repoFactory,
                                        ReviewsCache cache);

    AuthorsRepo getAuthorsPersistence();

    AccountsManager newAccountsManager();
}
