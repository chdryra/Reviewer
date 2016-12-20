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
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.BackendError;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Factories.FactoryFbReviewReference;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Interfaces.FbReviews;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Interfaces.SnapshotConverter;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Persistence.Implementation.RepositoryResult;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReferencesRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.RepositoryCallback;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsSubscriber;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 12/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class FbReferencesRepositoryBasic implements ReferencesRepository {
    protected static final CallbackMessage NULL_AT_SOURCE
            = CallbackMessage.error("Null at source");
    private static final CallbackMessage REFERENCING_ERROR
            = CallbackMessage.error("Referencing error");

    private final Firebase mDataBase;
    private final SnapshotConverter<ReviewListEntry> mEntryConverter;
    private final FbReviews mStructure;
    private final FactoryFbReviewReference mReferencer;
    private final Map<String, ChildEventListener> mSubscribers;

    protected abstract Firebase getAggregatesDb(ReviewListEntry entry);

    protected abstract Firebase getReviewDb(ReviewListEntry entry);

    FbReferencesRepositoryBasic(Firebase dataBase,
                                FbReviews structure,
                                SnapshotConverter<ReviewListEntry> entryConverter,
                                FactoryFbReviewReference referencer) {
        mDataBase = dataBase;
        mEntryConverter = entryConverter;
        mStructure = structure;
        mSubscribers = new HashMap<>();
        mReferencer = referencer;
    }

    protected Query getQuery(Firebase entriesDb) {
        return entriesDb.orderByChild(ReviewListEntry.DATE);
    }

    @Override
    public void subscribe(ReviewsSubscriber subscriber) {
        ChildEventListener listener = newChildEventListener(subscriber);
        mSubscribers.put(subscriber.getSubscriberId(), listener);
        getQuery().addChildEventListener(listener);
    }

    @Override
    public void unsubscribe(ReviewsSubscriber subscriber) {
        ChildEventListener listener = mSubscribers.remove(subscriber.getSubscriberId());
        if (listener != null) getQuery().removeEventListener(listener);
    }

    @Override
    public void getReference(ReviewId reviewId, RepositoryCallback callback) {
        Firebase entry = mStructure.getListEntryDb(mDataBase, reviewId);
        doSingleEvent(entry, newGetReferenceListener(reviewId, callback));
    }

    Firebase getDataBase() {
        return mDataBase;
    }

    private Query getQuery() {
        return getQuery(mStructure.getListEntriesDb(mDataBase));
    }

    @NonNull
    private ChildEventListener newChildEventListener(final ReviewsSubscriber subscriber) {
        return new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                newReference(dataSnapshot, new ReferenceReadyCallback() {
                    @Override
                    public void onReferenceReady(ReviewReference reference) {
                        if(reference != null) subscriber.onReviewAdded(reference);
                    }
                });
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                FbReferencesRepositoryBasic.this.onChildRemoved(dataSnapshot, subscriber);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        };
    }

    protected void onChildRemoved(DataSnapshot dataSnapshot, final ReviewsSubscriber subscriber) {
        newReference(dataSnapshot, new ReferenceReadyCallback() {
            @Override
            public void onReferenceReady(ReviewReference reference) {
                if(reference != null) subscriber.onReviewRemoved(reference);
            }
        });
    }

    @NonNull
    private ValueEventListener newGetReferenceListener(final ReviewId reviewId,
                                                       final RepositoryCallback callback) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() == null) {
                    callback.onRepositoryCallback(new RepositoryResult(reviewId, NULL_AT_SOURCE));
                    return;
                }

                newReference(dataSnapshot, new ReferenceReadyCallback() {
                    @Override
                    public void onReferenceReady(ReviewReference reference) {
                        RepositoryResult result;
                        if(reference != null) {
                            result = new RepositoryResult(reference);
                        } else {
                            result = new RepositoryResult(reviewId, REFERENCING_ERROR);
                        }
                        callback.onRepositoryCallback(result);
                    }
                });
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                BackendError error = FirebaseBackend.backendError(firebaseError);
                callback.onRepositoryCallback(new RepositoryResult(reviewId, CallbackMessage.error(error
                        .getMessage())));
            }
        };
    }

    private interface ReferenceReadyCallback {
        void onReferenceReady(@Nullable ReviewReference reference);
    }


    protected interface EntryReadyCallback {
        void onEntryReady(@Nullable ReviewListEntry entry);
    }

    @Nullable
    private void newReference(DataSnapshot dataSnapshot, final ReferenceReadyCallback callback) {
        getReviewListEntry(dataSnapshot, new EntryReadyCallback() {
            @Override
            public void onEntryReady(@Nullable ReviewListEntry entry) {
                ReviewReference reference = entry != null ?
                        mReferencer.newReview(entry.toInverseDate(), getReviewDb(entry), getAggregatesDb(entry)) : null;
                callback.onReferenceReady(reference);
            }
        });
    }

    protected void getReviewListEntry(DataSnapshot dataSnapshot, EntryReadyCallback callback) {
        callback.onEntryReady(convertToEntry(dataSnapshot));
    }

    @Nullable
    private ReviewListEntry convertToEntry(DataSnapshot dataSnapshot) {
        return mEntryConverter.convert(dataSnapshot);
    }

    private void doSingleEvent(Firebase root, ValueEventListener listener) {
        root.addListenerForSingleValueEvent(listener);
    }
}
