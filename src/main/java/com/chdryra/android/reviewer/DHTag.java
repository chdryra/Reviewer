/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 21 October, 2014
 */

package com.chdryra.android.reviewer;

import android.widget.EditText;

/**
 * Created by: Rizwan Choudrey
 * On: 21/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * {@link DialogHolderAddEdit}: tags
 */
class DHTag extends DialogHolderAddEdit<GVTagList.GVTag> {
    private static final int             LAYOUT    = R.layout.dialog_tag;
    private static final int             TAG       = R.id.tag_edit_text;
    private static final GVTagList.GVTag NULL_DATA = new GVTagList.GVTag();

    DHTag(DialogReviewDataAddFragment<GVTagList.GVTag> dialogAdd) {
        super(LAYOUT, new int[]{TAG}, dialogAdd, NULL_DATA);
    }

    DHTag(DialogReviewDataEditFragment<GVTagList.GVTag> dialogEdit) {
        super(LAYOUT, new int[]{TAG}, dialogEdit);
    }

    @Override
    protected EditText getEditTextForKeyboardAction() {
        return (EditText) getView(TAG);
    }

    @Override
    protected String getDialogOnAddTitle(GVTagList.GVTag data) {
        return "#" + data.get();
    }

    @Override
    protected String getDialogDeleteConfirmTitle(GVTagList.GVTag data) {
        return data.get();
    }

    @Override
    protected GVTagList.GVTag createGVData() {
        return new GVTagList.GVTag(((EditText) getView(TAG)).getText().toString().trim());
    }

    @Override
    protected void updateWithGVData(GVTagList.GVTag tag) {
        ((EditText) getView(TAG)).setText(tag.get());
    }
}
