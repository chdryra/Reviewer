/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Implementation;


import android.support.annotation.NonNull;

import com.chdryra.android.corelibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.corelibrary.ReferenceModel.Implementation.SizeReferencer;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Interfaces.FbReviews;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Interfaces.FbReviewsStructure;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Structuring.DbUpdater;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.Persistence.Implementation.RepoResult;
import com.chdryra.android.startouch.Persistence.Interfaces.RepoCallback;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewCollection;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsRepoReadable;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 18/12/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class FbReviewIdList extends FbReviewsListBasic<ReviewId> {
    static final CallbackMessage NO_ENTRY = CallbackMessage.error("No entry");
    static final CallbackMessage NO_REFERENCE = CallbackMessage.error("No reference");

    private final FbReviewsStructure mStructure;
    private final String mName;
    private final AuthorId mAuthorId;
    private final ReviewsRepoReadable mMasterRepo;
    private final ConverterCollectionItem mConverter;

    public FbReviewIdList(Firebase dataBase,
                          FbReviewsStructure structure,
                          SizeReferencer sizeReferencer,
                          String name,
                          AuthorId authorId,
                          ReviewsRepoReadable masterRepo,
                          ConverterCollectionItem converter) {
        super(dataBase, new CollectionStructure(name, authorId, structure), converter,
                sizeReferencer);
        mStructure = structure;
        mName = name;
        mAuthorId = authorId;
        mMasterRepo = masterRepo;
        mConverter = converter;
    }

    public String getName() {
        return mName;
    }

    @Override
    protected void getReference(ReviewId reviewId,
                                DataSnapshot dataSnapshot,
                                ReferenceReadyCallback callback) {
        ReviewId convert = mConverter.convert(dataSnapshot);
        if (convert != null) {
            mMasterRepo.getReference(reviewId, getReference(reviewId, callback));
        } else {
            callback.onReferenceReady(reviewId, null, NO_ENTRY);
        }
    }

    void hasEntry(ReviewId reviewId, final ReviewCollection.Callback callback) {
        mStructure.getCollectionEntryDb(getFbReference(), mAuthorId, getName(), reviewId)
                .addListenerForSingleValueEvent(checkForEntry(callback));
    }

    void update(ReviewId reviewId,
                DbUpdater.UpdateType updateType,
                ReviewCollection.Callback callback) {
        Map<String, Object> updates = getUpdatesMap(reviewId, updateType);
        getFbReference().updateChildren(updates, updateDoneListener(callback, updateType));
    }

    @NonNull
    private Firebase.CompletionListener updateDoneListener(final ReviewCollection.Callback callback,
                                                           final DbUpdater.UpdateType updateType) {
        return new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                CallbackMessage message = firebaseError == null ? CallbackMessage.ok() :
                        CallbackMessage.error(firebaseError.getMessage());
                notifyCallback(message, updateType, callback);
            }
        };
    }

    private void notifyCallback(CallbackMessage message,
                                DbUpdater.UpdateType updateType,
                                ReviewCollection.Callback callback) {
        if (updateType.equals(DbUpdater.UpdateType.INSERT_OR_UPDATE)) {
            callback.onAddedToCollection(mName, message);
        } else {
            callback.onRemovedFromCollection(mName, message);
        }
    }

    @NonNull
    private Map<String, Object> getUpdatesMap(ReviewId reviewId, DbUpdater.UpdateType type) {
        return mStructure.getCollectionUpdater(mName, mAuthorId).getUpdatesMap(reviewId, type);
    }

    @NonNull
    private ValueEventListener checkForEntry(final ReviewCollection.Callback callback) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                callback.onCollectionHasEntry(mName, dataSnapshot.getValue() != null,
                        CallbackMessage.ok());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                CallbackMessage error = CallbackMessage.error(FirebaseBackend
                        .backendError(firebaseError).getMessage());
                callback.onCollectionHasEntry(mName, false, error);
            }
        };
    }

    @NonNull
    private RepoCallback getReference(final ReviewId reviewId,
                                      final ReferenceReadyCallback callback) {
        return new RepoCallback() {
            @Override
            public void onRepoCallback(RepoResult result) {
                if (result.isReference()) {
                    callback.onReferenceReady(reviewId, result.getReference(), CallbackMessage.ok
                            ());
                } else {
                    callback.onReferenceReady(reviewId, null, NO_REFERENCE);
                }
            }
        };
    }

    private static class CollectionStructure implements FbReviews {
        private final String mPlaylistName;
        private final AuthorId mPlaylistOwner;
        private final FbReviewsStructure mRootStructure;

        private CollectionStructure(String playlistName, AuthorId playlistOwner, FbReviewsStructure
                rootStructure) {
            mPlaylistName = playlistName;
            mPlaylistOwner = playlistOwner;
            mRootStructure = rootStructure;
        }

        @Override
        public Firebase getListEntriesDb(Firebase root) {
            return mRootStructure.getCollectionDb(root, mPlaylistOwner, mPlaylistName);
        }

        @Override
        public Firebase getListEntryDb(Firebase root, ReviewId reviewId) {
            return mRootStructure.getListEntryDb(root, reviewId);
        }
    }
}
