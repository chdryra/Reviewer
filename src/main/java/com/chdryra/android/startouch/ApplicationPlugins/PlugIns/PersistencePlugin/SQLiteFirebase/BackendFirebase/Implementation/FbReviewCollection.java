/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation;


import android.support.annotation.NonNull;

import com.chdryra.android.corelibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Interfaces.FbReviews;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Interfaces.FbReviewsStructure;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Interfaces.SnapshotConverter;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Structuring.DbUpdater;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.DataDefinitions.References.Implementation.SizeReferencer;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.startouch.Persistence.Implementation.RepoResult;
import com.chdryra.android.startouch.Persistence.Implementation.ReviewCollectionDeleter;
import com.chdryra.android.startouch.Persistence.Implementation.ReviewDereferencer;
import com.chdryra.android.startouch.Persistence.Interfaces.RepoCallback;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewCollection;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsRepoReadable;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 18/12/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class FbReviewCollection extends FbReviewsRepoBasic implements ReviewCollection {
    private final String mName;
    private final AuthorId mAuthorId;
    private final FbReviewsStructure mStructure;
    private final ReviewsRepoReadable mMasterRepo;
    private final ConverterCollectionItem mItemConverter;
    private final ArrayList<ReviewId> mStealthDeletion;

    public FbReviewCollection(Firebase dataBase,
                              FbReviewsStructure structure,
                              SnapshotConverter<ReviewReference> converter,
                              ReviewDereferencer dereferencer,
                              SizeReferencer sizeReferencer,
                              String name,
                              AuthorId authorId,
                              ReviewsRepoReadable masterRepo,
                              ConverterCollectionItem itemConverter) {
        super(dataBase, new PlaylistStructure(name, authorId, structure), converter,
                dereferencer, sizeReferencer);
        mName = name;
        mAuthorId = authorId;
        mStructure = structure;
        mMasterRepo = masterRepo;
        mItemConverter = itemConverter;
        mStealthDeletion = new ArrayList<>();
    }

    @Override
    public String getName() {
        return mName;
    }

    @Override
    public void addEntry(ReviewId reviewId, Callback callback) {
        update(reviewId, DbUpdater.UpdateType.INSERT_OR_UPDATE, callback);
    }

    @Override
    public void removeEntry(ReviewId reviewId, Callback callback) {
        update(reviewId, DbUpdater.UpdateType.DELETE, callback);
    }

    @Override
    public void hasEntry(ReviewId reviewId, final Callback callback) {
        mStructure.getCollectionEntryDb(getDataBase(), mAuthorId, mName, reviewId)
                .addListenerForSingleValueEvent(checkForEntry(callback));
    }

    @Override
    protected void newReference(DataSnapshot dataSnapshot, ReferenceReadyCallback callback) {
        if (dataSnapshot.getValue() == null) {
            callback.onReferenceReady(null);
            return;
        }

        ReviewId reviewId = mItemConverter.convert(dataSnapshot);
        if (reviewId == null) {
            callback.onReferenceReady(null);
            return;
        }

        if(mStealthDeletion.contains(reviewId)) {
            callback.onReferenceReady(null);
            return;
        }

        mMasterRepo.getReference(reviewId, getEntryOrDeleteIfGone(reviewId, callback));
    }

    @NonNull
    private ValueEventListener checkForEntry(final Callback callback) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                callback.onCollectionHasReview(dataSnapshot.getValue() != null,
                        CallbackMessage.ok());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                CallbackMessage error = CallbackMessage.error(FirebaseBackend
                        .backendError(firebaseError).getMessage());
                callback.onCollectionHasReview(false, error);
            }
        };
    }

    private void update(ReviewId reviewId, DbUpdater.UpdateType updateType, Callback
            callback) {
        Map<String, Object> updates = getUpdatesMap(reviewId, updateType);
        getDataBase().updateChildren(updates, updateDoneListener(callback, updateType));
    }

    private void notifyCallback(CallbackMessage message,
                                DbUpdater.UpdateType updateType,
                                Callback callback) {
        if (updateType.equals(DbUpdater.UpdateType.INSERT_OR_UPDATE)) {
            callback.onAddedToCollection(message);
        } else {
            callback.onRemovedFromCollection(message);
        }
    }

    @NonNull
    private Firebase.CompletionListener updateDoneListener(final Callback callback,
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

    @NonNull
    private Map<String, Object> getUpdatesMap(ReviewId reviewId, DbUpdater.UpdateType type) {
        return mStructure.getCollectionUpdater(mName, mAuthorId).getUpdatesMap(reviewId, type);
    }

    @NonNull
    private RepoCallback getEntryOrDeleteIfGone(final ReviewId id,
                                                final ReferenceReadyCallback callback) {
        return new RepoCallback() {
            @Override
            public void onRepoCallback(RepoResult result) {
                if (result.isReference()) {
                    callback.onReferenceReady(result.getReference());
                } else {
                    deleteFromPlaylistIfNecessary(result, id, callback);
                }
            }
        };
    }

    private void deleteFromPlaylistIfNecessary(RepoResult result,
                                               final ReviewId id,
                                               final ReferenceReadyCallback callback) {
        if (result.isError() && result.getMessage().equals(NULL_AT_SOURCE)) {
            mStealthDeletion.add(id);
            ReviewCollectionDeleter deleter
                    = new ReviewCollectionDeleter(id, FbReviewCollection.this, new ReviewCollectionDeleter
                    .DeleteCallback() {
                @Override
                public void onDeletedFromPlaylist(String playlistName,
                                                  ReviewId reviewId,
                                                  CallbackMessage message) {
                    mStealthDeletion.remove(reviewId);
                    callback.onReferenceReady(null);
                }
            });
            deleter.delete();
        } else {
            callback.onReferenceReady(null);
        }
    }

    private static class PlaylistStructure implements FbReviews {
        private final String mPlaylistName;
        private final AuthorId mPlaylistOwner;
        private final FbReviewsStructure mRootStructure;

        private PlaylistStructure(String playlistName, AuthorId playlistOwner, FbReviewsStructure
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
