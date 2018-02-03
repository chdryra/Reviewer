/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation;


import com.chdryra.android.mygenerallibrary.CacheUtils.QueueCache;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Api.FactoryReviewsCache;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces.ReviewerDb;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Factories.FactoryLocalReviewerDb;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewMaker;

/**
 * Created by: Rizwan Choudrey
 * On: 31/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewerDbCache implements FactoryReviewsCache {
    private static final String CACHE_NAME = "ReviewerDbCache";
    private static final int CACHE_VER = 1;

    private int mIndex = 0;
    private final FactoryLocalReviewerDb mDbFactory;

    public FactoryReviewerDbCache(FactoryLocalReviewerDb dbFactory) {
        mDbFactory = dbFactory;
    }

    @Override
    public QueueCache.Cache<Review> newReviewsCache(ReviewMaker recreater, DataValidator validator) {
        String name = CACHE_NAME + mIndex++;
        ReviewerDb db = mDbFactory.newReviewerDb(name, CACHE_VER, recreater, validator);
        return new ReviewerDbCache(db);
    }
}
