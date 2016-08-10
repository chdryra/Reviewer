/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTagRef;

/**
 * Created by: Rizwan Choudrey
 * On: 07/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class VhTagRef extends VhDataRefBasic<DataTag, VhTagRef, VhTag, GvTagRef>{
    public VhTagRef() {
        super(new VhTag());
    }

    @Override
    protected void setViewHolder(GvTagRef datum) {
        datum.setViewHolder(this);
    }

    @Override
    protected void updateView(DataTag value) {
        getDataView().updateView("#" + value.getTag());
    }

    @Override
    protected void showPlaceholder() {
        getDataView().updateView(PLACEHOLDER);
    }
}