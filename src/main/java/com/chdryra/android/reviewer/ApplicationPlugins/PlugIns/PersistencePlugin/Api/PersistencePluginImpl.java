/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Api;


import android.content.Context;

import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ModelContext;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepositoryMutable;


/**
 * Created by: Rizwan Choudrey
 * On: 13/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class PersistencePluginImpl implements PersistencePlugin {
    private FactoryPersistentCache mCacheFactory;
    private FactoryPersistence mLocalFactory;
    private FactoryPersistence mBackendFactory;

    public PersistencePluginImpl(FactoryPersistentCache cacheFactory,
                                 FactoryPersistence localDb,
                                 FactoryPersistence backendDb) {
        mCacheFactory = cacheFactory;
        mLocalFactory = localDb;
        mBackendFactory = backendDb;
    }

    @Override
    public FactoryPersistentCache newCacheFactory() {
        return mCacheFactory;
    }

    @Override
    public ReviewsRepositoryMutable newLocalPersistence(Context context, ModelContext model, DataValidator validator) {
        return mLocalFactory.newPersistence(context, model, validator);
    }

    @Override
    public ReviewsRepositoryMutable newBackendPersistence(Context context, ModelContext modelContext, DataValidator validator) {
        return mBackendFactory.newPersistence(context, modelContext, validator);
    }
}
