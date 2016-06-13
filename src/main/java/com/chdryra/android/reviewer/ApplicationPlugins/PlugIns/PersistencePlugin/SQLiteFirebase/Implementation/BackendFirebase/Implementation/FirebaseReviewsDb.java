/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase
        .Implementation;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.Author;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.BackendError;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.BackendValidator;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.ReviewDb;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Interfaces.BackendReviewsDb;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Interfaces.DbObserver;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Interfaces.FbReviewsStructure;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Structuring.DbUpdater;
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
public class FirebaseReviewsDb implements BackendReviewsDb {
    private Firebase mDataBase;
    private FbReviewsStructure mStructure;
    private BackendValidator mValidator;
    private ArrayList<DbObserver<ReviewDb>> mObservers;

    public FirebaseReviewsDb(Firebase dataBase,
                             FbReviewsStructure structure,
                             BackendValidator validator) {
        mDataBase = dataBase;
        mStructure = structure;
        mValidator = validator;
        mObservers = new ArrayList<>();
    }

    @Override
    public void addReview(ReviewDb review, AddReviewCallback callback) {
        Map<String, Object> map = getUpdatesMap(review, DbUpdater.UpdateType.INSERT_OR_UPDATE);
        mDataBase.updateChildren(map, newAddListener(review, callback));
    }

    @Override
    public void deleteReview(String reviewId, final DeleteReviewCallback callback) {
        getReviewEntry(reviewId, newGetAndDeleteListener(reviewId, callback));
    }

    @Override
    public void getReview(final String reviewId, final GetReviewCallback callback) {
        Firebase listEntryDb = mStructure.getListEntryDb(mDataBase, reviewId);
        doSingleEvent(listEntryDb, newGetReferenceListener(reviewId, callback));
    }

    @Override
    public void getReviews(final Author author, final GetCollectionCallback callback) {
        Firebase listEntriesDb = mStructure.getListEntriesDb(mDataBase, author);
        doSingleEvent(listEntriesDb, newGetReferenceCollectionListener(author, callback));
    }

    @Override
    public void getReviewsList(Author author, GetCollectionCallback callback) {
        getReviews(author, callback);
    }

    @Override
    public void registerObserver(DbObserver<ReviewDb> observer) {
        if (!mObservers.contains(observer)) mObservers.add(observer);
    }

    @Override
    public void unregisterObserver(DbObserver<ReviewDb> observer) {
        if (mObservers.contains(observer)) mObservers.remove(observer);
    }

    @NonNull
    private ValueEventListener newGetReferenceListener(final String reviewId, final
    GetReviewCallback callback) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ReviewListEntry entry = dataSnapshot.getValue(ReviewListEntry.class);
                Firebase reviewDb = mStructure.getReviewDb(mDataBase, entry.getAuthor(), reviewId);
                callback.onReview(new FbReviewReference(entry, reviewDb), null);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                callback.onReview(new NullReviewReference(), FirebaseBackend.backendError
                        (firebaseError));
            }
        };
    }

    @NonNull
    private ValueEventListener newGetReferenceCollectionListener(final Author author, final
    GetCollectionCallback callback) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<ReviewReference> references = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    ReviewListEntry entry = child.getValue(ReviewListEntry.class);
                    Firebase reviewDb = mStructure.getReviewDb(mDataBase, entry.getAuthor(),
                            entry.getReviewId());
                    references.add(new FbReviewReference(entry, reviewDb));
                }

                callback.onReviewCollection(author, references, null);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                callback.onReviewCollection(author, new ArrayList<ReviewReference>(),
                        FirebaseBackend.backendError(firebaseError));
            }
        };
    }

    private void getReviewEntry(String reviewId, final ValueEventListener onReviewFound) {
        Firebase listEntryDb = mStructure.getListEntryDb(mDataBase, reviewId);
        doSingleEvent(listEntryDb, newOnEntryFoundListener(onReviewFound));
    }

    @NonNull
    private ValueEventListener newOnEntryFoundListener(final ValueEventListener onReviewFound) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final ReviewListEntry entry = dataSnapshot.getValue(ReviewListEntry.class);
                doSingleEvent(mStructure.getReviewDb(mDataBase, entry.getAuthor(), dataSnapshot
                        .getKey()), onReviewFound);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                onReviewFound.onCancelled(firebaseError);
            }
        };
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
}
