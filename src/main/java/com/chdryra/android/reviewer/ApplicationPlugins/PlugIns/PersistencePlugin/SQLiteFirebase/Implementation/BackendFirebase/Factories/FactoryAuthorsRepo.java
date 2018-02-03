/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Factories;



import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Factories.BackendReviewConverter;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.BackendValidator;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Implementation.FbAuthorReviewsMutable;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Implementation.FbAuthorReviewsReadable;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Implementation.ReviewListEntry;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Interfaces.FbAuthorsReviews;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Interfaces.SnapshotConverter;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.Persistence.Implementation.ReviewDereferencer;
import com.chdryra.android.reviewer.Persistence.Interfaces.MutableRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewCollection;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsCache;
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
    private final ReviewDereferencer mDereferencer;
    private final ReviewsCache mCache;
    private final FactoryFbCollection mCollectionFactory;

    public FactoryAuthorsRepo(BackendReviewConverter reviewConverter,
                              BackendValidator validator,
                              SnapshotConverter<ReviewListEntry> entryConverter,
                              FactoryFbReviewReference referencer,
                              ReviewDereferencer dereferencer,
                              ReviewsCache cache,
                              FactoryFbCollection collectionFactory) {
        mReviewConverter = reviewConverter;
        mValidator = validator;
        mEntryConverter = entryConverter;
        mReferencer = referencer;
        mDereferencer = dereferencer;
        mCache = cache;
        mCollectionFactory = collectionFactory;
    }

    public ReviewsRepository newAuthorsDbReadable(Firebase root, FbAuthorsReviews authorsDb) {
        return new FbAuthorReviewsReadable(root, authorsDb, mEntryConverter, mReferencer, mDereferencer);
    }

    public MutableRepository newAuthorsDbMutable(Firebase root, FbAuthorsReviews authorsDb) {
        return new FbAuthorReviewsMutable(root, authorsDb, mEntryConverter, mReviewConverter,
                mValidator, mReferencer, mDereferencer, mCache);
    }

    public ReviewCollection getBookmarks(Firebase root, AuthorId authorId) {
        return mCollectionFactory.newPlaylist(root, Strings.Playlists.BOOKMARKS,
                authorId, mDereferencer);
    }
}
