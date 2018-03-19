/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.DataReference;
import com.chdryra.android.corelibrary.Viewholder.ViewHolder;
import com.chdryra.android.corelibrary.Viewholder.ViewHolderData;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataConverter;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataRef;

/**
 * Created by: Rizwan Choudrey
 * On: 10/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class VhDataRef<GvReference extends GvDataRef<GvReference, ValueType, ValueHolder>,
        ValueType extends HasReviewId,
        ValueHolder extends ViewHolder>
        implements VhDataReference<ValueType> {
    private static final String PLACEHOLDER = "--";

    private final ValueHolder mValueHolder;
    private final DataConverter<ValueType, ? extends GvData, ?> mConverter;
    private final GvDataRef.PlaceHolderFactory<ValueType> mPlaceHolderFactory;
    private ReviewItemReference<ValueType> mReference;
    private ValueType mDataValue;

    public VhDataRef(ValueHolder valueHolder,
                     DataConverter<ValueType, ? extends GvData, ?> converter,
                     GvDataRef.PlaceHolderFactory<ValueType> placeHolderFactory) {
        mValueHolder = valueHolder;
        mConverter = converter;
        mPlaceHolderFactory = placeHolderFactory;
    }

    public GvDataRef.PlaceHolderFactory<ValueType> getPlaceHolderFactory() {
        return mPlaceHolderFactory;
    }

    public ValueHolder getValueHolder() {
        return mValueHolder;
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
        return reference.equals(mReference);
    }

    @Override
    public void unbindFromReference() {
        mReference.unsubscribe(this);
        showPlaceholder();
    }

    @Override
    public void updateView(ViewHolderData data) {
        GvReference gvReference;
        try {
            //TODO make type safe
            gvReference = (GvReference) data;
        } catch (ClassCastException e) {
            e.printStackTrace();
            return;
        }

        if (isBoundTo(gvReference.getReference())) return;

        if (mReference != null) unbindFromReference();
        mReference = gvReference.getReference();

        gvReference.setViewHolder(this);

        onReference(gvReference);

        showPlaceholder();

        mReference.subscribe(this);
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

    DataConverter<ValueType, ? extends GvData, ?> getConverter() {
        return mConverter;
    }

    void onReference(GvReference gvReference) {

    }

    private void showPlaceholder() {
        updateValueView(mConverter.convert(mPlaceHolderFactory.newPlaceHolder(PLACEHOLDER)));
    }

    private void updateValueView(GvData converted) {
        mValueHolder.updateView(converted);
    }
}
