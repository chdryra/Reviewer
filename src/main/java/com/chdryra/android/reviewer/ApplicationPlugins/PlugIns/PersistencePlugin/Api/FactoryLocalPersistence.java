/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Api;


import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ModelContext;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepoMutable;

/**
 * Created by: Rizwan Choudrey
 * On: 21/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface FactoryLocalPersistence {
    ReviewsRepoMutable newPersistence(ModelContext model, DataValidator validator);
}
