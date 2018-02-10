/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders;

import com.chdryra.android.corelibrary.Viewholder.ViewHolder;
import com.chdryra.android.corelibrary.Viewholder.ViewHolderData;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataSize;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvConverters.GvConverterSizes;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSize;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;

/**
 * {@link ViewHolder}: {@link com.chdryra.android.reviewer
 * .VgFactList.VgFact}. Shows fact
 * label top,
 * fact value bottom.
 */
public class VhSize extends VhDualText {
    @Override
    public void updateView(ViewHolderData data) {
        GvSize datum = (GvSize) data;
        int size = datum.getSize();
        String upper = datum.hasSize() ? String.valueOf(size) : datum.getPlaceholder();
        GvDataType<?> type = datum.getType();
        super.updateView(upper, size == 1 ? type.getDatumName() : type.getDataName());
    }

    public static class Reference extends VhDataRef<GvSize.Reference, DataSize, VhSize> {
        public Reference(GvSize.Reference.Factory factory, GvConverterSizes converter) {
            super(new VhSize(), converter, factory);
        }

        @Override
        protected void onReference(GvSize.Reference reference) {
            GvDataType<?> sizedType = reference.getSizedType();
            ((GvConverterSizes) getConverter()).setType(sizedType);
            ((GvSize.Reference.Factory)getPlaceHolderFactory()).setType(sizedType);
        }

    }

}
