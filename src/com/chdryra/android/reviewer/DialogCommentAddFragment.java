/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import com.chdryra.android.reviewer.GVReviewDataList.GVType;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

public class DialogCommentAddFragment extends DialogAddReviewDataFragment{
	public static final String COMMENT = "com.chdryra.android.review.comment";
	
	private GVCommentList mComments;	
	private EditText mCommentEditText;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mComments = (GVCommentList) setAndInitData(GVType.COMMENTS);
		setDialogTitle(getResources().getString(R.string.dialog_add_comments_title));
	}
	
	@Override
	protected View createDialogUI(ViewGroup parent) {
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_comment, parent, false);
		mCommentEditText = (EditText)v.findViewById(R.id.comment_edit_text);
		setKeyboardIMEDoAction(mCommentEditText);
		
		return v;
	}

	@Override
	protected void OnAddButtonClick(){
		String comment = mCommentEditText.getText().toString();
		if(comment == null || comment.length() == 0)
			return;
		
		if(mComments.contains(comment))
			Toast.makeText(getActivity(), R.string.toast_has_comment, Toast.LENGTH_SHORT).show();
		else {
			mComments.add(comment);
			getNewReturnData().putExtra(COMMENT, comment);
			mCommentEditText.setText(null);
			getDialog().setTitle("+ " + comment);
		}
	}
}
