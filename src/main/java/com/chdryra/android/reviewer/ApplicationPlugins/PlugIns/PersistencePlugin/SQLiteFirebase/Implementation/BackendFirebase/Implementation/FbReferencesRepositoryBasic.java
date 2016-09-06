/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation;



import android.support.annotation.NonNull;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.BackendError;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Factories.FactoryFbReviewReference;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Interfaces.FbReviews;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Interfaces.SnapshotConverter;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Persistence.Implementation.RepositoryResult;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReferencesRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.RepositoryCallback;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsSubscriber;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 12/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class FbReferencesRepositoryBasic implements ReferencesRepository{

    private final Firebase mDataBase;
    private final SnapshotConverter<ReviewListEntry> mEntryConverter;
    private final FbReviews mStructure;
    private final FactoryFbReviewReference mReferencer;
    private final Map<String, ChildEventListener> mSubscribers;

    protected abstract Firebase getAggregatesDb(ReviewListEntry entry);

    protected abstract Firebase getReviewDb(ReviewListEntry entry);

    FbReferencesRepositoryBasic(Firebase dataBase,
                                SnapshotConverter<ReviewListEntry> entryConverter,
                                FbReviews structure, FactoryFbReviewReference referencer) {
        mDataBase = dataBase;
        mEntryConverter = entryConverter;
        mStructure = structure;
        mSubscribers = new HashMap<>();
        mReferencer = referencer;
    }

    Firebase getDataBase() {
        return mDataBase;
    }

    public TagsManager getTagsManager() {
        return mReferencer.getTagsManager();
    }

    @Override
    public void subscribe(ReviewsSubscriber subscriber) {
        ChildEventListener listener = newChildEventListener(subscriber);
        mSubscribers.put(subscriber.getSubscriberId(), listener);
        mStructure.getListEntriesDb(mDataBase).addChildEventListener(listener);
    }

    @Override
    public void unsubscribe(ReviewsSubscriber subscriber) {
        ChildEventListener listener = mSubscribers.remove(subscriber.getSubscriberId());
        if (listener != null) mStructure.getListEntriesDb(mDataBase).removeEventListener(listener);
    }

    @Override
    public void getReference(ReviewId reviewId, RepositoryCallback callback) {
        Firebase entry = mStructure.getListEntryDb(mDataBase, reviewId);
        doSingleEvent(entry, newGetReferenceListener(callback));
    }

    @NonNull
    private ChildEventListener newChildEventListener(final ReviewsSubscriber subscriber) {
        return new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                subscriber.onReviewAdded(newReference(dataSnapshot));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                subscriber.onReviewRemoved(newReference(dataSnapshot));
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        };
    }

    @NonNull
    private ValueEventListener newGetReferenceListener(final RepositoryCallback callback) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                callback.onRepositoryCallback(new RepositoryResult(newReference(dataSnapshot)));
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                BackendError error = FirebaseBackend.backendError(firebaseError);
                callback.onRepositoryCallback(new RepositoryResult(CallbackMessage.error(error
                        .getMessage())));
            }
        };
    }

    @NonNull
    private ReviewReference newReference(DataSnapshot dataSnapshot) {
        ReviewListEntry entry = mEntryConverter.convert(dataSnapshot);
        ReviewReference ref;
        if(entry != null) {
            ref = mReferencer.newReview(entry, getReviewDb(entry), getAggregatesDb(entry));
        } else {
            ref = mReferencer.newNullReview();
        }

        return ref;
    }

    private void doSingleEvent(Firebase root, ValueEventListener listener) {
        root.addListenerForSingleValueEvent(listener);
    }
}
