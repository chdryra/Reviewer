/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Model.ReviewsModel.Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumSize;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataSize;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.DataDefinitions.References.Implementation.DataReferenceBasic;
import com.chdryra.android.startouch.DataDefinitions.References.Implementation.DataValue;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReferenceBinder;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNode;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 07/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class TreeSizeRefBasic<Value extends HasReviewId>
        extends DataReferenceBasic<DataSize>
        implements
        ReviewItemReference<DataSize>,
        DataReference.InvalidationListener,
        ReviewNode.NodeObserver {

    private final Collection<ReferenceBinder<DataSize>> mValueBinders;
    private boolean mCached = false;
    private final TreeDataReferenceBasic<Value, ?> mDataReference;
    private int mCurrentSize = 0;

    protected abstract void incrementForChild(ReviewNode child);

    protected abstract void decrementForChild(ReviewNode child);

    protected abstract void doDereference(final DereferenceCallback<DataSize> callback);

    TreeSizeRefBasic(TreeDataReferenceBasic<Value, ?> dataReference) {
        mDataReference = dataReference;
        mDataReference.registerListener(this);
        mValueBinders = new ArrayList<>();
    }

    TreeDataReferenceBasic<Value, ?> getReference() {
        return mDataReference;
    }

    @NonNull
    DataSize getSize() {
        return new DatumSize(getReviewId(), mCurrentSize);
    }

    void setSize(int size) {
        mCached = true;
        mCurrentSize = size;
    }

    void addSize(int size) {
        mCached = true;
        mCurrentSize += size;
    }

    void removeSize(int size) {
        mCached = true;
        mCurrentSize -= size;
    }

    private void notifyValueBinders() {
        if (mValueBinders.size() > 0) {
            dereference(new DereferenceCallback<DataSize>() {
                @Override
                public void onDereferenced(DataValue<DataSize> value) {
                    if (value.hasValue()) notifyValueBinders(value.getData());
                }
            });
        }
    }

    void notifyValueBinders(DataSize size) {
        for (ReferenceBinder<DataSize> binder : mValueBinders) {
            binder.onReferenceValue(size);
        }
    }

    private void notifyCachedSize(DereferenceCallback<DataSize> callback) {
        callback.onDereferenced(new DataValue<>(getSize()));
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

    }

    @Override
    public void onTreeChanged() {
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
            public void onDereferenced(DataValue<DataSize> value) {
                if (value.hasValue()) binder.onReferenceValue(value.getData());
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
