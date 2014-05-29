package com.chdryra.android.reviewer;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.reviewer.GVLocationList.GVLocation;

class VHLocationView implements ViewHolder {
	public static final int LAYOUT = R.layout.grid_cell_location;
	
	private TextView mLocation;
	
	public VHLocationView(View convertView) {
		init(convertView);
	}
	
	public VHLocationView(Context context) {
		init(View.inflate(context, LAYOUT, null));
	}
	
	private void init(View view) {
		mLocation = (TextView)view.findViewById(R.id.text_view);
	}
	
	@Override
	public void updateView(Object data) {
		GVLocation location = (GVLocation)data;
		mLocation.setText(location.getShortenedName());
	}
}
