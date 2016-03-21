/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.BackendDatabase.Factories;

import android.content.Context;

import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ModelContext;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel.ReviewsRepositoryMutable;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.BackendDatabase.FirebaseReviewsRepo;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.Plugin.FactoryPersistence;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 21/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryBackendFirebase implements FactoryPersistence{
    private static final String ROOT = "https://teeqr.firebaseio.com/";
    private FactoryUserReview mReviewFactory;

    public FactoryBackendFirebase() {
        mReviewFactory = new FactoryUserReview();
    }

    @Override
    public ReviewsRepositoryMutable newPersistence(Context context, ModelContext model) {
        Firebase.setAndroidContext(context);
        return new FirebaseReviewsRepo(new Firebase(ROOT), mReviewFactory, model.getTagsManager());
    }
}
