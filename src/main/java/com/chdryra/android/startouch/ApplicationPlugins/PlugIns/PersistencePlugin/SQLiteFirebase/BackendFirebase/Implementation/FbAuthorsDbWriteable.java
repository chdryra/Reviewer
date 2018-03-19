/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Implementation;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.corelibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.corelibrary.ReferenceModel.Implementation.SizeReferencer;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Factories.BackendReviewConverter;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.BackendError;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.BackendValidator;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.ReviewDb;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Interfaces.FbAuthorsDb;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Interfaces.SnapshotConverter;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Structuring.DbUpdater;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.startouch.Persistence.Implementation.RepoResult;
import com.chdryra.android.startouch.Persistence.Implementation.ReviewDereferencer;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsCache;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsRepoWriteable;
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
public class FbAuthorsDbWriteable extends FbAuthorsDbReadable implements ReviewsRepoWriteable {
    private final BackendReviewConverter mConverter;
    private final BackendValidator mValidator;
    private final ReviewsCache mCache;

    public FbAuthorsDbWriteable(Firebase dataBase,
                                FbAuthorsDb structure,
                                SnapshotConverter<ReviewReference> referenceConverter,
                                BackendReviewConverter converter,
                                BackendValidator validator,
                                ReviewDereferencer dereferencer,
                                SizeReferencer sizeReferencer,
                                ReviewsCache cache) {
        super(dataBase, structure, referenceConverter, dereferencer, sizeReferencer);
        mConverter = converter;
        mValidator = validator;
        mCache = cache;
    }

    @Override
    public void addReview(Review review, Callback callback) {
        ReviewDb reviewDb = mConverter.convert(review);
        Map<String, Object> map = getUpdatesMap(reviewDb, DbUpdater.UpdateType.INSERT_OR_UPDATE);
        getFbReference().updateChildren(map, newAddListener(reviewDb, callback));
    }

    @Override
    public void removeReview(ReviewId reviewId, Callback callback) {
        getReviewEntry(reviewId, newGetAndDeleteListener(reviewId, callback));
    }

    private void getReviewEntry(ReviewId id, ValueEventListener onReviewFound) {
        Firebase listEntryDb = getStructure().getListEntryDb(getFbReference(), id);
        doSingleEvent(listEntryDb, newOnEntryFoundListener(onReviewFound));
    }

    @NonNull
    private ValueEventListener newOnEntryFoundListener(final ValueEventListener onReviewFound) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ReviewId id = new DatumReviewId(dataSnapshot.getKey());
                doSingleEvent(getStructure().getReviewDb(getFbReference(), id), onReviewFound);
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
    Callback callback) {
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
                        CallbackMessage.error(beError.getMessage()) : CallbackMessage.error
                        ("Firebase cancelled");
                callback.onRemovedFromRepo(new RepoResult(reviewId, error));
            }
        };
    }

    @Nullable
    private BackendError newBackendError(FirebaseError firebaseError) {
        return firebaseError != null ? FirebaseBackend.backendError(firebaseError) : null;
    }

    private void doDelete(ReviewDb review, Callback callback) {
        Map<String, Object> deleteMap = getUpdatesMap(review, DbUpdater.UpdateType.DELETE);
        getFbReference().updateChildren(deleteMap, newDeleteListener(review, callback));
    }

    @NonNull
    private Firebase.CompletionListener newAddListener(final ReviewDb review,
                                                       final Callback callback) {
        return new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                RepoResult result;
                if (firebaseError == null) {
                    Review added = mConverter.convert(review);
                    result = new RepoResult(added);
                    ReviewId reviewId = added.getReviewId();
                    if (mCache.contains(reviewId)) {
                        mCache.remove(reviewId);
                        mCache.add(added);
                    }
                } else {
                    result = new RepoResult(CallbackMessage.error(firebaseError.getMessage
                            ()));
                }

                callback.onAddedToRepo(result);
            }
        };
    }

    @NonNull
    private Firebase.CompletionListener newDeleteListener(final ReviewDb review,
                                                          final Callback callback) {
        return new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                BackendError backendError = newBackendError(firebaseError);
                CallbackMessage message = backendError == null ?
                        CallbackMessage.ok() : CallbackMessage.error(backendError.getMessage());

                ReviewId reviewId = new DatumReviewId(review.getReviewId());
                if (message.isOk() && mCache.contains(reviewId)) mCache.remove(reviewId);

                callback.onRemovedFromRepo(new RepoResult(mConverter.convert
                        (review), message));
            }
        };
    }
}
