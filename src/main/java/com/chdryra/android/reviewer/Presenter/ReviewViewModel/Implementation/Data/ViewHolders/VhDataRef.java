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
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataConverter;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataReference;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataRef;

/**
 * Created by: Rizwan Choudrey
 * On: 10/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class VhDataRef<GvReference extends GvDataRef<GvReference, ValueType, ValueHolder>,
        ValueType extends HasReviewId,
        ValueHolder extends ViewHolder>
        implements VhDataReference<ValueType> {
    public static final String PLACEHOLDER = "--";

    private ValueHolder mValueHolder;
    private DataConverter<ValueType, ? extends GvData, ?> mConverter;
    private GvDataRef.PlaceHolderFactory<ValueType> mPlaceHolderFactory;
    private ReviewItemReference<ValueType> mReference;
    private ValueType mDataValue;

    public VhDataRef(ValueHolder valueHolder,
                     DataConverter<ValueType, ? extends GvData, ?> converter,
                     GvDataRef.PlaceHolderFactory<ValueType> placeHolderFactory) {
        mValueHolder = valueHolder;
        mConverter = converter;
        mPlaceHolderFactory = placeHolderFactory;
    }

    protected void onReference(GvReference gvReference) {

    }

    public GvDataRef.PlaceHolderFactory<ValueType> getPlaceHolderFactory() {
        return mPlaceHolderFactory;
    }

    protected DataConverter<ValueType, ? extends GvData, ?> getConverter() {
        return mConverter;
    }

    @Override
    public ValueType getDataValue() {
        return mDataValue;
    }

    @Override
    public void inflate(Context context, ViewGroup parent) {
        mValueHolder.inflate(context, parent);
    }

    @Override
    public View getView() {
        return mValueHolder.getView();
    }

    @Override
    public boolean isBoundTo(ReviewItemReference<ValueType> reference) {
        return mReference != null && mReference.getReviewId().equals(reference.getReviewId());
    }

    @Override
    public void unbindFromReference() {
        mReference.unbindFromValue(this);
        showPlaceholder();
    }

    @Override
    public void updateView(ViewHolderData data) {
        GvReference gvReference;
        try {
            gvReference = (GvReference) data;
        } catch (ClassCastException e) {
            e.printStackTrace();
            return;
        }

        if (isBoundTo(gvReference.getReference())) return;

        if (mReference != null) mReference.unbindFromValue(this);
        mReference = gvReference.getReference();

        gvReference.setViewHolder(this);

        onReference(gvReference);

        showPlaceholder();

        mReference.bindToValue(this);
    }

    @Override
    public void onReferenceValue(ValueType value) {
        mDataValue = value;
        GvData converted = mConverter.convert(value);
        updateValueView(converted);
    }

    @Override
    public void onInvalidated(DataReference<ValueType> reference) {
        showPlaceholder();
    }

    private void showPlaceholder() {
        updateValueView(mConverter.convert(mPlaceHolderFactory.newPlaceHolder(PLACEHOLDER)));
    }

    private void updateValueView(GvData converted) {
        mValueHolder.updateView(converted);
    }
}
