/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase
        .Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase
        .Interfaces.FirebaseReviewsDb;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase
        .Interfaces.FirebaseDbObserver;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase
        .FirebaseStructuring.DbUpdater;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 23/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FirebaseReviewsDbImpl implements FirebaseReviewsDb {
    private Firebase mDataBase;
    private FirebaseStructure mStructure;
    private FirebaseValidator mValidator;
    private ArrayList<FirebaseDbObserver<FbReview>> mObservers;

    public FirebaseReviewsDbImpl(Firebase dataBase,
                                 FirebaseStructure structure,
                                 FirebaseValidator validator) {
        mDataBase = dataBase;
        mStructure = structure;
        mValidator = validator;
        mObservers = new ArrayList<>();
        getReviewsRoot().addChildEventListener(new ChildListener());
    }

    @Override
    public void addReview(FbReview review, AddReviewCallback callback) {
        Map<String, Object> map = getUpdatesMap(review, DbUpdater.UpdateType.INSERT_OR_UPDATE);
        mDataBase.updateChildren(map, newAddListener(review, callback));
    }

    @Override
    public void deleteReview(String reviewId, final DeleteReviewCallback callback) {
        doSingleEvent(getReviewRoot(reviewId), newGetAndDeleteListener(reviewId, callback));
    }

    @Override
    public void getReview(String reviewId, GetReviewCallback callback) {
        doSingleEvent(getReviewRoot(reviewId), newGetListener(callback));
    }

    @Override
    public void getReviews(GetCollectionCallback callback) {
        doSingleEvent(getReviewsRoot(), newGetCollectionListener(callback));
    }

    @Override
    public void getReviewsList(GetCollectionCallback callback) {
        doSingleEvent(getReviewsListRoot(), newGetCollectionListener(callback));
    }

    @Override
    public void registerObserver(FirebaseDbObserver<FbReview> observer) {
        if (!mObservers.contains(observer)) mObservers.add(observer);
    }

    @Override
    public void unregisterObserver(FirebaseDbObserver<FbReview> observer) {
        if (mObservers.contains(observer)) mObservers.remove(observer);
    }

    private Firebase getReviewRoot(String reviewId) {
        return mDataBase.child(mStructure.pathToReview(reviewId));
    }

    private Firebase getReviewsRoot() {
        return mDataBase.child(mStructure.pathToReviewsData());
    }

    private Firebase getReviewsListRoot() {
        return mDataBase.child(mStructure.pathToReviewsList());
    }

    @NonNull
    private Map<String, Object> getUpdatesMap(FbReview review, DbUpdater.UpdateType type) {
        return mStructure.getReviewUploadUpdater().getUpdatesMap(review, type);
    }

    private void doSingleEvent(Firebase root, ValueEventListener listener) {
        root.addListenerForSingleValueEvent(listener);
    }

    @NonNull
    private ValueEventListener newGetAndDeleteListener(final String reviewId, final
    DeleteReviewCallback callback) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final FbReview review = dataSnapshot.getValue(FbReview.class);
                if (mValidator.isValid(review)) doDelete(review, callback);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                callback.onReviewDeleted(reviewId, firebaseError);
            }
        };
    }

    private void doDelete(FbReview review, DeleteReviewCallback callback) {
        Map<String, Object> deleteMap = getUpdatesMap(review, DbUpdater.UpdateType.DELETE);
        mDataBase.updateChildren(deleteMap, newDeleteListener(review.getReviewId(), callback));
    }

    @NonNull
    private ValueEventListener newGetListener(final GetReviewCallback listener) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FbReview review = dataSnapshot.getValue(FbReview.class);
                listener.onReview(review, null);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                listener.onReview(new FbReview(), firebaseError);
            }
        };
    }

    @NonNull
    private ValueEventListener newGetCollectionListener(final GetCollectionCallback listener) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<FbReview> reviews = new ArrayList<>();
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    FbReview review = childSnapshot.getValue(FbReview.class);
                    review.setReviewId(childSnapshot.getKey());
                    if (mValidator.isIdValid(review)) reviews.add(review);
                }

                listener.onReviewCollection(reviews, null);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                listener.onReviewCollection(new ArrayList<FbReview>(), firebaseError);
            }
        };
    }

    @NonNull
    private Firebase.CompletionListener newAddListener(final FbReview review,
                                                       final AddReviewCallback listener) {
        return new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                listener.onReviewAdded(review, firebaseError);
            }
        };
    }

    @NonNull
    private Firebase.CompletionListener newDeleteListener(final String reviewId,
                                                          final DeleteReviewCallback listener) {
        return new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                listener.onReviewDeleted(reviewId, firebaseError);
            }
        };
    }

    private class ChildListener implements ChildEventListener {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            notifyOnChildAdded(getReview(dataSnapshot));
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            notifyOnChildChanged(getReview(dataSnapshot));
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            notifyOnChildRemoved(getReview(dataSnapshot));
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {

        }

        private FbReview getReview(DataSnapshot dataSnapshot) {
            return dataSnapshot.getValue(FbReview.class);
        }

        private void notifyOnChildAdded(FbReview review) {
            for (FirebaseDbObserver<FbReview> observer : mObservers) {
                observer.onAdded(review);
            }
        }

        private void notifyOnChildChanged(FbReview review) {
            for (FirebaseDbObserver<FbReview> observer : mObservers) {
                observer.onChanged(review);
            }
        }

        private void notifyOnChildRemoved(FbReview review) {
            for (FirebaseDbObserver<FbReview> observer : mObservers) {
                observer.onRemoved(review);
            }
        }
    }
}
