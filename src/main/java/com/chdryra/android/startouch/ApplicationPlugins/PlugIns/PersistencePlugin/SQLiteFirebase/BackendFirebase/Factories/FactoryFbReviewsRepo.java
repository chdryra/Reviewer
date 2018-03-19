/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Factories;


import com.chdryra.android.corelibrary.ReferenceModel.Implementation.SizeReferencer;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Factories.BackendReviewConverter;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.BackendValidator;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Implementation.FbAuthorsDbReadable;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Implementation.FbAuthorsDbWriteable;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Interfaces.FbAuthorsDb;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Interfaces.SnapshotConverter;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.startouch.Persistence.Implementation.ReviewDereferencer;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewCollection;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsCache;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsRepoReadable;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsRepoWriteable;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 09/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryFbReviewsRepo {
    private final BackendReviewConverter mReviewConverter;
    private final BackendValidator mValidator;
    private final SnapshotConverter<ReviewReference> mConverter;
    private final ReviewDereferencer mDereferencer;
    private final SizeReferencer mSizeReferencer;
    private final ReviewsCache mCache;
    private final FactoryFbCollection mCollectionFactory;

    public FactoryFbReviewsRepo(BackendReviewConverter reviewConverter,
                                BackendValidator validator,
                                SnapshotConverter<ReviewReference> converter,
                                ReviewDereferencer dereferencer,
                                SizeReferencer sizeReferencer, ReviewsCache cache,
                                FactoryFbCollection collectionFactory) {
        mReviewConverter = reviewConverter;
        mValidator = validator;
        mConverter = converter;
        mDereferencer = dereferencer;
        mSizeReferencer = sizeReferencer;
        mCache = cache;
        mCollectionFactory = collectionFactory;
    }

    public ReviewsRepoReadable newRepoReadable(Firebase root, FbAuthorsDb authorsDb) {
        return new FbAuthorsDbReadable(root, authorsDb, mConverter, mDereferencer, mSizeReferencer);
    }

    public ReviewsRepoWriteable newRepoWriteable(Firebase root, FbAuthorsDb authorsDb) {
        return new FbAuthorsDbWriteable(root, authorsDb, mConverter, mReviewConverter,
                mValidator, mDereferencer, mSizeReferencer, mCache);
    }

    public ReviewCollection getReviewCollection(Firebase root, AuthorId authorId, String name) {
        return mCollectionFactory.newCollection(root, name, authorId, mDereferencer,
                mSizeReferencer);
    }
}
