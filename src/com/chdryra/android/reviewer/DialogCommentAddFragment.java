/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.chdryra.android.reviewer.GVCommentList.GVComment;
import com.chdryra.android.reviewer.GVReviewDataList.GVType;

/**
 * Dialog for adding comments.
 */
public class DialogCommentAddFragment extends DialogAddReviewDataFragment<GVComment> {
    private EditText mCommentEditText;

    public DialogCommentAddFragment() {
        super(GVType.COMMENTS);
    }

    @Override
    protected View createDialogUI(ViewGroup parent) {
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_comment, parent, false);
        mCommentEditText = (EditText) v.findViewById(R.id.comment_edit_text);

        setKeyboardDoActionOnEditText(mCommentEditText);

        return v;
    }

    @Override
    protected GVComment createGVData() {
        return new GVComment(mCommentEditText.getText().toString().trim());
    }

    @Override
    protected void resetDialogOnAdd(GVComment comment) {
        mCommentEditText.setText(null);
        getDialog().setTitle("+ " + comment.getComment());
    }
}
