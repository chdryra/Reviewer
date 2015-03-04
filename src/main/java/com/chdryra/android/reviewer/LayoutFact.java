/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 18 December, 2014
 */

package com.chdryra.android.reviewer;

import android.widget.EditText;

/**
 * Created by: Rizwan Choudrey
 * On: 18/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class LayoutFact extends GvDataEditLayout<GvFactList.GvFact> {
    public static final int   LAYOUT = R.layout.dialog_fact;
    public static final int   LABEL  = R.id.fact_label_edit_text;
    public static final int   VALUE  = R.id.fact_value_edit_text;
    public static final int[] VIEWS  = new int[]{LABEL, VALUE};

    public LayoutFact(GvDataAdder adder) {
        super(GvFactList.GvFact.class, LAYOUT, VIEWS, VALUE, adder);
    }

    public LayoutFact(GvDataEditor editor) {
        super(GvFactList.GvFact.class, LAYOUT, VIEWS, VALUE, editor);
    }

    @Override
    public GvFactList.GvFact createGvData() {
        String label = ((EditText) getView(LABEL)).getText().toString().trim();
        String value = ((EditText) getView(VALUE)).getText().toString().trim();
        return new GvFactList.GvFact(label, value);
    }

    @Override
    public void updateLayout(GvFactList.GvFact fact) {
        ((EditText) getView(LABEL)).setText(fact.getLabel());
        ((EditText) getView(VALUE)).setText(fact.getValue());
        getView(LABEL).requestFocus();
    }
}
