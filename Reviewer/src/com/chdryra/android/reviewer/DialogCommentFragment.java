package com.chdryra.android.reviewer;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DialogCommentFragment extends DialogBasicFragment {
	private ControllerReviewNode mController;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {		
		mController = Controller.unpack(getArguments());
		
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_comment, null);
		
		TextView textView= (TextView)v.findViewById(R.id.comment_text_view);
		if(mController.hasComment()) {
 			textView.setText(mController.getCommentString());
		}
	
		return buildDialog(v);
	}

	@Override
	protected String getDeleteConfirmationTitle() {
		return getResources().getString(R.string.activity_title_comment);
	}
	
	@Override
	protected void deleteData() {
		mController.deleteComment();	
	}
	
	@Override
	protected boolean hasData() {
		return mController.hasComment();
	}
}
