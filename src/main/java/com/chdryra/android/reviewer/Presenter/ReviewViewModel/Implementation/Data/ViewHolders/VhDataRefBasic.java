/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolder;
import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolderData;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataReference;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataRef;

/**
 * Created by: Rizwan Choudrey
 * On: 10/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class VhDataRefBasic<RefData extends HasReviewId,
        RefView extends VhDataRef<RefData>,
        DataView extends ViewHolder,
        GvRef extends GvDataRef<RefData, GvRef, RefView>> implements VhDataRef<RefData> {
    protected static final String PLACEHOLDER = "--";

    private ReviewItemReference<RefData> mReference;
    private RefData mDataValue;
    private DataView mDataView;

    protected abstract void setViewHolder(GvRef datum);

    protected abstract void updateView(RefData value);

    protected abstract void showPlaceholder();

    public VhDataRefBasic(DataView dataView) {
        mDataView = dataView;
    }

    @Override
    public RefData getDataValue() {
        return mDataValue;
    }

    public DataView getDataView() {
        return mDataView;
    }

    @Override
    public void inflate(Context context, ViewGroup parent) {
        mDataView.inflate(context, parent);
    }

    @Override
    public View getView() {
        return mDataView.getView();
    }

    @Override
    public boolean isBoundTo(ReviewItemReference<RefData> reference) {
        return mReference != null && mReference.getReviewId().equals(reference.getReviewId());
    }

    @Override
    public void unbindFromReference() {
        mReference.unbindFromValue(this);
        showPlaceholder();
    }

    @Override
    public void updateView(ViewHolderData data) {
        GvRef datum;
        try {
            datum = (GvRef) data;
        } catch (ClassCastException e) {
            e.printStackTrace();
            return;
        }

        if (isBoundTo(datum.getReference())) return;

        if (mReference != null) mReference.unbindFromValue(this);
        mReference = datum.getReference();

        setViewHolder(datum);

        onNewDatum(datum);

        showPlaceholder();

        mReference.bindToValue(this);
    }

    protected void onNewDatum(GvRef datum) {

    }

    @Override
    public void onReferenceValue(RefData value) {
        mDataValue = value;
        updateView(value);
    }

    @Override
    public void onInvalidated(DataReference<RefData> reference) {
        showPlaceholder();
    }
}
