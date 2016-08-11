/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders;

import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolder;
import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolderData;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSize;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters
        .GvConverterBasic;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSize;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSizeList;

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
        public Reference(GvSize.Reference.Factory factory) {
            super(new VhSize(), new GvSizeConverter(), factory);
        }

        @Override
        protected void onReference(GvSize.Reference reference) {
            GvDataType<?> sizedType = reference.getSizedType();
            ((GvSizeConverter) getConverter()).setType(sizedType);
            ((GvSize.Reference.Factory)getPlaceHolderFactory()).setType(sizedType);
        }

        private static class GvSizeConverter extends GvConverterBasic<DataSize, GvSize, GvSizeList> {
            private GvDataType<?> mType;

            public GvSizeConverter() {
                super(GvSizeList.class);
            }

            public void setType(GvDataType<?> type) {
                mType = type;
            }

            @Override
            public GvSize convert(DataSize datum, ReviewId reviewId) {
                return new GvSize(newId(datum.getReviewId()), mType, datum.getSize());
            }
        }
    }
}
