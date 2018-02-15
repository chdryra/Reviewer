/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.DataDefinitions.References.Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.SizeImpl;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.Size;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.ListItemBinder;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.ListReference;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReferenceBinder;

import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 15/02/2018
 * Email: rizwan.choudrey@gmail.com
 */

public class SizeReference<T, C extends Collection<T>> extends DereferencableBasic<Size> implements
        ListItemBinder<T>, DataReference<Size> {
    private final ListReference<T, C, Size> mReference;
    private int mSize = 0;
    private boolean mIsBound = false;
    private boolean mIsValid = true;

    public SizeReference(ListReference<T, C, Size> reference) {
        mReference = reference;
    }

    @Override
    protected void doDereferencing(final DereferenceCallback<Size> callback) {
        mReference.dereference(new DereferenceCallback<C>() {
            @Override
            public void onDereferenced(DataValue<C> value) {
                DataValue<Size> val = value.hasValue() ? new DataValue<Size>(getSize(value
                        .getData().size())) :
                        new DataValue<Size>(value.getMessage());
                callback.onDereferenced(val);
            }
        });
    }

    @Override
    public void onItemAdded(T value) {
        ++mSize;
        notifyBinders();
    }

    @Override
    public void onItemRemoved(T value) {
        --mSize;
        notifyBinders();
    }

    @Override
    public void onListChanged(Collection<T> newItems) {
        mSize = newItems.size();
        notifyBinders();
    }

    @Override
    public void onInvalidated(ListReference<T, ?, ?> reference) {
        onInvalidate();
    }

    @Override
    protected void onInvalidate() {
        mSize = 0;
        mReference.unbindFromItems(this);
        mIsBound = false;
        mIsValid = false;
        super.onInvalidate();
    }

    @Override
    protected void fireForBinder(ReferenceBinder<Size> binder) {
        if (!mIsValid) {
            binder.onInvalidated(this);
            return;
        }

        if (mIsBound) {
            binder.onReferenceValue(getSize(mSize));
        } else {
            mReference.bindToItems(this);
            mIsBound = true;
        }
    }

    @NonNull
    private SizeImpl getSize(int size) {
        return new SizeImpl(size);
    }

    private void notifyBinders() {
        for (ReferenceBinder<Size> binder : getBinders()) {
            binder.onReferenceValue(getSize(mSize));
        }
    }
}
