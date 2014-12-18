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
public class DialogComment extends DialogGvData<GvCommentList.GvComment> {
    private static final int                     LAYOUT    = R.layout.dialog_comment;
    private static final int                     COMMENT   = R.id.comment_edit_text;
    private static final int[]                   VIEWS     = new int[]{COMMENT};

    private GvDataViewHolderBasic<GvCommentList.GvComment> mViewHolder;

    DialogComment(DialogGvDataAddFragment<GvCommentList.GvComment> dialogAdd) {
        super(LAYOUT, VIEWS, COMMENT, dialogAdd);
    }

    DialogComment(DialogGvDataEditFragment<GvCommentList.GvComment> dialogEdit) {
        super(LAYOUT, VIEWS, COMMENT, dialogEdit);
    }

    @Override
    public String getDialogTitleOnAdd(GvCommentList.GvComment data) {
        return data.getCommentHeadline();
    }

    @Override
    public String getDeleteConfirmDialogTitle(GvCommentList.GvComment data) {
        return data.getUnSplitComment().getComment();
    }

    @Override
    public GvCommentList.GvComment createGvDataFromViews() {
        return new GvCommentList.GvComment(((EditText) mViewHolder.getView(COMMENT)).getText()
                .toString().trim());
    }

    @Override
    public void updateViews(GvCommentList.GvComment comment) {
        ((EditText) mViewHolder.getView(COMMENT)).setText(comment.getComment());
    }
}
