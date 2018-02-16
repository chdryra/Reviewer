/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.DataDefinitions.References.Implementation;

import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReferenceBinder;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 01/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class DereferencableBasic<T> extends BindableReferenceBasic<T> {
    private final Collection<ReferenceBinder<T>> mValueBinders;

    public DereferencableBasic() {
        super();
        mValueBinders = new ArrayList<>();
    }

    @Override
    protected void removeUnboundBinder(ReferenceBinder<T> binder) {
        mValueBinders.remove(binder);
    }

    @Override
    protected void bind(ReferenceBinder<T> binder) {
        mValueBinders.add(binder);
        fireForBinder(binder);
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
    protected boolean contains(ReferenceBinder<T> binder) {
        return mValueBinders.contains(binder);
    }

    protected void fireForBinder(final ReferenceBinder<T> binder) {
        dereference(new DereferenceCallback<T>() {
            @Override
            public void onDereferenced(DataValue<T> value) {
                if (value.hasValue()) binder.onReferenceValue(value.getData());
            }
        });
    }
}
