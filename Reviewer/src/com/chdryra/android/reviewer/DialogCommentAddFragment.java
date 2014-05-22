package com.chdryra.android.reviewer;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.chdryra.android.mygenerallibrary.DialogAddCancelDoneFragment;

public class DialogCommentAddFragment extends DialogAddCancelDoneFragment{
	public static final String COMMENT = "com.chdryra.android.review.comment";
	
	private GVComments mComments;	
	private EditText mCommentEditText;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mComments = Controller.unpack(getArguments()).getComments();
		setDialogTitle(getResources().getString(R.string.dialog_add_comments_title));
		setAddOnDone(true);
	}
	
	@Override
	protected View createDialogUI() {
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_comment_add, null);
		mCommentEditText = (EditText)v.findViewById(R.id.comment_edit_text);
		setKeyboardIMEDoAction(mCommentEditText);
		
		return v;
	}

	@Override
	protected void OnAddButtonClick(){
		String comment = mCommentEditText.getText().toString();
		if(comment == null || comment.length() == 0)
			return;
		
		if(mComments.hasComment(comment))
			Toast.makeText(getActivity(), R.string.toast_has_comment, Toast.LENGTH_SHORT).show();
		else {
			mComments.add(comment);
			getNewReturnData().putExtra(COMMENT, comment);
			mCommentEditText.setText(null);
			getDialog().setTitle("Added comment: " + comment);
		}
	}
}
