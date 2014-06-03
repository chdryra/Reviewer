package com.chdryra.android.reviewer;

import android.view.View;

import com.chdryra.android.mygenerallibrary.GVData;
import com.chdryra.android.reviewer.GVProConSummaryList.GVProConSummary;


public class VHProConSummaryView extends VHDualStringView {
	private final static int LAYOUT = R.layout.grid_cell_procon_summary;
	private final static int UPPER = R.id.pros_text_view;
	private final static int LOWER = R.id.cons_text_view;
	
	public VHProConSummaryView() {
		super(LAYOUT, UPPER, LOWER);
	}
	
	@Override
	public View updateView(GVData data) {
		GVProConSummary summary = (GVProConSummary)data;
		mUpper.setText(summary.getUpper());
		mLower.setText(summary.getLower());
		
		return mInflated;
	}
}
