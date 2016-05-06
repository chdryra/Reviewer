/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.ApiClasses;


import android.content.Context;

import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ModelContext;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Api.Backend;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Api
        .FactoryPersistence;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Api.FactoryPersistentCache;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Api.PersistencePlugin;
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
    private Backend mBackend;

    public PersistencePluginImpl(FactoryPersistentCache cacheFactory,
                                 FactoryPersistence localDb,
                                 Backend backend) {
        mCacheFactory = cacheFactory;
        mLocalFactory = localDb;
        mBackend = backend;
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
    public Backend getBackend() {
        return mBackend;
    }
}
