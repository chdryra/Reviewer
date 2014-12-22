/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 17 December, 2014
 */

package com.chdryra.android.reviewer;

import android.widget.EditText;

/**
 * Created by: Rizwan Choudrey
 * On: 17/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class GvDataViewLayoutComment extends GvDataViewLayout<GvCommentList.GvComment> {
    private static final int                     LAYOUT    = R.layout.dialog_comment;
    private static final int                     COMMENT   = R.id.comment_edit_text;
    private static final int[]                   VIEWS     = new int[]{COMMENT};

    GvDataViewLayoutComment(GvDataViewAdd.GvDataAdder<GvCommentList.GvComment> adder) {
        super(LAYOUT, VIEWS, COMMENT, adder);
    }

    GvDataViewLayoutComment(GvDataViewEdit.GvDataEditor<GvCommentList.GvComment> editor) {
        super(LAYOUT, VIEWS, COMMENT, editor);
    }

    @Override
    public String getTitleOnAdd(GvCommentList.GvComment data) {
        return data.getCommentHeadline();
    }

    @Override
    public String getDeleteConfirmDialogTitle(GvCommentList.GvComment data) {
        return data.getUnSplitComment().getComment();
    }

    @Override
    public GvCommentList.GvComment createGvDataFromViews() {
        EditText commentET = (EditText) mViewHolder.getView(COMMENT);
        return new GvCommentList.GvComment(commentET.getText().toString().trim());
    }

    @Override
    public void updateViews(GvCommentList.GvComment comment) {
        ((EditText) mViewHolder.getView(COMMENT)).setText(comment.getComment());
    }
}
