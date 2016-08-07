/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumSize;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataReference;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSize;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReferenceBinder;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 07/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class TreeSizeReferenceBasic<T extends HasReviewId>
        extends DataReferenceBasic<DataSize>
        implements
        ReviewItemReference<DataSize>,
        DataReference.InvalidationListener,
        ReviewNode.NodeObserver {

    private static final CallbackMessage OK = CallbackMessage.ok();
    protected Collection<ReferenceBinder<DataSize>> mValueBinders;
    protected boolean mCached = false;
    private TreeDataReferenceBasic<T> mDataReference;
    private int mCurrentSize = 0;

    protected abstract void incrementForChild(ReviewNode child);

    protected abstract void decrementForChild(ReviewNode child);

    protected abstract void doDereference(final DereferenceCallback<DataSize> callback);

    public TreeSizeReferenceBasic(TreeDataReferenceBasic<T> dataReference) {
        mDataReference = dataReference;
        mDataReference.registerListener(this);
        mValueBinders = new ArrayList<>();
    }

    protected TreeDataReferenceBasic<T> getReference() {
        return mDataReference;
    }

    @NonNull
    protected DatumSize getSize() {
        return new DatumSize(getReviewId(), mCurrentSize);
    }

    protected void setSize(int size) {
        mCached = true;
        mCurrentSize = size;
    }

    protected void addSize(int size) {
        mCached = true;
        mCurrentSize += size;
    }

    protected void removeSize(int size) {
        mCached = true;
        mCurrentSize -= size;
    }

    protected void notifyValueBinders() {
        if (mValueBinders.size() > 0) {
            dereference(new DereferenceCallback<DataSize>() {
                @Override
                public void onDereferenced(@Nullable DataSize data, CallbackMessage message) {
                    if (data != null && !message.isError()) notifyValueBinders(data);
                }
            });
        }
    }

    protected void notifyValueBinders(DataSize size) {
        for (ReferenceBinder<DataSize> binder : mValueBinders) {
            binder.onReferenceValue(size);
        }
    }

    protected void notifyCachedSize(DereferenceCallback<DataSize> callback) {
        callback.onDereferenced(getSize(), OK);
    }

    @Override
    public void onChildRemoved(ReviewNode child) {
        mCached = false;
        decrementForChild(child);
    }

    @Override
    public void onChildAdded(ReviewNode child) {
        mCached = false;
        incrementForChild(child);
    }

    @Override
    public void dereference(final DereferenceCallback<DataSize> callback) {
        if (!mCached) {
            doDereference(callback);
        } else {
            notifyCachedSize(callback);
        }
    }

    @Override
    public void onNodeChanged() {
        mCached = false;
        notifyValueBinders();
    }

    @Override
    public void unbindFromValue(ReferenceBinder<DataSize> binder) {
        if (mValueBinders.contains(binder)) mValueBinders.remove(binder);
    }

    @Override
    public void bindToValue(final ReferenceBinder<DataSize> binder) {
        if (!mValueBinders.contains(binder)) mValueBinders.add(binder);
        dereference(new DereferenceCallback<DataSize>() {
            @Override
            public void onDereferenced(@Nullable DataSize data, CallbackMessage message) {
                if (data != null && !message.isError()) binder.onReferenceValue(data);
            }
        });
    }

    @Override
    public ReviewId getReviewId() {
        return mDataReference.getReviewId();
    }

    @Override
    protected void onInvalidate() {
        super.onInvalidate();
        mDataReference.unregisterListener(this);
    }

    @Override
    public void onReferenceInvalidated(DataReference<?> reference) {
        invalidate();
    }
}
