/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 17 June, 2015
 */

package com.chdryra.android.reviewer.View.Dialogs;

import android.widget.TextView;

import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.GvDataModel.GvFactList;

/**
 * Created by: Rizwan Choudrey
 * On: 18/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewFact extends DialogLayout<GvFactList.GvFact> {
    public static final int   LAYOUT = R.layout.dialog_fact_view;
    public static final int   LABEL  = R.id.fact_label_text_view;
    public static final int   VALUE  = R.id.fact_value_text_view;
    public static final int[] VIEWS  = new int[]{LABEL, VALUE};

    public ViewFact() {
        super(LAYOUT, VIEWS);
    }

    @Override
    public void updateLayout(GvFactList.GvFact fact) {
        ((TextView) getView(LABEL)).setText(fact.getLabel());
        ((TextView) getView(VALUE)).setText(fact.getValue());
    }
}
