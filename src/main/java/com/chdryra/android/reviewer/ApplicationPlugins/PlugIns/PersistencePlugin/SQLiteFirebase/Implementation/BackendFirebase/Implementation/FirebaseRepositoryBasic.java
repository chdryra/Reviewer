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
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsSubscriber;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Interfaces.FbReviewsStructure;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Persistence.Implementation.RepositoryResult;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReferencesRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.RepositoryCallback;
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
public abstract class FirebaseRepositoryBasic implements ReferencesRepository{
    protected Firebase mDataBase;
    protected FbReviewsStructure mStructure;
    protected FbReferencer mReferencer;
    protected Map<String, ChildEventListener> mSubscribers;

    protected abstract Firebase getAggregatesDb(ReviewListEntry entry);

    protected abstract Firebase getReviewDb(ReviewListEntry entry);

    public FirebaseRepositoryBasic(Firebase dataBase, FbReviewsStructure structure, FbReferencer referencer) {
        mDataBase = dataBase;
        mStructure = structure;
        mSubscribers = new HashMap<>();
        mReferencer = referencer;
    }

    protected Firebase getDataBase() {
        return mDataBase;
    }

    public TagsManager getTagsManager() {
        return mReferencer.getTagsManager();
    }

    @Override
    public void bind(ReviewsSubscriber subscriber) {
        ChildEventListener listener = newChildEventListener(subscriber);
        mSubscribers.put(subscriber.getSubscriberId(), listener);
        mStructure.getListEntriesDb(mDataBase).addChildEventListener(listener);
    }

    @Override
    public void unbind(ReviewsSubscriber subscriber) {
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
        ReviewListEntry entry = dataSnapshot.getValue(ReviewListEntry.class);
        Firebase reviewDb = getReviewDb(entry);
        Firebase aggregatesDb = getAggregatesDb(entry);
        return mReferencer.newReference(entry, reviewDb, aggregatesDb);
    }

    private void doSingleEvent(Firebase root, ValueEventListener listener) {
        root.addListenerForSingleValueEvent(listener);
    }
}
