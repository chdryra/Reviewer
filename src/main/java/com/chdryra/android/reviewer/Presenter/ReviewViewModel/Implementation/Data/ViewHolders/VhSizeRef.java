/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSize;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSizeRef;

/**
 * Created by: Rizwan Choudrey
 * On: 07/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class VhSizeRef extends VhDataRefBasic<DataSize, VhSizeRef, VhDataSize, GvSizeRef> {
    private GvDataType<?> mType;

    public VhSizeRef() {
        super(new VhDataSize());
    }

    @Override
    protected void setViewHolder(GvSizeRef datum) {
        datum.setViewHolder(this);
    }

    @Override
    protected void updateView(DataSize value) {
        int size = value.getSize();
        getDataView().updateView(String.valueOf(size), size == 1 ? mType.getDatumName() : mType.getDataName());
    }

    @Override
    protected void onNewDatum(GvSizeRef datum) {
        mType = datum.getSizedType();
    }

    @Override
    protected void showPlaceholder() {
        if(mType == null) {
            getDataView().updateView(PLACEHOLDER, PLACEHOLDER);
        } else {
            getDataView().updateView(PLACEHOLDER, mType.getDataName());
        }
    }
}