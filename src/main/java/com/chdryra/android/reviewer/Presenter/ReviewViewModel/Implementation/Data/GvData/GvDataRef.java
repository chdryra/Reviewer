/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData;

import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolder;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataReference;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.VhDataRef;

/**
 * Created by: Rizwan Choudrey
 * On: 10/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class GvDataRef<R extends HasReviewId, T extends GvData, V extends VhDataRef<R>>
        extends GvDataBasic<T> implements DataReference.InvalidationListener {
    private ReviewItemReference<R> mReference;
    private V mViewHolder;

    protected abstract V newViewHolder();

    public GvDataRef(GvDataType<T> type, ReviewItemReference<R> reference) {
        super(type, new GvReviewId(reference.getReviewId()));
        mReference = reference;
        mReference.registerListener(this);
    }

    public GvDataRef(GvDataType<T> type) {
        super(type);
    }

    public ReviewItemReference<R> getReference() {
        return mReference;
    }

    @Nullable
    public R getDataValue() {
        return mViewHolder != null ? mViewHolder.getDataValue() : null;
    }

    public void unbind() {
        if (mViewHolder != null && mViewHolder.isBoundTo(mReference)) {
            mViewHolder.unbindFromReference();
        }
    }

    @Override
    public ViewHolder getViewHolder() {
        return newViewHolder();
    }

    public void setViewHolder(V viewHolder) {
        mViewHolder = viewHolder;
    }

    @Override
    public String getStringSummary() {
        return getGvReviewId().getStringSummary();
    }

    @Override
    public boolean hasData(DataValidator dataValidator) {
        return mReference.isValidReference();
    }

    @Override
    public boolean isValidForDisplay() {
        return getGvReviewId() != null && mReference.isValidReference();
    }

    @Override
    public void onReferenceInvalidated(DataReference<?> reference) {
        unbind();
    }
}
