/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Plugin;



import android.content.Context;

import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ModelContext;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Api.Backend;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Factories.FactoryReviewDb;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.BackendReviewMaker;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.BackendReviewsRepo;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.BackendValidator;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.BackendUserAccounts;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.UserProfileTranslator;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Interfaces.BackendReviewsDb;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.FirebaseAuthenticator;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.FirebaseBackend;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.FirebaseReviewsDb;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.FirebaseStructure;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.FirebaseUsersDb;
import com.chdryra.android.reviewer.Authentication.Factories.FactoryAuthorProfile;
import com.chdryra.android.reviewer.Authentication.Implementation.UsersManager;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAccounts;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAuthenticator;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepositoryMutable;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 21/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class BackendFirebase implements Backend {
    private final Firebase mDatabase;
    private final FirebaseStructure mStructure;
    private UserProfileTranslator mTranslator;

    public BackendFirebase(Context context, FactoryAuthorProfile profileFactory) {
        Firebase.setAndroidContext(context);
        mDatabase = new Firebase(FirebaseBackend.ROOT);
        mStructure = new FirebaseStructure();
        mTranslator = new UserProfileTranslator(profileFactory);
    }

    @Override
    public ReviewsRepositoryMutable newPersistence(ModelContext model, DataValidator validator) {
        BackendValidator fbValidator = new BackendValidator(validator);
        BackendReviewsDb db = new FirebaseReviewsDb(mDatabase, mStructure, fbValidator);
        FactoryReviewDb reviewsFactory = new FactoryReviewDb(fbValidator);
        BackendReviewMaker maker = new BackendReviewMaker(model.getReviewsFactory());

        return new BackendReviewsRepo(db, reviewsFactory, model.getTagsManager(), maker);
    }

    @Override
    public UsersManager newUsersManager() {
        FirebaseUsersDb usersDb = new FirebaseUsersDb(mDatabase, mStructure, mTranslator);
        UserAccounts accounts = new BackendUserAccounts(usersDb, mTranslator);
        UserAuthenticator authenticator = new FirebaseAuthenticator(mDatabase, usersDb, mTranslator);

        return new UsersManager(authenticator, accounts);
    }
}
