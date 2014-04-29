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

		final Button cancelButton = (Button)dialog.findViewById(R.id.button_left);
		final Button addButton = (Button)dialog.findViewById(R.id.button_middle);
		final Button doneButton = (Button)dialog.findViewById(R.id.button_right);

		cancelButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sendResult(Activity.RESULT_CANCELED);
				dialog.dismiss();
			}
		});
		
		addButton.setVisibility(View.GONE);
		
		doneButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sendResult(Activity.RESULT_OK);
				dialog.dismiss();
			}
		});
		
		dialog.setTitle(getResources().getString(R.string.dialog_edit_comment_title));
		dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

		return dialog;
	}

	private void sendResult(int resultCode) {
		if (getTargetFragment() == null || resultCode == Activity.RESULT_CANCELED)
			return;
		
		if(resultCode == Activity.RESULT_OK) {
			String comment = mCommentEditText.getText().toString();
			if(comment == null || comment.length() == 0)
				return;
			
			mController.setComment(comment);
		}
		
		getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, new Intent());	
	}

}
