/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 17 October, 2014
 */

package com.chdryra.android.reviewer;

import android.widget.EditText;

import com.chdryra.android.reviewer.GVFactList.GVFact;

/**
 * Created by: Rizwan Choudrey
 * On: 17/10/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class DHFact extends DialogHolderBasic<GVFact> {
    private static final int    LAYOUT    = R.layout.dialog_fact;
    private static final int    LABEL     = R.id.fact_label_edit_text;
    private static final int    VALUE     = R.id.fact_value_edit_text;
    private static final GVFact NULL_DATA = new GVFact(null, null);

    DHFact(DialogAddReviewDataFragment<GVFact> dialogAdd) {
        super(LAYOUT, new int[]{LABEL, VALUE});
        UIDialog.UIDialogManager<GVFact, DialogAddReviewDataFragment<GVFact>> manager = new
                UIDialog.UIDialogManager<GVFact, DialogAddReviewDataFragment<GVFact>>() {
                    @Override
                    public void initialise(GVFact data, DialogAddReviewDataFragment<GVFact>
                            dialog) {
                        dialog.setKeyboardDoActionOnEditText(getEditTextKeyboardDoAction());
                    }

                    @Override
                    public void update(GVFact data, DialogAddReviewDataFragment<GVFact> dialog) {
                        updateInputs(NULL_DATA);
                        dialog.getDialog().setTitle(getDialogTitleOnAdd(data));
                    }

                    @Override
                    public GVFact getGVData() {
                        return createGVData();
                    }
                };

        setDialogUI(new UIDialog<GVFact, DialogAddReviewDataFragment<GVFact>>(dialogAdd, manager));
    }

    DHFact(DialogEditReviewDataFragment<GVFact> dialogEdit) {
        super(LAYOUT, new int[]{LABEL, VALUE});
        UIDialog.UIDialogManager<GVFact, DialogEditReviewDataFragment<GVFact>> manager = new
                UIDialog.UIDialogManager<GVFact, DialogEditReviewDataFragment<GVFact>>() {

                    @Override
                    public void initialise(GVFact data, DialogEditReviewDataFragment<GVFact>
                            dialog) {
                        updateInputs(data);
                        dialog.setKeyboardDoDoneOnEditText(getEditTextKeyboardDoDone());
                        dialog.setDeleteWhatTitle(getDialogDeleteConfirmTitle(data));
                    }

                    @Override
                    public void update(GVFact data, DialogEditReviewDataFragment<GVFact> dialog) {

                    }

                    @Override
                    public GVFact getGVData() {
                        return createGVData();
                    }
                };

        setDialogUI(new UIDialog<GVFact, DialogEditReviewDataFragment<GVFact>>(dialogEdit,
                manager));
    }

    protected EditText getEditTextKeyboardDoAction() {
        return (EditText) getView(VALUE);
    }

    protected EditText getEditTextKeyboardDoDone() {
        return (EditText) getView(VALUE);
    }

    protected String getDialogTitleOnAdd(GVFact data) {
        return "+ " + data.getLabel() + ": " + data.getValue();
    }

    protected String getDialogDeleteConfirmTitle(GVFact data) {
        return data.getLabel() + ": " + data.getValue();
    }

    protected GVFact createGVData() {
        String label = ((EditText) getView(LABEL)).getText().toString().trim();
        String value = ((EditText) getView(VALUE)).getText().toString().trim();
        return new GVFact(label, value);
    }

    protected void updateInputs(GVFact fact) {
        ((EditText) getView(LABEL)).setText(fact.getLabel());
        ((EditText) getView(VALUE)).setText(fact.getValue());
        getView(LABEL).requestFocus();
    }
}
