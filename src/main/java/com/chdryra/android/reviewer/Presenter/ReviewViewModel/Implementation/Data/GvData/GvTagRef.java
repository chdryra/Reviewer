/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataReference;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters
        .GvConverterDataTags;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.VhTagRef;


public class GvTagRef extends GvDataRef<DataTag, GvTagRef, VhTagRef> implements DataReference.InvalidationListener {
    public static final GvDataType<GvTagRef> TYPE =
            new GvDataType<>(GvTagRef.class, GvTag.TYPE.getDatumName());

    private GvConverterDataTags mConverter;

    public GvTagRef(ReviewItemReference<DataTag> reference, GvConverterDataTags converter) {
        super(GvTagRef.TYPE, reference);
        mConverter = converter;
    }

    @Override
    protected VhTagRef newViewHolder() {
        return new VhTagRef();
    }

    @Nullable
    @Override
    public GvDataParcelable getParcelable() {
        DataTag value = getDataValue() != null ? getDataValue() : new DatumTag(getReviewId());
        return mConverter.convert(value);
    }
}
