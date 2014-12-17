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
class DialogHolderComment extends DialogHolderAddEdit<GvCommentList.GvComment> {
    private static final int                     LAYOUT    = R.layout.dialog_comment;
    private static final int                     COMMENT   = R.id.comment_edit_text;
    private static final GvCommentList.GvComment NULL_DATA = new GvCommentList.GvComment();

    DialogHolderComment(DialogGvDataAddFragment<GvCommentList.GvComment> dialogAdd) {
        super(LAYOUT, new int[]{COMMENT}, COMMENT, dialogAdd, NULL_DATA);
    }

    DialogHolderComment(DialogGvDataEditFragment<GvCommentList.GvComment> dialogEdit) {
        super(LAYOUT, new int[]{COMMENT}, COMMENT, dialogEdit);
    }

    @Override
    protected String getDialogTitleOnAdd(GvCommentList.GvComment data) {
        return data.getCommentHeadline();
    }

    @Override
    protected String getDeleteConfirmDialogTitle(GvCommentList.GvComment data) {
        return data.getUnSplitComment().getComment();
    }

    @Override
    protected GvCommentList.GvComment createGvDataFromViews() {
        return new GvCommentList.GvComment(((EditText) getView(COMMENT)).getText().toString()
                .trim());
    }

    @Override
    protected void updateViews(GvCommentList.GvComment comment) {
        ((EditText) getView(COMMENT)).setText(comment.getComment());
    }
}
