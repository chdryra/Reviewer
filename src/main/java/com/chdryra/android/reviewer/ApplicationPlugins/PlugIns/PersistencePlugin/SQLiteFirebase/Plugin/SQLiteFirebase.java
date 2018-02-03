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
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Api
        .FactoryPersistentCache;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Api
        .PersistencePlugin;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.AndroidSqLiteDb.Plugin.AndroidSqlLiteDb;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Plugin.BackendFirebase;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.LocalReviewerDb.Implementation.FactoryReviewerDbCache;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.LocalReviewerDb.Plugin.FactoryLocalReviewerDb;
import com.chdryra.android.reviewer.Authentication.Interfaces.AccountsManager;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.reviewer.Persistence.Factories.FactoryReviewsRepo;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepo;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepoMutable;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsCache;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsSource;

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
    public ReviewsRepoMutable newLocalPersistence(ModelContext model,
                                                  DataValidator validator) {
        return mLocalFactory.newPersistence(model, validator);
    }

    @Override
    public ReviewsSource newBackendPersistence(ModelContext model,
                                               DataValidator validator,
                                               FactoryReviewsRepo repoFactory,
                                               ReviewsCache cache) {
        return mBackend.newReviewsRepo(model, validator, repoFactory, cache);
    }

    @Override
    public AuthorsRepo getAuthorsPersistence() {
        return mBackend.getAuthorsRepo();
    }

    @Override
    public AccountsManager newAccountsManager() {
        return mBackend.newUsersManager();
    }
}
