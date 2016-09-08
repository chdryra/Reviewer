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
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Factories.BackendReviewConverter;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.BackendError;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.BackendValidator;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.ReviewDb;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Factories.FactoryFbReviewReference;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Interfaces.FbAuthorsReviews;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Interfaces.SnapshotConverter;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Structuring.DbUpdater;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Persistence.Implementation.RepositoryResult;
import com.chdryra.android.reviewer.Persistence.Interfaces.MutableRepoCallback;
import com.chdryra.android.reviewer.Persistence.Interfaces.MutableRepository;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 23/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FbAuthorReviewsMutable extends FbAuthorReviewsReadable implements MutableRepository {
    private final BackendReviewConverter mConverter;
    private final BackendValidator mValidator;

    public FbAuthorReviewsMutable(Firebase dataBase,
                                  FbAuthorsReviews structure,
                                  SnapshotConverter<ReviewListEntry> entryConverter,
                                  BackendReviewConverter converter,
                                  BackendValidator validator,
                                  FactoryFbReviewReference referencer) {
        super(dataBase, structure, entryConverter, referencer);
        mConverter = converter;
        mValidator = validator;
    }

    @Override
    public void addReview(Review review, MutableRepoCallback callback) {
        ReviewDb reviewDb = mConverter.convert(review);
        Map<String, Object> map = getUpdatesMap(reviewDb, DbUpdater.UpdateType.INSERT_OR_UPDATE);
        getDataBase().updateChildren(map, newAddListener(reviewDb, callback));
    }

    @Override
    public void removeReview(ReviewId reviewId, MutableRepoCallback callback) {
        getReviewEntry(reviewId, newGetAndDeleteListener(reviewId, callback));
    }

    private void getReviewEntry(ReviewId id, ValueEventListener onReviewFound) {
        Firebase listEntryDb = getStructure().getListEntryDb(getDataBase(), id);
        doSingleEvent(listEntryDb, newOnEntryFoundListener(onReviewFound));
    }

    @NonNull
    private ValueEventListener newOnEntryFoundListener(final ValueEventListener onReviewFound) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ReviewId id = new DatumReviewId(dataSnapshot.getKey());
                doSingleEvent(getStructure().getReviewDb(getDataBase(), id), onReviewFound);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                onReviewFound.onCancelled(firebaseError);
            }
        };
    }

    @NonNull
    private Map<String, Object> getUpdatesMap(ReviewDb review, DbUpdater.UpdateType type) {
        return getStructure().getReviewUpdater().getUpdatesMap(review, type);
    }

    private void doSingleEvent(Firebase root, ValueEventListener listener) {
        root.addListenerForSingleValueEvent(listener);
    }

    @NonNull
    private ValueEventListener newGetAndDeleteListener(final ReviewId reviewId, final
    MutableRepoCallback callback) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final ReviewDb review = dataSnapshot.getValue(ReviewDb.class);
                if (mValidator.isValid(review)) doDelete(review, callback);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                BackendError beError = newBackendError(firebaseError);
                CallbackMessage error = beError != null ?
                        CallbackMessage.error(beError.getMessage()) : CallbackMessage.error("Firebase cancelled");
                callback.onRemovedFromRepoCallback(new RepositoryResult(reviewId, error));
            }
        };
    }

    @Nullable
    private BackendError newBackendError(FirebaseError firebaseError) {
        return firebaseError != null ? FirebaseBackend.backendError(firebaseError) : null;
    }

    private void doDelete(ReviewDb review, MutableRepoCallback callback) {
        Map<String, Object> deleteMap = getUpdatesMap(review, DbUpdater.UpdateType.DELETE);
        getDataBase().updateChildren(deleteMap, newDeleteListener(review, callback));
    }

    @NonNull
    private Firebase.CompletionListener newAddListener(final ReviewDb review,
                                                       final MutableRepoCallback callback) {
        return new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                RepositoryResult result;
                if(firebaseError == null) {
                    result = new RepositoryResult(mConverter.convert(review));
                } else {
                    result = new RepositoryResult(CallbackMessage.error(firebaseError.getMessage()));
                }

                callback.onAddedToRepoCallback(result);
            }
        };
    }

    @NonNull
    private Firebase.CompletionListener newDeleteListener(final ReviewDb review,
                                                          final MutableRepoCallback callback) {
        return new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                BackendError backendError = newBackendError(firebaseError);
                CallbackMessage message = backendError == null ?
                        CallbackMessage.ok() : CallbackMessage.error(backendError.getMessage());
                callback.onRemovedFromRepoCallback(new RepositoryResult(mConverter.convert(review), message));
            }
        };
    }
}
