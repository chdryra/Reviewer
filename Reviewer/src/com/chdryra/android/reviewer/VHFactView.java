package com.chdryra.android.reviewer;

import com.chdryra.android.mygenerallibrary.GVData;
import com.chdryra.android.reviewer.GVFactList.GVFact;

public class VHFactView extends VHDualStringView {
	private static final int LAYOUT = R.layout.grid_cell_fact;
	private static final int UPPER = R.id.fact_label_text_view;
	private static final int LOWER = R.id.fact_value_text_view;
	
	public VHFactView() {
		super(LAYOUT, UPPER, LOWER);
	}
	
	public VHFactView(int layoutID, int upperTextViewID, int lowerTextViewID) {
		super(layoutID, upperTextViewID, lowerTextViewID);
	}
	
	@Override
	public void updateView(GVData data) {
		GVFact fact = (GVFact)data;
		if(fact != null) {
			mUpper.setText(fact.getLabel());
			mLower.setText(fact.getValue());
		}
	}
}
