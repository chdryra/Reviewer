package com.chdryra.android.reviewer;

import com.chdryra.android.mygenerallibrary.DialogActionCancelDoneFragment;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class DialogCommentEditFragment extends DialogActionCancelDoneFragment{

	private ControllerReviewNode mController;
	private EditText mCommentEditText;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mController = Controller.unpack(getArguments());
		setDialogTitle(getResources().getString(R.string.dialog_edit_comment_title));
		setLeftButtonAction(ActionType.CLEAR);
		setDismissDialogOnActionClick(false);
	}

	@Override
	protected void onActionButtonClick() {
		mCommentEditText.setText(null);
	}
	
	@Override
	protected void onDoneButtonClick() {
		String comment = mCommentEditText.getText().toString();
		if(comment != null && comment.length() > 0)
			mController.setComment(comment);
		else if(mController.hasComment())
			mController.deleteComment();
	}
	
	@Override
	protected View createDialogUI() {
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_comment_edit, null);

		mCommentEditText = (EditText)v.findViewById(R.id.comment_edit_text);
		if(mController.hasComment())
			mCommentEditText.setText(mController.getCommentString());

		return v;
	}
}
