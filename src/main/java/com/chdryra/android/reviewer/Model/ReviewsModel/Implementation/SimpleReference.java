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
public class SimpleReference<T extends HasReviewId> extends DataReferenceBasic<T> implements ReviewItemReference<T> {
    private Collection<ReferenceBinder<T>> mValueBinders;
    private Dereferencer<T> mDereferencer;

    public interface Dereferencer<T extends HasReviewId>{
        ReviewId getReviewId();

        void dereference(DereferenceCallback<T> callback);
    }

    public SimpleReference(Dereferencer<T> dereferencer) {
        super();
        mDereferencer = dereferencer;
        mValueBinders = new ArrayList<>();
    }

    @Override
    public void dereference(DereferenceCallback<T> callback) {
        if(isValidReference()) {
            mDereferencer.dereference(callback);
        }
    }

    @Override
    public void bindToValue(final ReferenceBinder<T> binder) {
        if(isValidReference() && !mValueBinders.contains(binder)) mValueBinders.add(binder);
        dereference(new DereferenceCallback<T>() {
            @Override
            public void onDereferenced(@Nullable T data, CallbackMessage message) {
                if(data != null && !message.isError()) binder.onReferenceValue(data);
            }
        });
    }

    @Override
    public void unbindFromValue(ReferenceBinder<T> binder) {
        if(isValidReference() && mValueBinders.contains(binder)) mValueBinders.remove(binder);
    }

    @Override
    public ReviewId getReviewId() {
        return mDereferencer.getReviewId();
    }

    @Override
    protected void onInvalidate() {
        for(ReferenceBinder<T> binder : mValueBinders) {
            binder.onInvalidated(this);
        }
        mValueBinders.clear();
    }
}
