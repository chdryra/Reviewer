package com.chdryra.android.reviewer;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DialogCommentFragment extends DialogBasicFragment {
	private ReviewUser mReview;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {		
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_comment, null);
		mReview = (ReviewUser)UtilReviewPackager.get(getArguments());
		TextView textView= (TextView)v.findViewById(R.id.comment_text_view);
		if(mReview.hasComment()) {
			ReviewNode node = mReview.getReviewNode();
			textView.setText(mReview.getComment().toString());
		}
	
		return buildDialog(v);
	}

	@Override
	protected String getDeleteConfirmationTitle() {
		return getResources().getString(R.string.comment_activity_title);
	}
	
	@Override
	protected void deleteData() {
		ReviewNode reviewTree = mReview.getReviewNode();
	}
}
