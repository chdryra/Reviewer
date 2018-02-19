/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.corelibrary.Viewholder.ViewHolder;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataConverter;
import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.DataReference;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .VhDataRef;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .VhDataReference;

/**
 * Created by: Rizwan Choudrey
 * On: 10/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class GvDataRef<Reference extends GvDataRef<Reference, ValueType, ValueHolder>,
        ValueType extends HasReviewId, ValueHolder extends ViewHolder>
        extends GvDataBasic<Reference> implements DataReference.InvalidationListener {

    private final ReviewItemReference<ValueType> mReference;
    private final PlaceHolderFactory<ValueType> mFactory;
    private final DataConverter<ValueType, ? extends GvDataParcelable, ?> mConverter;
    private final Class<ValueHolder> mValueHolderClass;
    private VhDataReference<ValueType> mViewHolder;

    public interface PlaceHolderFactory<ValueType extends HasReviewId> {
        ValueType newPlaceHolder(String placeHolder);
    }

    public GvDataRef(GvDataType<Reference> type,
                     ReviewItemReference<ValueType> reference,
                     DataConverter<ValueType, ? extends GvDataParcelable, ?> converter,
                     Class<ValueHolder> valueHolderClass,
                     PlaceHolderFactory<ValueType> factory) {
        super(type, new GvReviewId(reference.getReviewId()));
        mReference = reference;
        mConverter = converter;
        mFactory = factory;
        mValueHolderClass = valueHolderClass;
        mReference.registerListener(this);
    }

    @NonNull
    protected static <T extends GvData> GvDataType<T> getType(Class<T> refClass,
                                                              GvDataType<?> type) {
        return new GvDataType<>(refClass, type.getDatumName(), type.getDataName());
    }

    public ReviewItemReference<ValueType> getReference() {
        return mReference;
    }

    DataConverter<ValueType, ? extends GvDataParcelable, ?> getConverter() {
        return mConverter;
    }

    @Nullable
    public VhDataReference<ValueType> getReferenceViewHolder() {
        return mViewHolder;
    }

    @Nullable
    public ValueType getDataValue() {
        return mViewHolder != null ? mViewHolder.getDataValue() : null;
    }

    public void unbind() {
        if (mViewHolder != null && mViewHolder.isBoundTo(mReference)) {
            mViewHolder.unbindFromReference();
        }
    }

    PlaceHolderFactory<ValueType> getPlaceholderFactory() {
        return mFactory;
    }

    VhDataReference<ValueType> newViewHolder() {
        try {
            ValueHolder valueHolder = mValueHolderClass.newInstance();
            return new VhDataRef<Reference, ValueType, ValueHolder>(valueHolder, mConverter, mFactory);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Nullable
    @Override
    public GvDataParcelable getParcelable() {
        ValueType value = getDataValue() != null ? getDataValue() : mFactory.newPlaceHolder("");
        return mConverter.convert(value);
    }

    @Override
    public ViewHolder getViewHolder() {
        return newViewHolder();
    }

    public void setViewHolder(VhDataReference<ValueType> viewHolder) {
        mViewHolder = viewHolder;
    }

    @Override
    public String toString() {
        return getDataValue() != null ? getDataValue().toString() : getGvReviewId().toString() + ": " + getGvDataType().getDataName();
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
    public void onInvalidated(DataReference<?> reference) {
        unbind();
    }
}
