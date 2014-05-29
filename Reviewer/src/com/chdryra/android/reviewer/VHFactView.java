package com.chdryra.android.reviewer;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.reviewer.GVFactList.GVFact;

class VHFactView implements ViewHolder {
	public static final int LAYOUT = R.layout.grid_cell_fact;
	
	private TextView mLabel;
	private TextView mValue;
	
	public VHFactView(View convertView) {
		init(convertView);
	}
	
	public VHFactView(Context context) {
		init(View.inflate(context, LAYOUT, null));
	}
	
	private void init(View view) {
		mLabel = (TextView)view.findViewById(R.id.fact_label_text_view);
		mValue = (TextView)view.findViewById(R.id.fact_value_text_view);	
	}
	
	@Override
	public void updateView(Object data) {
		GVFact fact = (GVFact)data;
		mLabel.setText(fact.getLabel());
		mValue.setText(fact.getValue());
	}
}
