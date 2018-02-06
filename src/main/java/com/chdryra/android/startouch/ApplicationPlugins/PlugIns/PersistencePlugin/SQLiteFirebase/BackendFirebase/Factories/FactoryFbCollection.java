/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Factories;



import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Factories.BackendInfoConverter;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation.ConverterCollectionItem;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation.FbReviewCollection;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation.ReviewListEntry;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Interfaces.FbReviewsStructure;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Interfaces.SnapshotConverter;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.startouch.Persistence.Implementation.ReviewDereferencer;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewCollection;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsRepoReadable;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 18/12/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class FactoryFbCollection {
    private final FbReviewsStructure mStructure;
    private final SnapshotConverter<ReviewListEntry> mEntryConverter;
    private final BackendInfoConverter mInfoConverter;
    private final FbReviewReferencer mReferencer;
    private ReviewsRepoReadable mMasterRepo;

    public FactoryFbCollection(FbReviewsStructure structure, SnapshotConverter<ReviewListEntry>
            entryConverter, BackendInfoConverter infoConverter, FbReviewReferencer referencer) {
        mStructure = structure;
        mEntryConverter = entryConverter;
        mInfoConverter = infoConverter;
        mReferencer = referencer;
    }

    public void setMasterRepo(ReviewsRepoReadable masterRepo) {
        mMasterRepo = masterRepo;
    }

    public ReviewCollection newCollection(Firebase root, String name, AuthorId authorId, ReviewDereferencer dereferencer) {
        return new FbReviewCollection(root, mStructure, mEntryConverter, mReferencer, dereferencer, name, authorId,
                mMasterRepo, mInfoConverter, new ConverterCollectionItem());
    }
}
