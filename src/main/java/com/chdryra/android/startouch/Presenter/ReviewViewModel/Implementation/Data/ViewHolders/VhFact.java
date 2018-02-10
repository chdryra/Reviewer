/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders;

import com.chdryra.android.corelibrary.Viewholder.ViewHolderData;
import com.chdryra.android.corelibrary.Viewholder.ViewHolder;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;

/**
 * {@link ViewHolder}: {@link com.chdryra.android.reviewer
 * .VgFactList.VgFact}. Shows fact
 * label top,
 * fact value bottom.
 */
public class VhFact extends VhDualText {
    @Override
    public void updateView(ViewHolderData data) {
        GvFact fact = (GvFact) data;
        super.updateView(fact.getLabel(), fact.getValue());
    }
}
