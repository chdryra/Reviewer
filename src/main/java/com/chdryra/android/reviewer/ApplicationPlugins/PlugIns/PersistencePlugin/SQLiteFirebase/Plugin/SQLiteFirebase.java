/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Plugin;



import android.content.Context;

import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ModelContext;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Api.Backend;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Api.FactoryPersistentCache;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Api.PersistencePlugin;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.AndroidSqLiteDb.Plugin.AndroidSqlLiteDb;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Plugin.BackendFirebase;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Implementation.FactoryReviewerDbCache;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Plugin.FactoryLocalReviewerDb;
import com.chdryra.android.reviewer.Authentication.Interfaces.AccountsManager;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.reviewer.Persistence.Factories.FactoryReviewsRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.LocalRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsCache;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepository;

/**
 * Created by: Rizwan Choudrey
 * On: 09/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class SQLiteFirebase implements PersistencePlugin {
    private final FactoryPersistentCache mCacheFactory;
    private final FactoryLocalReviewerDb mLocalFactory;
    private final Backend mBackend;

    public SQLiteFirebase(Context context, String localName, int localVersion) {
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
    public LocalRepository newLocalPersistence(ModelContext model,
                                               DataValidator validator,
                                               FactoryReviewsRepository repoFactory) {
        return mLocalFactory.newPersistence(model, validator, repoFactory);
    }

    @Override
    public ReviewsRepository newBackendPersistence(ModelContext model,
                                                   DataValidator validator,
                                                   FactoryReviewsRepository repoFactory,
                                                   ReviewsCache cache) {
        return mBackend.newReviewsRepo(model, validator, repoFactory, cache);
    }

    @Override
    public AuthorsRepository getAuthorsPersistence() {
        return mBackend.getAuthorsRepo();
    }

    @Override
    public AccountsManager newAccountsManager() {
        return mBackend.newUsersManager();
    }
}
