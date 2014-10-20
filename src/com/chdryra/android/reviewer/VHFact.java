/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import com.chdryra.android.mygenerallibrary.VHDDualString;
import com.chdryra.android.mygenerallibrary.VHDualString;
import com.chdryra.android.mygenerallibrary.ViewHolderData;
import com.chdryra.android.reviewer.GVFactList.GVFact;

/**
 * ViewHolder: GVFact. Shows fact label top, fact value bottom.
 *
 * @see com.chdryra.android.reviewer.GVFactList.GVFact
 */
class VHFact extends VHDualString {
    private static final int LAYOUT = R.layout.grid_cell_text_dual;
    private static final int UPPER  = R.id.upper_text;
    private static final int LOWER  = R.id.lower_text;

    public VHFact() {
        super(LAYOUT, UPPER, LOWER);
    }

    @Override
    public void updateView(ViewHolderData data) {
        GVFact fact = (GVFact) data;
        if (fact != null) super.updateView(new VHDDualString(fact.getLabel(), fact.getValue()));
    }
}
