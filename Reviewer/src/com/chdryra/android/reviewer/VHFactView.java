package com.chdryra.android.reviewer;

import android.view.View;

import com.chdryra.android.mygenerallibrary.GVData;
import com.chdryra.android.mygenerallibrary.VHDualStringView;
import com.chdryra.android.reviewer.GVFactList.GVFact;

public class VHFactView extends VHDualStringView {
	private static final int LAYOUT = R.layout.grid_cell_text_dual;
	private static final int UPPER = R.id.upper_text;
	private static final int LOWER = R.id.lower_text;
	
	public VHFactView() {
		super(LAYOUT, UPPER, LOWER);
	}
	
	@Override
	public View updateView(GVData data) {
		GVFact fact = (GVFact)data;
		if(fact != null) {
			mUpper.setText(fact.getLabel());
			mLower.setText(fact.getValue());
		}
		
		return mInflated;
	}
}
