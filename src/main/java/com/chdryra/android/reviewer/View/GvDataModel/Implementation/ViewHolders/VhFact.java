/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.View.GvDataModel.Implementation.ViewHolders;

import com.chdryra.android.mygenerallibrary.ViewHolderData;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvFact;

/**
 * {@link com.chdryra.android.mygenerallibrary.ViewHolder}: {@link com.chdryra.android.reviewer
 * .VgFactList.VgFact}. Shows fact
 * label top,
 * fact value bottom.
 */
public class VhFact extends VhDualText {
    //Overridden
    @Override
    public void updateView(ViewHolderData data) {
        GvFact fact = (GvFact) data;
        super.updateView(fact.getLabel(), fact.getValue());
    }
}
