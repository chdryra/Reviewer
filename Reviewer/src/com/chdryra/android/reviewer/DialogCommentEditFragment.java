package com.chdryra.android.reviewer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.chdryra.android.mygenerallibrary.DialogDeleteCancelDoneFragment;

public class DialogCommentEditFragment extends DialogDeleteCancelDoneFragment{
	public static final String COMMENT_NEW = "com.chdryra.android.reviewer.comment_new";
	public static final String COMMENT_OLD = "com.chdryra.android.reviewer.comment_old";
	
	private String mOldComment;
	private EditText mCommentEditText;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		mOldComment = getArguments().getString(FragmentReviewComments.COMMENT);
		setDeleteWhatTitle(getResources().getString(R.string.dialog_delete_comment_title));
		setDialogTitle(getResources().getString(R.string.dialog_edit_comment_title));
	}
	
	@Override
	protected View createDialogUI() {
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_comment, null);
		mCommentEditText = (EditText)v.findViewById(R.id.comment_edit_text);
		mCommentEditText.setText(mOldComment);		

		return v;
	}

	@Override
	protected void onDoneButtonClick() {
		Intent i = getNewReturnData();
		i.putExtra(COMMENT_OLD, mOldComment);
		i.putExtra(COMMENT_NEW, mCommentEditText.getText().toString());
	}
	
	@Override
	protected void onDeleteButtonClick() {
		getNewReturnData().putExtra(COMMENT_OLD, mOldComment);
	}

	@Override
	protected boolean hasDataToDelete() {
		return true;
	}	
}
