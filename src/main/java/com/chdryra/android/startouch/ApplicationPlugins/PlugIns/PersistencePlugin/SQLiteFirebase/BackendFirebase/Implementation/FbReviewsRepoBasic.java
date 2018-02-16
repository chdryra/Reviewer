/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
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
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.BackendError;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Interfaces.FbReviews;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Interfaces.SnapshotConverter;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.Size;
import com.chdryra.android.startouch.DataDefinitions.References.Implementation.SizeReferencer;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.startouch.Persistence.Implementation.RepoResult;
import com.chdryra.android.startouch.Persistence.Implementation.ReviewDereferencer;
import com.chdryra.android.startouch.Persistence.Interfaces.RepoCallback;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsRepoReadable;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.List;

/**
 * Created by: Rizwan Choudrey
 * On: 12/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class FbReviewsRepoBasic extends FbListReferenceBasic<ReviewReference,
        List<ReviewReference>, Size> implements ReviewsRepoReadable {
    static final CallbackMessage NULL_AT_SOURCE
            = CallbackMessage.error("Null at source");
    private static final CallbackMessage REFERENCING_ERROR
            = CallbackMessage.error("Referencing error");

    private final Firebase mDataBase;
    private final FbReviews mStructure;
    private final SnapshotConverter<ReviewReference> mConverter;
    private final ReviewDereferencer mDereferencer;
    private final SizeReferencer mSizeReferencer;

    protected interface ReferenceReadyCallback {
        void onReferenceReady(@Nullable ReviewReference reference);
    }

    FbReviewsRepoBasic(Firebase dataBase,
                       FbReviews structure,
                       SnapshotConverter<ReviewReference> converter,
                       ReviewDereferencer dereferencer,
                       SizeReferencer sizeReferencer) {
        super(dataBase, new ListConverter<>(converter), converter);
        mDataBase = dataBase;
        mConverter = converter;
        mStructure = structure;
        mDereferencer = dereferencer;
        mSizeReferencer = sizeReferencer;
    }

    @Override
    public DataReference<Size> getSize() {
        return mSizeReferencer.newSizeReference(this);
    }

    @Override
    protected void doBinding(ChildEventListener listener) {
        getQuery().addChildEventListener(listener);
    }

    @Override
    protected void doUnbinding(ChildEventListener listener) {
        getQuery().removeEventListener(listener);
    }

    @Override
    public void getReference(ReviewId reviewId, RepoCallback callback) {
        Firebase entry = mStructure.getListEntryDb(mDataBase, reviewId);
        doSingleEvent(entry, newGetReferenceListener(reviewId, callback));
    }

    @Override
    public void getReview(ReviewId reviewId, RepoCallback callback) {
        mDereferencer.getReview(reviewId, this, callback);
    }

    Firebase getDataBase() {
        return mDataBase;
    }

    private Query getQuery() {
        return getQuery(mStructure.getListEntriesDb(mDataBase));
    }

    private Query getQuery(Firebase entriesDb) {
        return entriesDb.orderByChild(ReviewListEntry.DATE);
    }

    @NonNull
    private ValueEventListener newGetReferenceListener(final ReviewId reviewId,
                                                       final RepoCallback callback) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    callback.onRepoCallback(new RepoResult(reviewId, NULL_AT_SOURCE));
                    return;
                }

                newReference(dataSnapshot, new ReferenceReadyCallback() {
                    @Override
                    public void onReferenceReady(ReviewReference reference) {
                        RepoResult result;
                        if (reference != null) {
                            result = new RepoResult(reference);
                        } else {
                            result = new RepoResult(reviewId, REFERENCING_ERROR);
                        }
                        callback.onRepoCallback(result);
                    }
                });
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                BackendError error = FirebaseBackend.backendError(firebaseError);
                callback.onRepoCallback(new RepoResult(reviewId, CallbackMessage.error(error
                        .getMessage())));
            }
        };
    }

    protected void newReference(DataSnapshot dataSnapshot, final ReferenceReadyCallback callback) {
        callback.onReferenceReady(mConverter.convert(dataSnapshot));
    }

    private void doSingleEvent(Firebase root, ValueEventListener listener) {
        root.addListenerForSingleValueEvent(listener);
    }
}
