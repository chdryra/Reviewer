/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.BackendFirebase.Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.BackendFirebase.Interfaces.FirebaseDb;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.BackendFirebase.Interfaces
        .FirebaseDbObserver;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 23/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FirebaseReviewsDb implements FirebaseDb {
    private static final String REVIEWS_ROOT = "Reviews";
    private Firebase mDataRoot;
    private FirebaseValidator mValidator;
    private ArrayList<FirebaseDbObserver> mObservers;

    public FirebaseReviewsDb(Firebase dataRoot, FirebaseValidator validator) {
        mDataRoot = dataRoot;
        mValidator = validator;
        mObservers = new ArrayList<>();
        mDataRoot.child(REVIEWS_ROOT).addChildEventListener(new ChildListener());
    }

    @Override
    public void addReview(final FbReview review, AddCallback callback) {
        Firebase root = mDataRoot.child(REVIEWS_ROOT).child(review.getReviewId());
        root.setValue(review, newAddListener(review, callback));
    }

    @Override
    public void deleteReview(String reviewId, DeleteCallback callback) {
        Firebase ref = mDataRoot.child(REVIEWS_ROOT).child(reviewId);
        ref.removeValue(newDeleteListener(reviewId, callback));
    }

    @Override
    public void getReview(String id, GetCallback callback) {
        Firebase root = mDataRoot.child(REVIEWS_ROOT).child(id);
        root.addListenerForSingleValueEvent(newGetListener(callback));
    }

    @Override
    public void getReviews(GetCollectionCallback callback) {
        Firebase root = mDataRoot.child(REVIEWS_ROOT);
        root.addListenerForSingleValueEvent(newGetCollectionListener(callback));
    }

    @Override
    public void registerObserver(FirebaseDbObserver observer) {
        if (!mObservers.contains(observer)) mObservers.add(observer);
    }

    @Override
    public void unregisterObserver(FirebaseDbObserver observer) {
        if (mObservers.contains(observer)) mObservers.remove(observer);
    }

    @NonNull
    private ValueEventListener newGetListener(final GetCallback listener) {
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
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    FbReview review = postSnapshot.getValue(FbReview.class);
                    if (mValidator.isValid(review)) reviews.add(review);
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
                                                       final AddCallback listener) {
        return new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                listener.onReviewAdded(review, firebaseError);
            }
        };
    }

    @NonNull
    private Firebase.CompletionListener newDeleteListener(final String reviewId,
                                                          final DeleteCallback listener) {
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
            for (FirebaseDbObserver observer : mObservers) {
                observer.onReviewAdded(review);
            }
        }

        private void notifyOnChildChanged(FbReview review) {
            for (FirebaseDbObserver observer : mObservers) {
                observer.onReviewChanged(review);
            }
        }

        private void notifyOnChildRemoved(FbReview review) {
            for (FirebaseDbObserver observer : mObservers) {
                observer.onReviewRemoved(review);
            }
        }
    }
}
