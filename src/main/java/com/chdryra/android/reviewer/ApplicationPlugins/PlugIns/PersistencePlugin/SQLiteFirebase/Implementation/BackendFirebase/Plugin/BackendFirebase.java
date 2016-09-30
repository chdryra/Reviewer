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
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Factories.BackendInfoConverter;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Factories.BackendReviewConverter;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.BackendValidator;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.UserProfileConverter;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Factories.FactoryFbReference;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Factories.FactoryFbReviewReference;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Factories.FactoryUserAccount;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Implementation.ConverterEntry;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Factories.FactoryAuthorsRepo;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Implementation.ConverterNamedAuthorId;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Implementation.FbAuthenticator;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Implementation.FbAuthorsRepository;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Implementation.FbReviewsRepository;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Implementation.FbStructUsersLed;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Implementation.FbUserAccounts;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Implementation.FirebaseBackend;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Interfaces.FirebaseStructure;
import com.chdryra.android.reviewer.Authentication.Factories.FactoryAuthorProfile;
import com.chdryra.android.reviewer.Authentication.Interfaces.AccountsManager;
import com.chdryra.android.reviewer.Authentication.Implementation.AccountsManagerImpl;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAccounts;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAuthenticator;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.reviewer.Persistence.Factories.FactoryReviewsRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepository;
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
    private final UserProfileConverter mConverter;
    private final FactoryFbReference mDataReferencer;
    private final AuthorsRepository mAuthorsRepo;

    public BackendFirebase(Context context, FactoryAuthorProfile profileFactory) {
        Firebase.setAndroidContext(context);
        mDatabase = new Firebase(FirebaseBackend.ROOT);
        mStructure = new FbStructUsersLed();
        mConverter = new UserProfileConverter(profileFactory);
        mDataReferencer = new FactoryFbReference();
        mAuthorsRepo = new FbAuthorsRepository(mDatabase, mStructure, new ConverterNamedAuthorId(), mDataReferencer);
    }

    @Override
    public ReviewsRepository newReviewsRepo(ModelContext model,
                                            DataValidator validator,
                                            FactoryReviewsRepository repoFactory,
                                            ReviewsCache cache) {
        BackendValidator beValidator = new BackendValidator(validator);
        BackendReviewConverter reviewConverter
                = new BackendReviewConverter(beValidator,
                model.getReviewsFactory(), model.getTagsManager());

        FactoryFbReviewReference referencer
                = new FactoryFbReviewReference(mDataReferencer, new BackendInfoConverter(), reviewConverter, cache);

        ConverterEntry entryConverter = new ConverterEntry();
        FactoryAuthorsRepo authorsDbFactory = new FactoryAuthorsRepo(reviewConverter, beValidator, entryConverter, referencer);
        return new FbReviewsRepository(mDatabase, mStructure, entryConverter, referencer, authorsDbFactory);
    }

    @Override
    public AccountsManager newUsersManager() {
        UserAccounts accounts = new FbUserAccounts(mDatabase, mStructure, mDataReferencer, mConverter,
                mAuthorsRepo, new FactoryUserAccount());
        UserAuthenticator authenticator = new FbAuthenticator(mDatabase, accounts, mConverter);

        return new AccountsManagerImpl(authenticator, accounts);
    }

    @Override
    public AuthorsRepository getAuthorsRepo() {
        return new FbAuthorsRepository(mDatabase, mStructure, new ConverterNamedAuthorId(), mDataReferencer);
    }
}
