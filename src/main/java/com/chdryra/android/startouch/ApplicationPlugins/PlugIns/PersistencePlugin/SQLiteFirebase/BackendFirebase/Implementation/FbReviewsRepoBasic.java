/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Implementation;


import com.chdryra.android.corelibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.corelibrary.ReferenceModel.Implementation.SizeReferencer;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Interfaces.FbReviews;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Interfaces.SnapshotConverter;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.startouch.Persistence.Implementation.ReviewDereferencer;
import com.chdryra.android.startouch.Persistence.Interfaces.RepoCallback;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsRepoReadable;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.Query;

/**
 * Created by: Rizwan Choudrey
 * On: 12/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class FbReviewsRepoBasic extends FbReviewsListBasic<ReviewReference> implements ReviewsRepoReadable {
    private final ReviewDereferencer mDereferencer;

    FbReviewsRepoBasic(Firebase dataBase,
                       FbReviews structure,
                       SnapshotConverter<ReviewReference> converter,
                       ReviewDereferencer dereferencer,
                       SizeReferencer sizeReferencer) {
        super(dataBase, structure, converter, sizeReferencer);
        mDereferencer = dereferencer;
    }

    @Override
    protected void doBinding(ChildEventListener listener) {
        getRoot().addChildEventListener(listener);
    }

    @Override
    protected void doUnbinding(ChildEventListener listener) {
        getRoot().removeEventListener(listener);
    }

    @Override
    public void getReview(ReviewId reviewId, RepoCallback callback) {
        mDereferencer.getReview(reviewId, this, callback);
    }

    @Override
    protected Query getRoot() {
        return super.getRoot().orderByChild(ReviewListEntry.DATE);
    }

    @Override
    protected void getReference(ReviewId reviewId, DataSnapshot dataSnapshot, FbReviewsListBasic.ReferenceReadyCallback callback) {
        ReviewReference convert = getItemConverter().convert(dataSnapshot);
        if(convert != null) {
            callback.onReferenceReady(convert.getReviewId(), convert, CallbackMessage.ok());
        } else {
            callback.onReferenceReady(reviewId, null, CallbackMessage.error("No reference"));
        }
    }
}
