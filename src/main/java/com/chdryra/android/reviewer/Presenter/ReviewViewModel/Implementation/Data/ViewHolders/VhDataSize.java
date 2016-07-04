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
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataSize;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;

/**
 * {@link ViewHolder}: {@link com.chdryra.android.reviewer
 * .VgFactList.VgFact}. Shows fact
 * label top,
 * fact value bottom.
 */
public class VhDataSize extends VhDualText {
    @Override
    public void updateView(ViewHolderData data) {
        GvDataSize datum = (GvDataSize) data;
        int size = datum.getSize();
        String upper = datum.hasSize() ? String.valueOf(size) : datum.getPlaceholder();
        GvDataType<?> type = datum.getType();
        super.updateView(upper, size == 1 ? type.getDatumName() : type.getDataName());
    }
}
