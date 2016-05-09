/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendDb.BackendFirebase.Plugin;


import android.content.Context;

import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ModelContext;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Api.Backend;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendDb.BackendFirebase.Implementation.FactoryReviewDb;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendDb.BackendFirebase.Implementation.FirebaseUserAuthenticator;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendDb.BackendFirebase.Implementation.FirebaseReviewMaker;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendDb.BackendFirebase.Implementation.FirebaseReviewsDbImpl;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendDb.BackendFirebase.Implementation.FirebaseReviewsRepo;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendDb.BackendFirebase.Implementation.FirebaseStructure;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendDb
        .BackendFirebase.Implementation.FirebaseUsersDbImpl;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendDb.BackendFirebase.Implementation.FirebaseValidator;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendDb
        .Implementation.UserAccountsBackend;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendDb
        .Implementation.UserProfileTranslator;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendDb.Interfaces.BackendReviewsDb;


import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendDb
        .Interfaces.BackendUsersDb;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAccounts;
import com.chdryra.android.reviewer.Authentication.Implementation.UsersManager;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepositoryMutable;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 21/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class BackendFirebase implements Backend {
    private static final UserProfileTranslator TRANSLATOR = new UserProfileTranslator();
    private final Firebase mDatabase;
    private final FirebaseStructure mStructure;

    public BackendFirebase(Firebase database, FirebaseStructure structure) {
        mDatabase = database;
        mStructure = structure;
    }

    @Override
    public ReviewsRepositoryMutable newPersistence(Context context, ModelContext model, DataValidator validator) {
        Firebase.setAndroidContext(context);

        FirebaseValidator fbValidator = new FirebaseValidator(validator);
        BackendReviewsDb db = new FirebaseReviewsDbImpl(mDatabase, mStructure, fbValidator);
        FactoryReviewDb reviewsFactory = new FactoryReviewDb(fbValidator);
        FirebaseReviewMaker maker = new FirebaseReviewMaker(model.getReviewsFactory());

        return new FirebaseReviewsRepo(db, reviewsFactory, model.getTagsManager(), maker);
    }

    @Override
    public UsersManager newUsersManager() {
        FirebaseUserAuthenticator authenticator = new FirebaseUserAuthenticator(mDatabase);
        BackendUsersDb usersDb = new FirebaseUsersDbImpl(mDatabase, mStructure, TRANSLATOR);
        UserAccounts accounts = new UserAccountsBackend(usersDb, TRANSLATOR);

        return new UsersManager(authenticator, accounts);
    }
}
