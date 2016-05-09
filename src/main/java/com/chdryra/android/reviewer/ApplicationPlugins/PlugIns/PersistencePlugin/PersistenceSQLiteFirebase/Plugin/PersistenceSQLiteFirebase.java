/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Plugin;



import android.content.Context;

import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ModelContext;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Api.Backend;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Api.FactoryPersistentCache;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Api.PersistencePlugin;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.AndroidSqLiteDb.Plugin.AndroidSqlLiteDb;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.BackendFirebase.Plugin.BackendFirebase;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.LocalReviewerDb.Implementation.FactoryReviewerDbCache;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.LocalReviewerDb.Plugin.FactoryLocalReviewerDb;
import com.chdryra.android.reviewer.Authentication.Implementation.UsersManager;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepositoryMutable;

/**
 * Created by: Rizwan Choudrey
 * On: 09/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class PersistenceSQLiteFirebase implements PersistencePlugin {
    private FactoryPersistentCache mCacheFactory;
    private FactoryLocalReviewerDb mLocalFactory;
    private Backend mBackend;

    public PersistenceSQLiteFirebase(Context context, String localName, int localVersion) {
        AndroidSqlLiteDb relationalDb = new AndroidSqlLiteDb();
        mLocalFactory = new FactoryLocalReviewerDb(context, relationalDb, localName, localVersion);
        mCacheFactory = new FactoryReviewerDbCache(mLocalFactory);
        mBackend = new BackendFirebase(context);
    }

    @Override
    public FactoryPersistentCache newCacheFactory() {
        return mCacheFactory;
    }

    @Override
    public ReviewsRepositoryMutable newLocalPersistence(ModelContext model, DataValidator validator) {
        return mLocalFactory.newPersistence(model, validator);
    }

    @Override
    public ReviewsRepositoryMutable newBackendPersistence(ModelContext model, DataValidator validator) {
        return mBackend.newPersistence(model, validator);
    }

    @Override
    public UsersManager newUsersManager() {
        return mBackend.newUsersManager();
    }
}
