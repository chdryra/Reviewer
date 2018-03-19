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
import com.chdryra.android.corelibrary.ReferenceModel.Implementation.SizeReferencer;
import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.DataReference;
import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.Size;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.BackendError;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Interfaces.FbReviews;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Interfaces.SnapshotConverter;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.startouch.Persistence.Implementation.RepoResult;
import com.chdryra.android.startouch.Persistence.Interfaces.RepoCallback;
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
public abstract class FbReviewsListBasic<T extends HasReviewId> extends FbListReferenceBasic<T,
        List<T>, Size> {
    private static final CallbackMessage NULL_AT_SOURCE
            = CallbackMessage.error("Null at source");

    private final Firebase mDataBase;
    private final FbReviews mStructure;
    private final SizeReferencer mSizeReferencer;

    protected interface ReferenceReadyCallback {
        void onReferenceReady(ReviewId reviewId, @Nullable ReviewReference reference,
                              CallbackMessage message);
    }

    protected abstract void getReference(ReviewId reviewId,
                                         DataSnapshot dataSnapshot,
                                         ReferenceReadyCallback callback);

    FbReviewsListBasic(Firebase dataBase,
                       FbReviews structure,
                       SnapshotConverter<T> converter,
                       SizeReferencer sizeReferencer) {
        super(dataBase, new ListConverter<>(converter), converter);
        mDataBase = dataBase;
        mStructure = structure;
        mSizeReferencer = sizeReferencer;
    }

    public FbReviews getStructure() {
        return mStructure;
    }

    public void getReference(ReviewId reviewId, RepoCallback callback) {
        Firebase entry = mStructure.getListEntryDb(mDataBase, reviewId);
        doSingleEvent(entry, newGetReferenceListener(reviewId, callback));
    }

    protected Query getRoot() {
        return mStructure.getListEntriesDb(mDataBase);
    }

    @Override
    public DataReference<Size> getSize() {
        return mSizeReferencer.newSizeReference(this);
    }

    @Override
    protected void doBinding(ChildEventListener listener) {
        getRoot().addChildEventListener(listener);
    }

    @Override
    protected void doUnbinding(ChildEventListener listener) {
        getRoot().removeEventListener(listener);
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

                getReference(reviewId, dataSnapshot, new ReferenceReadyCallback() {
                    @Override
                    public void onReferenceReady(ReviewId reviewId,
                                                 ReviewReference reference,
                                                 CallbackMessage message) {
                        RepoResult result;
                        if (reference != null && message.isOk()) {
                            result = new RepoResult(reference);
                        } else {
                            result = new RepoResult(reviewId, message);
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

    private void doSingleEvent(Firebase root, ValueEventListener listener) {
        root.addListenerForSingleValueEvent(listener);
    }
}
