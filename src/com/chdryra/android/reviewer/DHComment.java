/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 21 October, 2014
 */

package com.chdryra.android.reviewer;

import android.widget.EditText;

import com.chdryra.android.reviewer.GVCommentList.GVComment;

/**
 * Created by: Rizwan Choudrey
 * On: 21/10/2014
 * Email: rizwan.choudrey@gmail.com
 */
class DHComment extends DialogHolderAddEdit<GVComment> {
    private static final int       LAYOUT    = R.layout.dialog_comment;
    private static final int       COMMENT   = R.id.comment_edit_text;
    private static final GVComment NULL_DATA = new GVComment();

    DHComment(DialogReviewDataAddFragment<GVComment> dialogAdd) {
        super(LAYOUT, new int[]{COMMENT}, dialogAdd, NULL_DATA);
    }

    DHComment(DialogReviewDataEditFragment<GVComment> dialogEdit) {
        super(LAYOUT, new int[]{COMMENT}, dialogEdit);
    }

    @Override
    protected EditText getEditTextForKeyboardAction() {
        return (EditText) getView(COMMENT);
    }

    @Override
    protected String getDialogOnAddTitle(GVComment data) {
        return data.getCommentHeadline();
    }

    @Override
    protected String getDialogDeleteConfirmTitle(GVComment data) {
        return data.getUnSplitComment().getComment();
    }

    @Override
    protected GVComment createGVData() {
        return new GVComment(getEditTextForKeyboardAction().getText().toString().trim());
    }

    @Override
    protected void updateInputs(GVComment fact) {

    }
}
