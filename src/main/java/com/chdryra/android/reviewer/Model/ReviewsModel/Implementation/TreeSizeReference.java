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
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReferenceBinder;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 05/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class TreeSizeReference<T extends HasReviewId> extends DataReferenceBasic<DataSize>
        implements
        ReviewItemReference<DataSize>,
        DataReference.InvalidationListener, ReviewNode.NodeObserver {
    private static final CallbackMessage OK = CallbackMessage.ok();

    private TreeInfoReference<T> mDataReference;
    private Collection<ReferenceBinder<DataSize>> mValueBinders;
    private boolean mCached = false;
    private int mCurrentSize = 0;

    public TreeSizeReference(TreeInfoReference<T> dataReference) {
        mDataReference = dataReference;
        mDataReference.registerListener(this);
        mValueBinders = new ArrayList<>();
    }

    @Override
    public void onReferenceInvalidated(DataReference<?> reference) {
        invalidate();
    }

    @Override
    protected void onInvalidate() {
        super.onInvalidate();
        mDataReference.unregisterListener(this);
    }

    @Override
    public ReviewId getReviewId() {
        return mDataReference.getReviewId();
    }

    @Override
    public void dereference(final DereferenceCallback<DataSize> callback) {
        if (!mCached) {
            mDataReference.getData(new TreeDataReferenceBasic.GetDataCallback<T>() {
                @Override
                public void onData(IdableList<T> items) {
                    setSize(items.size());
                    callback.onDereferenced(getSize(), OK);
                }
            });
        } else {
            callback.onDereferenced(getSize(), OK);
        }
    }

    @Override
    public void bindToValue(final ReferenceBinder<DataSize> binder) {
        if (!mValueBinders.contains(binder)) mValueBinders.add(binder);
        dereference(new DereferenceCallback<DataSize>() {
            @Override
            public void onDereferenced(@Nullable DataSize data, CallbackMessage message) {
                if (data != null) binder.onReferenceValue(data);
            }
        });
    }

    @Override
    public void unbindFromValue(ReferenceBinder<DataSize> binder) {
        if (mValueBinders.contains(binder)) mValueBinders.remove(binder);
    }

    @Override
    public void onChildAdded(ReviewNode child) {
        mCached = false;
        mDataReference.getData(child.getReviewId(), new TreeDataReferenceBasic.GetDataCallback<T>() {
            @Override
            public void onData(IdableList<T> items) {
                addSize(items.size());
                notifyValueBinders(getSize());
            }
        });
    }

    @Override
    public void onChildRemoved(ReviewNode child) {
        mCached = false;
        mDataReference.getData(child.getReviewId(), new TreeDataReferenceBasic.GetDataCallback<T>() {
            @Override
            public void onData(IdableList<T> items) {
                removeSize(items.size());
                notifyValueBinders(getSize());
            }
        });
    }

    @Override
    public void onNodeChanged() {
        mCached = false;
        notifyValueBinders();
    }

    @NonNull
    private DatumSize getSize() {
        return new DatumSize(getReviewId(), mCurrentSize);
    }

    private void setSize(int size) {
        mCached = true;
        mCurrentSize = size;
    }

    private void addSize(int size) {
        mCached = true;
        mCurrentSize += size;
    }

    private void removeSize(int size) {
        mCached = true;
        mCurrentSize -= size;
    }

    private void notifyValueBinders() {
        if (mValueBinders.size() > 0) {
            dereference(new DereferenceCallback<DataSize>() {
                @Override
                public void onDereferenced(@Nullable DataSize data, CallbackMessage message) {
                    if (data != null && !message.isError()) notifyValueBinders(data);
                }
            });
        }
    }

    private void notifyValueBinders(DataSize size) {
        for (ReferenceBinder<DataSize> binder : mValueBinders) {
            binder.onReferenceValue(size);
        }
    }
}
