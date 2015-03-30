/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 17 December, 2014
 */

package com.chdryra.android.reviewer.View;

import android.widget.EditText;

import com.chdryra.android.reviewer.R;

/**
 * Created by: Rizwan Choudrey
 * On: 17/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class LayoutComment extends GvDataEditLayout<GvCommentList.GvComment> {
    public static final int   LAYOUT  = R.layout.dialog_comment;
    public static final int   COMMENT = R.id.comment_edit_text;
    public static final int[] VIEWS   = new int[]{COMMENT};

    private GvCommentList.GvComment mCurrent;

    public LayoutComment(GvDataAdder adder) {
        super(GvCommentList.GvComment.class, LAYOUT, VIEWS, COMMENT, adder);
    }

    public LayoutComment(GvDataEditor editor) {
        super(GvCommentList.GvComment.class, LAYOUT, VIEWS, COMMENT, editor);
    }

    @Override
    public GvCommentList.GvComment createGvData() {
        EditText commentET = (EditText) getView(COMMENT);
        boolean isHeadline = mCurrent != null && mCurrent.isHeadline();
        mCurrent = new GvCommentList.GvComment(commentET.getText().toString().trim(), isHeadline);

        return mCurrent;
    }

    @Override
    public void updateLayout(GvCommentList.GvComment comment) {
        ((EditText) getView(COMMENT)).setText(comment.getComment());
        mCurrent = comment;
    }
}
