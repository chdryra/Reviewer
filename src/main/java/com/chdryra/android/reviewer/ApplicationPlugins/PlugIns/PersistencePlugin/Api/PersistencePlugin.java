/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Api;

import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ModelContext;
import com.chdryra.android.reviewer.Authentication.Implementation.UsersManager;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.Persistence.Factories.FactoryReviewsRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsCache;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepositoryMutable;

/**
 * Created by: Rizwan Choudrey
 * On: 13/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface PersistencePlugin {
    FactoryPersistentCache newCacheFactory();

    ReviewsRepositoryMutable newLocalPersistence(ModelContext modelContext,
                                                 DataValidator validator,
                                                 FactoryReviewsRepository repoFactory);

    ReviewsRepositoryMutable newBackendPersistence(ModelContext modelContext,
                                                   DataValidator validator,
                                                   FactoryReviewsRepository repoFactory,
                                                   ReviewsCache cache);

    UsersManager newUsersManager();
}
