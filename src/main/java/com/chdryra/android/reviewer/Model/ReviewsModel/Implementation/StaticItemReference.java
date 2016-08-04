/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReferenceBinder;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 01/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class StaticItemReference<T extends HasReviewId> extends DataReferenceBasic<T> implements ReviewItemReference<T> {
    private ReviewId mId;
    private T mValue;
    private Collection<ReferenceBinder<T>> mValueBinders;

    public StaticItemReference(ReviewId id, T value) {
        super();
        mId = id;
        mValue = value;
        mValueBinders = new ArrayList<>();
    }

    protected T getData() {
        return mValue;
    }

    @Override
    public void dereference(DereferenceCallback<T> callback) {
        if(isValidReference()) callback.onDereferenced(mValue, CallbackMessage.ok());
    }

    @Override
    public void bindToValue(ReferenceBinder<T> binder) {
        if(isValidReference() && !mValueBinders.contains(binder)) mValueBinders.add(binder);
        binder.onReferenceValue(mValue);
    }

    @Override
    public void unbindFromValue(ReferenceBinder<T> binder) {
        if(isValidReference() && mValueBinders.contains(binder)) mValueBinders.remove(binder);
    }

    @Override
    public ReviewId getReviewId() {
        return mId;
    }

    @Override
    protected void onInvalidate() {
        for(ReferenceBinder<T> binder : mValueBinders) {
            binder.onInvalidated(this);
        }
        mValueBinders.clear();
    }
}
