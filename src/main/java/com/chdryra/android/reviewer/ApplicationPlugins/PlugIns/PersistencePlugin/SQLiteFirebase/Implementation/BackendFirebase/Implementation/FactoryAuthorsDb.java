/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation;



import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Factories.BackendReviewConverter;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.BackendValidator;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Factories.FactoryFbReference;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Interfaces.FbAuthorsReviews;
import com.chdryra.android.reviewer.Persistence.Interfaces.MutableRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReferencesRepository;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 09/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryAuthorsDb {
    private BackendReviewConverter mReviewConverter;
    private BackendValidator mValidator;
    private SnapshotConverter<ReviewListEntry> mEntryConverter;
    private FactoryFbReference mReferencer;

    public FactoryAuthorsDb(BackendReviewConverter reviewConverter,
                            BackendValidator validator,
                            SnapshotConverter<ReviewListEntry> entryConverter,
                            FactoryFbReference referencer) {
        mReviewConverter = reviewConverter;
        mValidator = validator;
        mEntryConverter = entryConverter;
        mReferencer = referencer;
    }

    public ReferencesRepository newAuthorReviews(Firebase root, FbAuthorsReviews structure) {
        return new FirebaseAuthorsRepository(root, structure, mEntryConverter, mReferencer);
    }

    public MutableRepository newAuthorsDb(Firebase root, FbAuthorsReviews structure) {
        return new FirebaseMutableRepository(root, structure, mEntryConverter, mReviewConverter,
                mValidator, mReferencer);
    }
}
