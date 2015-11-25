/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 17 June, 2015
 */

package com.chdryra.android.reviewer.View.Implementation.Dialogs.Layouts.Implementation;

import android.widget.TextView;

import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvFact;

/**
 * Created by: Rizwan Choudrey
 * On: 18/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewLayoutFact extends DialogLayoutBasic<GvFact> {
    public static final int LAYOUT = R.layout.dialog_fact_view;
    public static final int LABEL = R.id.fact_label_text_view;
    public static final int VALUE = R.id.fact_value_text_view;
    public static final int[] VIEWS = new int[]{LABEL, VALUE};

    //Constructors
    public ViewLayoutFact() {
        super(LAYOUT, VIEWS);
    }

    //Overridden
    @Override
    public void updateLayout(GvFact fact) {
        ((TextView) getView(LABEL)).setText(fact.getLabel());
        ((TextView) getView(VALUE)).setText(fact.getValue());
    }
}
