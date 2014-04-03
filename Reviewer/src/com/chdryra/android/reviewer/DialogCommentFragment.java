package com.chdryra.android.reviewer;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DialogCommentFragment extends DialogBasicFragment {
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {		
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_comment, null);
		Review review = getArguments().getParcelable(FragmentReviewOptions.REVIEW_OBJECT);
		
		TextView textView= (TextView)v.findViewById(R.id.comment_text_view);
		textView.setText(review.getComment().toString());
	
		return buildDialog(v);
	}

	@Override
	protected String getDeleteWhat() {
		return getResources().getString(R.string.comment_activity_title);
	}
}
