/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 17 October, 2014
 */

package com.chdryra.android.reviewer;

import android.widget.EditText;

import com.chdryra.android.reviewer.GvFactList.GvFact;

/**
 * Created by: Rizwan Choudrey
 * On: 17/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * {@link DialogHolderAddEdit}: facts
 */
class DHFact extends DialogHolderAddEdit<GvFactList.GvFact> {
    private static final int               LAYOUT    = R.layout.dialog_fact;
    private static final int               LABEL     = R.id.fact_label_edit_text;
    private static final int               VALUE     = R.id.fact_value_edit_text;
    private static final GvFactList.GvFact NULL_DATA = new GvFactList.GvFact(null, null);

    DHFact(DialogGvDataAddFragment<GvFact> dialogAdd) {
        super(LAYOUT, new int[]{LABEL, VALUE}, dialogAdd, NULL_DATA);
    }

    DHFact(DialogGvDataEditFragment<GvFact> dialogEdit) {
        super(LAYOUT, new int[]{LABEL, VALUE}, dialogEdit);
    }

    @Override
    protected EditText getEditTextForKeyboardAction() {
        return (EditText) getView(VALUE);
    }

    @Override
    protected String getDialogOnAddTitle(GvFactList.GvFact data) {
        return data.getLabel() + ": " + data.getValue();
    }

    @Override
    protected String getDialogDeleteConfirmTitle(GvFactList.GvFact data) {
        return data.getLabel() + ": " + data.getValue();
    }

    @Override
    protected GvFactList.GvFact createGvData() {
        String label = ((EditText) getView(LABEL)).getText().toString().trim();
        String value = ((EditText) getView(VALUE)).getText().toString().trim();
        return new GvFact(label, value);
    }

    @Override
    protected void updateWithGvData(GvFact fact) {
        ((EditText) getView(LABEL)).setText(fact.getLabel());
        ((EditText) getView(VALUE)).setText(fact.getValue());
        getView(LABEL).requestFocus();
    }
}
