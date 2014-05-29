package com.chdryra.android.reviewer;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.reviewer.GVUrlList.GVUrl;

class VHUrlView implements ViewHolder {
	public static final int LAYOUT = R.layout.grid_cell_url;
	
	private TextView mTextView;
	
	public VHUrlView(View convertView) {
		init(convertView);
	}
	
	public VHUrlView(Context context) {
		init(View.inflate(context, LAYOUT, null));
	}
	
	private void init(View view) {
		mTextView = (TextView)view.findViewById(R.id.text_view);
	}
	
	@Override
	public void updateView(Object data) {
		mTextView.setText(((GVUrl)data).toShortenedString());
	}
}
