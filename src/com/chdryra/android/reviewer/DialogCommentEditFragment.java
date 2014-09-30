/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.chdryra.android.mygenerallibrary.DialogCancelDeleteDoneFragment;

public class DialogCommentEditFragment extends DialogCancelDeleteDoneFragment {
    public static final String COMMENT_NEW = "com.chdryra.android.reviewer.comment_new";
    public static final String COMMENT_OLD = "com.chdryra.android.reviewer.comment_old";

    private String   mOldComment;
    private EditText mCommentEditText;

    @Override
    protected View createDialogUI(ViewGroup parent) {
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_comment, parent, false);
        mCommentEditText = (EditText) v.findViewById(R.id.comment_edit_text);
        mCommentEditText.setText(mOldComment);

        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOldComment = getArguments().getString(FragmentReviewComments.COMMENT);
        setDeleteWhatTitle(getResources().getString(R.string.dialog_delete_comment_title));
        setDialogTitle(getResources().getString(R.string.dialog_edit_comment_title));
    }

    @Override
    protected void onDeleteButtonClick() {
        getNewReturnData().putExtra(COMMENT_OLD, mOldComment);
    }

    @Override
    protected boolean hasDataToDelete() {
        return true;
    }

    @Override
    protected void onDoneButtonClick() {
        Intent i = getNewReturnData();
        i.putExtra(COMMENT_OLD, mOldComment);
        i.putExtra(COMMENT_NEW, mCommentEditText.getText().toString());
    }
}
