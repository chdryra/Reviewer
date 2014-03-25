package com.chdryra.android.reviewer;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DialogCommentFragment extends DialogBasicFragment {
	
	private String mComment;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_comment, null);
		TextView textView= (TextView)v.findViewById(R.id.comment_text_view);
		mComment = getArguments().getString(ReviewOptionsFragment.DIALOG_COMMENT);		
		textView.setText(mComment);
	
		setDeleteConfirmation(getResources().getString(R.string.comment_activity_title));
		
		return buildDialog(v);
	}

}
