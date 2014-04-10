package com.chdryra.android.reviewer;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DialogCommentFragment extends DialogBasicFragment {
	private Controller mController = Controller.getInstance();
	private RDId mReviewID;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {		
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_comment, null);
		mReviewID = (RDId)getArguments().getParcelable(FragmentReviewOptions.REVIEW_ID);
		
		TextView textView= (TextView)v.findViewById(R.id.comment_text_view);
		if(mController.hasComment(mReviewID)) {
 			textView.setText(mController.getCommentString(mReviewID));
		}
	
		return buildDialog(v);
	}

	@Override
	protected String getDeleteConfirmationTitle() {
		return getResources().getString(R.string.comment_activity_title);
	}
	
	@Override
	protected void deleteData() {
		mController.deleteComment(mReviewID, true);	
	}
}
