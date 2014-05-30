package com.chdryra.android.reviewer;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.reviewer.GVCommentList.GVComment;

class VHCommentView implements ViewHolder {
	public static final int LAYOUT = R.layout.grid_cell_comment;
	private TextView mComment;
	
	public VHCommentView(View convertView) {
		init(convertView);
	}
	
	public VHCommentView(Context context) {
		init(View.inflate(context, LAYOUT, null));
	}
	
	private void init(View view) {
		mComment = (TextView)view.findViewById(R.id.text_view);
	}
	
	@Override
	public void updateView(Object data) {
		GVComment comment = (GVComment)data;
		mComment.setText(comment.getCommentHeadline());
	}
}
