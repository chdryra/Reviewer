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
class DHComment extends DialogHolderAddEdit<GVCommentList.GvComment> {
    private static final int                     LAYOUT    = R.layout.dialog_comment;
    private static final int                     COMMENT   = R.id.comment_edit_text;
    private static final GVCommentList.GvComment NULL_DATA = new GVCommentList.GvComment();

    DHComment(DialogReviewDataAddFragment<GVCommentList.GvComment> dialogAdd) {
        super(LAYOUT, new int[]{COMMENT}, dialogAdd, NULL_DATA);
    }

    DHComment(DialogReviewDataEditFragment<GVCommentList.GvComment> dialogEdit) {
        super(LAYOUT, new int[]{COMMENT}, dialogEdit);
    }

    @Override
    protected EditText getEditTextForKeyboardAction() {
        return (EditText) getView(COMMENT);
    }

    @Override
    protected String getDialogOnAddTitle(GVCommentList.GvComment data) {
        return data.getCommentHeadline();
    }

    @Override
    protected String getDialogDeleteConfirmTitle(GVCommentList.GvComment data) {
        return data.getUnSplitComment().getComment();
    }

    @Override
    protected GVCommentList.GvComment createGVData() {
        return new GVCommentList.GvComment(getEditTextForKeyboardAction().getText().toString()
                .trim());
    }

    @Override
    protected void updateWithGVData(GVCommentList.GvComment comment) {
        ((EditText) getView(COMMENT)).setText(comment.getComment());
    }
}
