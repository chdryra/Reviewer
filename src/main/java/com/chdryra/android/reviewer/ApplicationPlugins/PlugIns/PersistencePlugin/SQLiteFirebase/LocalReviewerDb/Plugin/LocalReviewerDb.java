/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Plugin;



import android.content.Context;

import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ModelContext;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Api.FactoryReviewsCache;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Api.LocalPlugin;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Api.RelationalDbPlugin;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Factories.FactoryLocalReviewerDb;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation.FactoryReviewerDbCache;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepoWriteable;

/**
 * Created by: Rizwan Choudrey
 * On: 03/02/2018
 * Email: rizwan.choudrey@gmail.com
 */

public class LocalReviewerDb implements LocalPlugin {
    private final FactoryReviewsCache mCacheFactory;
    private final FactoryLocalReviewerDb mLocalFactory;

    public LocalReviewerDb(Context context, String localName, int localVersion, RelationalDbPlugin localDb) {
        mLocalFactory = new FactoryLocalReviewerDb(context, localName, localVersion, localDb);
        mCacheFactory = new FactoryReviewerDbCache(mLocalFactory);
    }

    @Override
    public FactoryReviewsCache getCacheFactory() {
        return mCacheFactory;
    }

    @Override
    public ReviewsRepoWriteable newReviewsRepo(ModelContext model,
                                               DataValidator validator) {
        return mLocalFactory.newPersistence(model, validator);
    }
}
