/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.DataDefinitions.References.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ReviewItemReference;
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
    private final Collection<ReferenceBinder<T>> mValueBinders;
    private final Dereferencer<T> mDereferencer;

    public interface Dereferencer<T extends HasReviewId> {
        ReviewId getReviewId();

        void dereference(DereferenceCallback<T> callback);
    }

    public SimpleItemReference(Dereferencer<T> dereferencer) {
        super();
        mDereferencer = dereferencer;
        mValueBinders = new ArrayList<>();
    }

    protected Dereferencer<T> getDereferencer() {
        return mDereferencer;
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
            public void onDereferenced(DataValue<T> value) {
                if (value.hasValue()) {
                    callback.onDereferenced(value);
                } else {
                    invalidate();
                    invalidReference(callback);
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
            public void onDereferenced(DataValue<T> value) {
                if (value.hasValue()) binder.onReferenceValue(value.getData());
            }
        });
    }
}
