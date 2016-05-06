/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase.Plugin;


import android.content.Context;

import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ModelContext;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Api.Backend;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase.Implementation.FactoryReviewDb;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase.Implementation.FirebaseAuthenticator;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase.Implementation.FirebaseBackend;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase.Implementation.FirebaseReviewMaker;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase.Implementation.FirebaseReviewsDbImpl;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase.Implementation.FirebaseReviewsRepo;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase.Implementation.FirebaseStructure;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase.Implementation.FirebaseValidator;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.ApiClasses.BackendReviewsDb;
import com.chdryra.android.reviewer.Authentication.Interfaces.Authenticator;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepositoryMutable;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 21/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class BackendFirebase implements Backend {
    @Override
    public ReviewsRepositoryMutable newPersistence(Context context, ModelContext model, DataValidator validator) {
        Firebase.setAndroidContext(context);

        FirebaseValidator fbValidator = new FirebaseValidator(validator);
        BackendReviewsDb db = new FirebaseReviewsDbImpl(new Firebase(FirebaseBackend.ROOT),
                new FirebaseStructure(), fbValidator);
        FactoryReviewDb reviewsFactory = new FactoryReviewDb(fbValidator);
        FirebaseReviewMaker maker = new FirebaseReviewMaker(model.getReviewsFactory());

        return new FirebaseReviewsRepo(db, reviewsFactory, model.getTagsManager(), maker);
    }

    @Override
    public Authenticator getAuthenticator() {
        return new FirebaseAuthenticator(new Firebase(FirebaseBackend.ROOT));
    }
}
