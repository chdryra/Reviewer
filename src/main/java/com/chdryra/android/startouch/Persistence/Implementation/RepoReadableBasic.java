/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Persistence.Implementation;

import android.support.annotation.Nullable;

import com.chdryra.android.corelibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.Size;
import com.chdryra.android.startouch.DataDefinitions.References.Implementation.BindableListReferenceBasic;
import com.chdryra.android.startouch.DataDefinitions.References.Implementation.DataValue;
import com.chdryra.android.startouch.DataDefinitions.References.Implementation.ItemBindersDelegate;
import com.chdryra.android.startouch.DataDefinitions.References.Implementation.SizeReferencer;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.CollectionBinder;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.CollectionReference;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.startouch.Persistence.Interfaces.RepoCallback;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsRepoReadable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by: Rizwan Choudrey
 * On: 15/02/2018
 * Email: rizwan.choudrey@gmail.com
 */

public abstract class RepoReadableBasic extends BindableListReferenceBasic<ReviewReference, List<ReviewReference>, Size>
        implements ReviewsRepoReadable, ItemBindersDelegate.BindableListReference<ReviewReference, List<ReviewReference>, Size> {
    private final ReviewDereferencer mDereferencer;
    private final SizeReferencer mSizeReferencer;

    public RepoReadableBasic(ReviewDereferencer dereferencer, SizeReferencer sizeReferencer) {
        mDereferencer = dereferencer;
        mSizeReferencer = sizeReferencer;
    }

    @Override
    public DataReference<Size> getSize() {
        return mSizeReferencer.newSizeReference(this);
    }


    @Override
    public void getReview(ReviewId reviewId, RepoCallback callback) {
        mDereferencer.getReview(reviewId, this, callback);
    }

    @Override
    public void getReference(final ReviewId reviewId, final RepoCallback callback) {
        doDereferencing(new DereferenceCallback<List<ReviewReference>>() {
            @Override
            public void onDereferenced(DataValue<List<ReviewReference>> value) {
                RepoResult result = new RepoResult(CallbackMessage.error("Reference not found"));
                if(value.hasValue()) {
                    for(ReviewReference reference : value.getData()) {
                        if(reference.getReviewId().equals(reviewId)) {
                            result = new RepoResult(reference);
                            break;
                        }
                    }
                }
                callback.onRepoCallback(result);
            }
        });
    }

    @Override
    protected void fireForBinder(final CollectionBinder<ReviewReference> binder) {
        doDereferencing(new DereferenceCallback<List<ReviewReference>>() {
            @Override
            public void onDereferenced(DataValue<List<ReviewReference>> value) {
                if(value.hasValue()) {
                    for(ReviewReference reference : value.getData()) {
                        binder.onItemAdded(reference);
                    }
                }
            }
        });
    }

    @Nullable
    @Override
    protected List<ReviewReference> getNullValue() {
        return new ArrayList<>();
    }

    protected void notifyOnAdd(ReviewReference reference) {
        for (CollectionBinder<ReviewReference> binder : getItemBinders()) {
            binder.onItemAdded(reference);
        }
    }

    protected void notifyOnRemove(ReviewReference reference) {
        for (CollectionBinder<ReviewReference> binder : getItemBinders()) {
            binder.onItemRemoved(reference);
        }
    }

    protected void notifyOnChanged(Collection<ReviewReference> collection) {
        for (CollectionBinder<ReviewReference> binder : getItemBinders()) {
            binder.onCollectionChanged(collection);
        }
    }

    protected void notifyOnInvalidated(CollectionReference<ReviewReference, ?, ?> reference) {
        for (CollectionBinder<ReviewReference> binder : getItemBinders()) {
            binder.onInvalidated(reference);
        }
    }
}