/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSize;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.VhSizeRef;


public class GvSizeRef extends GvDataRef<DataSize, GvSizeRef, VhSizeRef> {
    public static final GvDataType<GvSizeRef> TYPE =
            new GvDataType<>(GvSizeRef.class, "size");

    private GvDataType<?> mSizedType;

    public GvSizeRef(GvDataType<?> sizedType, ReviewItemReference<DataSize> reference) {
        super(TYPE, reference);
        mSizedType = sizedType;
    }

    public GvDataType<?> getSizedType() {
        return mSizedType;
    }

    @Override
    protected VhSizeRef newViewHolder() {
        return new VhSizeRef();
    }

    @Nullable
    @Override
    public GvSize getParcelable() {
        int size = getDataValue() != null ? getDataValue().getSize() : 0;
        return new GvSize(getGvReviewId(), mSizedType, size);
    }
}
