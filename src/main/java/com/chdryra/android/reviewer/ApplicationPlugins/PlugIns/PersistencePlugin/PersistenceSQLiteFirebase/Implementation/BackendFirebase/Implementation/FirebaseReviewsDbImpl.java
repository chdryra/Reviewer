/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.BackendFirebase
        .Implementation;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation
        .BackendError;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation
        .ReviewDb;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Interfaces.BackendReviewsDb;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Interfaces.DbObserver;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.BackendFirebase
        .HierarchyStructuring.DbUpdater;
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
public class FirebaseReviewsDbImpl implements BackendReviewsDb {
    private Firebase mDataBase;
    private FirebaseStructure mStructure;
    private FirebaseValidator mValidator;
    private ArrayList<DbObserver<ReviewDb>> mObservers;

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
    public void addReview(ReviewDb review, AddReviewCallback callback) {
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
    public void registerObserver(DbObserver<ReviewDb> observer) {
        if (!mObservers.contains(observer)) mObservers.add(observer);
    }

    @Override
    public void unregisterObserver(DbObserver<ReviewDb> observer) {
        if (mObservers.contains(observer)) mObservers.remove(observer);
    }

    private Firebase getReviewsRoot() {
        return mDataBase.child(mStructure.pathToReviewsData());
    }

    private Firebase getReviewsListRoot() {
        return mDataBase.child(mStructure.pathToReviewsList());
    }

    private Firebase getReviewRoot(String reviewId) {
        return mDataBase.child(mStructure.pathToReview(reviewId));
    }

    @NonNull
    private Map<String, Object> getUpdatesMap(ReviewDb review, DbUpdater.UpdateType type) {
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
                final ReviewDb review = dataSnapshot.getValue(ReviewDb.class);
                if (mValidator.isValid(review)) doDelete(review, callback);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                callback.onReviewDeleted(reviewId, newBackendError(firebaseError));
            }
        };
    }

    @Nullable
    private BackendError newBackendError(FirebaseError firebaseError) {
        return firebaseError != null ? FirebaseBackend.backendError(firebaseError) : null;
    }

    private void doDelete(ReviewDb review, DeleteReviewCallback callback) {
        Map<String, Object> deleteMap = getUpdatesMap(review, DbUpdater.UpdateType.DELETE);
        mDataBase.updateChildren(deleteMap, newDeleteListener(review.getReviewId(), callback));
    }

    @NonNull
    private ValueEventListener newGetListener(final GetReviewCallback listener) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ReviewDb review = dataSnapshot.getValue(ReviewDb.class);
                listener.onReview(review, null);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                listener.onReview(new ReviewDb(), newBackendError(firebaseError));
            }
        };
    }

    @NonNull
    private ValueEventListener newGetCollectionListener(final GetCollectionCallback listener) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<ReviewDb> reviews = new ArrayList<>();
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    ReviewDb review = childSnapshot.getValue(ReviewDb.class);
                    review.setReviewId(childSnapshot.getKey());
                    if (mValidator.isIdValid(review)) reviews.add(review);
                }

                listener.onReviewCollection(reviews, null);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                listener.onReviewCollection(new ArrayList<ReviewDb>(), newBackendError
                        (firebaseError));
            }
        };
    }

    @NonNull
    private Firebase.CompletionListener newAddListener(final ReviewDb review,
                                                       final AddReviewCallback listener) {
        return new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                listener.onReviewAdded(review, newBackendError(firebaseError));
            }
        };
    }

    @NonNull
    private Firebase.CompletionListener newDeleteListener(final String reviewId,
                                                          final DeleteReviewCallback listener) {
        return new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                listener.onReviewDeleted(reviewId, newBackendError(firebaseError));
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

        private ReviewDb getReview(DataSnapshot dataSnapshot) {
            return dataSnapshot.getValue(ReviewDb.class);
        }

        private void notifyOnChildAdded(ReviewDb review) {
            for (DbObserver<ReviewDb> observer : mObservers) {
                observer.onAdded(review);
            }
        }

        private void notifyOnChildChanged(ReviewDb review) {
            for (DbObserver<ReviewDb> observer : mObservers) {
                observer.onChanged(review);
            }
        }

        private void notifyOnChildRemoved(ReviewDb review) {
            for (DbObserver<ReviewDb> observer : mObservers) {
                observer.onRemoved(review);
            }
        }
    }
}
