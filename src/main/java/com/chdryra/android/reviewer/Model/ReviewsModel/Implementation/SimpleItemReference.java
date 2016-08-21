/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReferenceBinder;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 01/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class SimpleItemReference<T extends HasReviewId> extends BindableReferenceBasic<T> implements
        ReviewItemReference<T> {
    private Collection<ReferenceBinder<T>> mValueBinders;
    private Dereferencer<T> mDereferencer;

    public interface Dereferencer<T extends HasReviewId> {
        ReviewId getReviewId();

        void dereference(DereferenceCallback<T> callback);
    }

    public SimpleItemReference(Dereferencer<T> dereferencer) {
        super();
        mDereferencer = dereferencer;
        mValueBinders = new ArrayList<>();
    }

    @Override
    protected void removeBinder(ReferenceBinder<T> binder) {
        mValueBinders.remove(binder);
    }

    @Override
    protected void bind(ReferenceBinder<T> binder) {
        mValueBinders.add(binder);
        fireForBinder(binder);
    }

    @Override
    public ReviewId getReviewId() {
        return mDereferencer.getReviewId();
    }

    @Override
    protected Collection<ReferenceBinder<T>> getBinders() {
        return mValueBinders;
    }

    @Override
    protected void onInvalidate() {
        super.onInvalidate();
        mValueBinders.clear();
    }

    @Override
    protected void doDereferencing(final DereferenceCallback<T> callback) {
        mDereferencer.dereference(new DereferenceCallback<T>() {
            @Override
            public void onDereferenced(@Nullable T data, CallbackMessage message) {
                if (data == null) {
                    invalidate();
                    invalidReference(callback);
                } else {
                    callback.onDereferenced(data, message);
                }
            }
        });
    }

    @Override
    protected boolean contains(ReferenceBinder<T> binder) {
        return mValueBinders.contains(binder);
    }

    private void fireForBinder(final ReferenceBinder<T> binder) {
        dereference(new DereferenceCallback<T>() {
            @Override
            public void onDereferenced(@Nullable T data, CallbackMessage message) {
                if (data != null && !message.isError()) binder.onReferenceValue(data);
            }
        });
    }
}
