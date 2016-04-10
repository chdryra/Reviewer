/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDb.LocalReviewerDb.Implementation;


import android.content.Context;

import com.chdryra.android.mygenerallibrary.CacheUtils.QueueCache;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ModelContext;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.Api.FactoryPersistentCache;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDb.LocalReviewerDb.Interfaces.ReviewerDb;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDb.LocalReviewerDb.Plugin
        .FactoryLocalReviewerDb;

/**
 * Created by: Rizwan Choudrey
 * On: 31/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewerDbCache implements FactoryPersistentCache {
    private static final String CACHE_NAME = "ReviewerDbCache";
    private static final int CACHE_VER = 1;

    private int mIndex = 0;
    private FactoryLocalReviewerDb mDbFactory;

    public FactoryReviewerDbCache(FactoryLocalReviewerDb dbFactory) {
        mDbFactory = dbFactory;
    }

    @Override
    public QueueCache.Cache<Review> newReviewsCache(Context context, ModelContext model) {
        String name = CACHE_NAME + mIndex++;
        ReviewerDb db = mDbFactory.newReviewerDb(name, CACHE_VER, context, model);
        return new ReviewerDbCache(db, model.getTagsManager());
    }
}
