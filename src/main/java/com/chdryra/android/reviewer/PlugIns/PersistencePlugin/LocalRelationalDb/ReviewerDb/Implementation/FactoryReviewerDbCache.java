/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb.ReviewerDb.Implementation;


import android.content.Context;

import com.chdryra.android.mygenerallibrary.QueueCache;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ModelContext;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.Api.FactoryPersistentCache;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb.ReviewerDb.Plugin
        .FactoryLocalReviewerDb;

/**
 * Created by: Rizwan Choudrey
 * On: 31/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewerDbCache implements FactoryPersistentCache {
    private FactoryLocalReviewerDb mDbFactory;

    public FactoryReviewerDbCache(FactoryLocalReviewerDb dbFactory) {
        mDbFactory = dbFactory;
    }

    @Override
    public QueueCache.Cache<Review> newReviewsCache(Context context, ModelContext model) {
        return new ReviewerDbCache(mDbFactory.newReviewerDb(context, model), model.getTagsManager());
    }
}
