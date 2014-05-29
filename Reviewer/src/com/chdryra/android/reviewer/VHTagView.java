package com.chdryra.android.reviewer;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chdryra.android.mygenerallibrary.GVStringList.GVString;
import com.chdryra.android.mygenerallibrary.ViewHolder;

class VHTagView implements ViewHolder {
	public static final int LAYOUT = R.layout.grid_cell_tag;
	
	private TextView mTextView;
	
	public VHTagView(View convertView) {
		mTextView = (TextView)convertView.findViewById(R.id.text_view);
	}
	
	public VHTagView(Context context) {
		init(View.inflate(context, LAYOUT, null));
	}
	
	private void init(View view) {
		mTextView = (TextView)view.findViewById(R.id.text_view);
	}
	
	@Override
	public void updateView(Object data) {
		mTextView.setText(((GVString)data).toString());
	}
}