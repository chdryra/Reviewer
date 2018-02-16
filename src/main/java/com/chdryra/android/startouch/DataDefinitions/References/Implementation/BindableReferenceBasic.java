/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.DataDefinitions.References.Implementation;

import android.support.annotation.Nullable;

import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReferenceBinder;

import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 01/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class BindableReferenceBasic<T> extends DataReferenceBasic<T> {

    protected abstract Collection<ReferenceBinder<T>> getBinders();

    protected abstract void removeUnboundBinder(ReferenceBinder<T> binder);

    protected abstract void doDereferencing(DereferenceCallback<T> callback);

    protected abstract boolean contains(ReferenceBinder<T> binder);

    protected abstract void bind(ReferenceBinder<T> binder);

    @Override
    public void dereference(final DereferenceCallback<T> callback) {
        if (isValidReference()) {
            doDereferencing(callback);
        } else {
            invalidReference(callback);
        }
    }

    @Override
    public void bindToValue(final ReferenceBinder<T> binder) {
        if (!isValidReference()) {
            binder.onInvalidated(this);
            return;
        }
        //Have to do this to force it to refire.
        if (contains(binder)) unbindFromValue(binder);
        bind(binder);
    }

    @Override
    public void unbindFromValue(ReferenceBinder<T> binder) {
        if (contains(binder)) removeUnboundBinder(binder);
    }

    @Override
    protected void onInvalidate() {
        super.onInvalidate();
        for (ReferenceBinder<T> binder : getBinders()) {
            binder.onInvalidated(this);
        }
    }

    protected void invalidReference(DereferenceCallback<T> callback) {
        callback.onDereferenced(new DataValue<>(getNullValue()));
    }

    @Nullable
    protected T getNullValue() {
        return null;
    }
}
