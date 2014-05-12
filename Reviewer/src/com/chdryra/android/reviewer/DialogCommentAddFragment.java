package com.chdryra.android.reviewer;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.actionbarsherlock.app.SherlockDialogFragment;

public class DialogCommentAddFragment extends SherlockDialogFragment{

	private ControllerReviewNode mController;
	private EditText mCommentEditText;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mController = Controller.unpack(getArguments());
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	
		final Dialog dialog = new Dialog(getSherlockActivity());
		dialog.setContentView(R.layout.dialog_comment_edit);

		mCommentEditText = (EditText)dialog.findViewById(R.id.comment_edit_text);
		if(mController.hasComment())
			mCommentEditText.setText(mController.getCommentString());

		final Button clearButton = (Button)dialog.findViewById(R.id.button_left);
		final Button cancelButton = (Button)dialog.findViewById(R.id.button_middle);
		final Button doneButton = (Button)dialog.findViewById(R.id.button_right);

		cancelButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sendResult(Activity.RESULT_CANCELED);
				dialog.dismiss();
			}
		});
		
		clearButton.setText(getResources().getString(R.string.button_clear_text));
		clearButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				clearText();
			}
		});
		
		doneButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sendResult(Activity.RESULT_OK);
			}
		});
		
		dialog.setTitle(getResources().getString(R.string.dialog_edit_comment_title));
		dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

		return dialog;
	}

	private void clearText() {
		mCommentEditText.setText(null);
	}
	
	private void sendResult(int resultCode) {
		if (getTargetFragment() == null)
			return;
		
		if(resultCode == Activity.RESULT_OK) {
			String comment = mCommentEditText.getText().toString();
			if(comment != null && comment.length() > 0)
				mController.setComment(comment);
		}
		
		getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, new Intent());
		getDialog().dismiss();
	}
}
