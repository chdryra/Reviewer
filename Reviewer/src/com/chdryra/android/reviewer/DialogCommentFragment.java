package com.chdryra.android.reviewer;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DialogCommentFragment extends DialogDeleteCancelDoneFragment {
	private ControllerReviewNode mController;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mController = Controller.unpack(getArguments());

		setDialogTitle(getResources().getString(R.string.dialog_edit_comment_title));
		setDeleteConfirmation(true);
		setDeleteWhatTitle(getResources().getString(R.string.activity_title_comment));
	}
	
	@Override
	protected void onDeleteButtonClick() {
		mController.deleteComment();	
	}
	
	@Override
	protected View createDialogUI() {
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_comment, null);
		
		TextView textView= (TextView)v.findViewById(R.id.comment_text_view);
		if(mController.hasComment()) {
 			textView.setText(mController.getCommentString());
		}

		return v;
	}

	@Override
	protected boolean hasDataToDelete() {
		return mController.hasComment();
	}
}
