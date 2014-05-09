package com.chdryra.android.reviewer;

import java.util.ArrayList;

import android.view.View;
import android.widget.TextView;

public class GVDStrings implements GridViewData {
	private ArrayList<String> mData;
	
	public GVDStrings(ArrayList<String> data) {
		mData = data;
	}
	
	@Override
	public int size() {
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		return mData.get(position);
	}

	@Override
	public ViewHolder getViewHolder(View convertView) {
		TextView textView = (TextView)convertView.findViewById(R.id.text_view);
		return new VHTextView(textView);
	}

	class VHTextView implements ViewHolder {
		private TextView mTextView;
		
		public VHTextView(TextView textView) {
			mTextView = textView;
		}
		
		@Override
		public void updateView(Object data, int textColour) {
			mTextView.setText((String)data);
			mTextView.setTextColor(textColour);
		}
		
	}
}
