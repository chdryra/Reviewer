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
 * {@link DialogHolderAddEdit}: comments
 */
class DHComment extends DialogHolderAddEdit<VgCommentList.GvComment> {
    private static final int                     LAYOUT    = R.layout.dialog_comment;
    private static final int                     COMMENT   = R.id.comment_edit_text;
    private static final VgCommentList.GvComment NULL_DATA = new VgCommentList.GvComment();

    DHComment(DialogReviewDataAddFragment<VgCommentList.GvComment> dialogAdd) {
        super(LAYOUT, new int[]{COMMENT}, dialogAdd, NULL_DATA);
    }

    DHComment(DialogReviewDataEditFragment<VgCommentList.GvComment> dialogEdit) {
        super(LAYOUT, new int[]{COMMENT}, dialogEdit);
    }

    @Override
    protected EditText getEditTextForKeyboardAction() {
        return (EditText) getView(COMMENT);
    }

    @Override
    protected String getDialogOnAddTitle(VgCommentList.GvComment data) {
        return data.getCommentHeadline();
    }

    @Override
    protected String getDialogDeleteConfirmTitle(VgCommentList.GvComment data) {
        return data.getUnSplitComment().getComment();
    }

    @Override
    protected VgCommentList.GvComment createGVData() {
        return new VgCommentList.GvComment(getEditTextForKeyboardAction().getText().toString()
                .trim());
    }

    @Override
    protected void updateWithGVData(VgCommentList.GvComment comment) {
        ((EditText) getView(COMMENT)).setText(comment.getComment());
    }
}
