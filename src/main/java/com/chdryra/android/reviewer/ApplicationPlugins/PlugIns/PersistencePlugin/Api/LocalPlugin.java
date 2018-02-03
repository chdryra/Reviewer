/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Api;

import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ModelContext;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepoWriteable;

/**
 * Created by: Rizwan Choudrey
 * On: 03/02/2018
 * Email: rizwan.choudrey@gmail.com
 */

public interface LocalPlugin {
    FactoryReviewsCache getCacheFactory();

    ReviewsRepoWriteable newReviewsRepo(ModelContext modelContext, DataValidator validator);
}
