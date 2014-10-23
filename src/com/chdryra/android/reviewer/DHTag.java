/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 21 October, 2014
 */

package com.chdryra.android.reviewer;

import android.widget.EditText;

import com.chdryra.android.reviewer.GVTagList.GVTag;

/**
 * Created by: Rizwan Choudrey
 * On: 21/10/2014
 * Email: rizwan.choudrey@gmail.com
 */
class DHTag extends DialogHolderAddEdit<GVTag> {
    private static final int   LAYOUT    = R.layout.dialog_tag;
    private static final int   TAG       = R.id.tag_edit_text;
    private static final GVTag NULL_DATA = new GVTag();

    DHTag(DialogReviewDataAddFragment<GVTag> dialogAdd) {
        super(LAYOUT, new int[]{TAG}, dialogAdd, NULL_DATA);
    }

    DHTag(DialogReviewDataEditFragment<GVTag> dialogEdit) {
        super(LAYOUT, new int[]{TAG}, dialogEdit);
    }

    @Override
    protected EditText getEditTextForKeyboardAction() {
        return (EditText) getView(TAG);
    }

    @Override
    protected String getDialogOnAddTitle(GVTag data) {
        return "#" + data.get();
    }

    @Override
    protected String getDialogDeleteConfirmTitle(GVTag data) {
        return data.get();
    }

    @Override
    protected GVTag createGVData() {
        return new GVTag(((EditText) getView(TAG)).getText().toString().trim());
    }

    @Override
    protected void updateInputs(GVTag tag) {
        ((EditText) getView(TAG)).setText(tag.get());
    }
}
