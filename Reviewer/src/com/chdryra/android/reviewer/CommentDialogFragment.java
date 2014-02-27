package com.chdryra.android.reviewer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CommentDialogFragment extends BasicDialogFragment {

	private String mComment;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_comment, null);
		TextView textView= (TextView)v.findViewById(R.id.comment_text_view);
		mComment = getArguments().getString(ReviewOptionsFragment.REVIEW_COMMENT);		
		textView.setText(mComment);
		
		return buildDialog(v);
	}

}
