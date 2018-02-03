/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Implementation;


import android.support.annotation.NonNull;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Factories.BackendInfoConverter;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Factories.FactoryFbReviewReference;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Interfaces.FbReviews;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Interfaces.FbReviewsStructure;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Interfaces.SnapshotConverter;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Structuring.DbUpdater;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.AuthorIdParcelable;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Persistence.Implementation.ReviewCollectionDeleter;
import com.chdryra.android.reviewer.Persistence.Implementation.RepositoryResult;
import com.chdryra.android.reviewer.Persistence.Implementation.ReviewDereferencer;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewCollection;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.RepositoryCallback;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsSubscriber;
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

public class FbReviewCollection extends FbReviewsRepositoryBasic implements ReviewCollection {
    private final String mName;
    private final AuthorId mAuthorId;
    private final FbReviewsStructure mStructure;
    private final ReviewsRepository mMasterRepo;
    private final BackendInfoConverter mInfoConverter;
    private final ConverterCollectionItem mItemConverter;
    private final ArrayList<ReviewId> mStealthDeletion;

    public FbReviewCollection(Firebase dataBase,
                              FbReviewsStructure structure,
                              SnapshotConverter<ReviewListEntry> entryConverter,
                              FactoryFbReviewReference referencer,
                              ReviewDereferencer dereferencer,
                              String name,
                              AuthorId authorId,
                              ReviewsRepository masterRepo,
                              BackendInfoConverter infoConverter,
                              ConverterCollectionItem itemConverter) {
        super(dataBase, new PlaylistStructure(name, authorId, structure), entryConverter,
                referencer, dereferencer);
        mName = name;
        mAuthorId = authorId;
        mStructure = structure;
        mMasterRepo = masterRepo;
        mInfoConverter = infoConverter;
        mItemConverter = itemConverter;
        mStealthDeletion = new ArrayList<>();
    }

    @Override
    public String getName() {
        return mName;
    }

    @Override
    protected Firebase getAggregatesDb(ReviewListEntry entry) {
        return mStructure.getAggregatesDb(getDataBase(), getAuthorId(entry), getReviewId(entry));
    }

    @Override
    protected Firebase getReviewDb(ReviewListEntry entry) {
        return mStructure.getReviewDb(getDataBase(), getAuthorId(entry), getReviewId(entry));
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
        mStructure.getPlaylistEntryDb(getDataBase(), mAuthorId, mName, reviewId)
                .addListenerForSingleValueEvent(checkForEntry(callback));
    }

    @Override
    protected void getReviewListEntry(DataSnapshot dataSnapshot, EntryReadyCallback callback) {
        if (dataSnapshot.getValue() == null) {
            callback.onEntryReady(null);
            return;
        }

        ReviewId reviewId = mItemConverter.convert(dataSnapshot);
        if (reviewId == null) {
            callback.onEntryReady(null);
            return;
        }

        if(mStealthDeletion.contains(reviewId)) {
            callback.onEntryReady(null);
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
    private AuthorId getAuthorId(ReviewListEntry entry) {
        return new AuthorIdParcelable(entry.getAuthorId());
    }

    @NonNull
    private ReviewId getReviewId(ReviewListEntry entry) {
        return new DatumReviewId(entry.getReviewId());
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
        return mStructure.getPlaylistUpdater(mName, mAuthorId).getUpdatesMap(reviewId, type);
    }

    @NonNull
    private RepositoryCallback getEntryOrDeleteIfGone(final ReviewId id,
                                                      final EntryReadyCallback callback) {
        return new RepositoryCallback() {
            @Override
            public void onRepoCallback(RepositoryResult result) {
                if (result.isReference()) {
                    ReviewListEntry entry = mInfoConverter.convert(result.getReference());
                    callback.onEntryReady(entry.toInverseDate());
                } else {
                    deleteFromPlaylistIfNecessary(result, id, callback);
                }
            }
        };
    }

    private void deleteFromPlaylistIfNecessary(RepositoryResult result,
                                               final ReviewId id,
                                               final EntryReadyCallback callback) {
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
                    callback.onEntryReady(null);
                }
            });
            deleter.delete();
        } else {
            callback.onEntryReady(null);
        }
    }

    @Override
    protected void onChildRemoved(DataSnapshot dataSnapshot, ReviewsSubscriber subscriber) {
        ReviewId id = mItemConverter.convert(dataSnapshot);
        if(id != null) subscriber.onReferenceInvalidated(id);
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
            return mRootStructure.getPlaylistDb(root, mPlaylistOwner, mPlaylistName);
        }

        @Override
        public Firebase getListEntryDb(Firebase root, ReviewId reviewId) {
            return mRootStructure.getListEntryDb(root, reviewId);
        }
    }
}
