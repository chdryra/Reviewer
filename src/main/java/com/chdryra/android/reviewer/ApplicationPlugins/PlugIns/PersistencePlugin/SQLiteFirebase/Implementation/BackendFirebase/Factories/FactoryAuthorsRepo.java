/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Factories;



import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Factories.BackendReviewConverter;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.BackendValidator;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.FbAuthorsRefsRepo;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.FbMutableRefsRepo;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.ReviewListEntry;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Interfaces.FbAuthorsReviews;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Interfaces.SnapshotConverter;
import com.chdryra.android.reviewer.Persistence.Interfaces.MutableRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReferencesRepository;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 09/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryAuthorsRepo {
    private final BackendReviewConverter mReviewConverter;
    private final BackendValidator mValidator;
    private final SnapshotConverter<ReviewListEntry> mEntryConverter;
    private final FactoryFbReviewReference mReferencer;

    public FactoryAuthorsRepo(BackendReviewConverter reviewConverter,
                              BackendValidator validator,
                              SnapshotConverter<ReviewListEntry> entryConverter,
                              FactoryFbReviewReference referencer) {
        mReviewConverter = reviewConverter;
        mValidator = validator;
        mEntryConverter = entryConverter;
        mReferencer = referencer;
    }

    public ReferencesRepository newAuthorReviews(Firebase root, FbAuthorsReviews structure) {
        return new FbAuthorsRefsRepo(root, structure, mEntryConverter, mReferencer);
    }

    public MutableRepository newAuthorsDb(Firebase root, FbAuthorsReviews structure) {
        return new FbMutableRefsRepo(root, structure, mEntryConverter, mReviewConverter,
                mValidator, mReferencer);
    }
}
