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
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Factories.BackendDataConverter;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Factories.BackendReviewConverter;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.BackendValidator;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.UserProfileConverter;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Factories.FactoryUserAccount;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.FactoryAuthorsDb;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.FbReferencer;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.FbStructUsersLed;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.FirebaseAuthenticator;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.FirebaseBackend;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.FirebaseReviewsRepository;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.FirebaseUserAccounts;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Interfaces.FirebaseStructure;
import com.chdryra.android.reviewer.Authentication.Factories.FactoryAuthorProfile;
import com.chdryra.android.reviewer.Authentication.Implementation.UsersManager;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAccounts;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAuthenticator;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.Persistence.Factories.FactoryReviewsRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsCache;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepository;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 21/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class BackendFirebase implements Backend {
    private final Firebase mDatabase;
    private final FirebaseStructure mStructure;
    private UserProfileConverter mTranslator;

    public BackendFirebase(Context context, FactoryAuthorProfile profileFactory) {
        Firebase.setAndroidContext(context);
        mDatabase = new Firebase(FirebaseBackend.ROOT);
        mStructure = new FbStructUsersLed();
        mTranslator = new UserProfileConverter(profileFactory);
    }

    @Override
    public ReviewsRepository newPersistence(ModelContext model,
                                            DataValidator validator,
                                            FactoryReviewsRepository repoFactory,
                                            ReviewsCache cache) {
        BackendValidator beValidator = new BackendValidator(validator);
        BackendReviewConverter reviewConverter
                = new BackendReviewConverter(beValidator,
                model.getReviewsFactory(), model.getTagsManager());

        BackendDataConverter dataConverter = new BackendDataConverter();
        FbReferencer referencer = new FbReferencer(dataConverter, reviewConverter,
                cache, model.getReviewsFactory());

        FactoryAuthorsDb authorsDbFactory = new FactoryAuthorsDb(reviewConverter, beValidator, referencer);
        return new FirebaseReviewsRepository(mDatabase, mStructure, referencer, authorsDbFactory);
    }

    @Override
    public UsersManager newUsersManager() {
        UserAccounts accounts = new FirebaseUserAccounts(mDatabase, mStructure, mTranslator, new FactoryUserAccount());
        UserAuthenticator authenticator = new FirebaseAuthenticator(mDatabase, accounts, mTranslator);

        return new UsersManager(authenticator, accounts);
    }
}
