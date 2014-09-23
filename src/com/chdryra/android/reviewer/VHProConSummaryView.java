/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.view.View;

import com.chdryra.android.mygenerallibrary.GVData;
import com.chdryra.android.mygenerallibrary.VHDualStringView;
import com.chdryra.android.reviewer.GVProConSummaryList.GVProConSummary;


public class VHProConSummaryView extends VHDualStringView {
	private final static int LAYOUT = R.layout.grid_cell_text_dual;
	private final static int UPPER = R.id.upper_text;
	private final static int LOWER = R.id.lower_text;
	
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
