/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import com.chdryra.android.mygenerallibrary.GVData;
import com.chdryra.android.mygenerallibrary.GVDualString;
import com.chdryra.android.mygenerallibrary.VHDualStringView;
import com.chdryra.android.reviewer.GVFactList.GVFact;

class VHFactView extends VHDualStringView {
    private static final int LAYOUT = R.layout.grid_cell_text_dual;
    private static final int UPPER  = R.id.upper_text;
    private static final int LOWER  = R.id.lower_text;

    public VHFactView() {
        super(LAYOUT, UPPER, LOWER);
    }

    @Override
    public void updateView(GVData data) {
        GVFact fact = (GVFact) data;
        if (fact != null) super.updateView(new GVDualString(fact.getLabel(), fact.getValue()));
    }
}
