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
public class DialogFact extends DialogGvData<GvFactList.GvFact> {
    private static final int   LAYOUT = R.layout.dialog_fact;
    private static final int   LABEL  = R.id.fact_label_edit_text;
    private static final int   VALUE  = R.id.fact_value_edit_text;
    private static final int[] VIEWS  = new int[]{LABEL, VALUE};

    public DialogFact(DialogGvDataAddFragment<GvFactList.GvFact> dialogAdd) {
        super(LAYOUT, VIEWS, VALUE, dialogAdd);
    }

    public DialogFact(DialogGvDataEditFragment<GvFactList.GvFact> dialogEdit) {
        super(LAYOUT, VIEWS, VALUE, dialogEdit);
    }

    @Override
    public String getDialogTitleOnAdd(GvFactList.GvFact data) {
        return data.getLabel() + ": " + data.getValue();
    }

    @Override
    public String getDeleteConfirmDialogTitle(GvFactList.GvFact data) {
        return data.getLabel() + ": " + data.getValue();
    }

    @Override
    public GvFactList.GvFact createGvDataFromViews() {
        String label = ((EditText) mViewHolder.getView(LABEL)).getText().toString().trim();
        String value = ((EditText) mViewHolder.getView(VALUE)).getText().toString().trim();
        return new GvFactList.GvFact(label, value);
    }

    @Override
    public void updateViews(GvFactList.GvFact fact) {
        ((EditText) mViewHolder.getView(LABEL)).setText(fact.getLabel());
        ((EditText) mViewHolder.getView(VALUE)).setText(fact.getValue());
        mViewHolder.getView(LABEL).requestFocus();
    }
}
