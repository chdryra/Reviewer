/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import com.chdryra.android.mygenerallibrary.VHStringView;

public class VHTextView extends VHStringView {
	private static final int LAYOUT = R.layout.grid_cell_text;
	private static final int TEXTVIEW = R.id.grid_cell_text_view;

	public VHTextView() {
		super(LAYOUT, TEXTVIEW);
	}
	
	public VHTextView(GVDataStringGetter getter) {
		super(LAYOUT, TEXTVIEW, getter);
	}
	
	public VHTextView(int layoutID, int textViewID) {
		super(layoutID, textViewID);
	}
	
	public VHTextView(int layoutID, int textViewID, GVDataStringGetter getter) {
		super(layoutID, textViewID, getter);
	}
}
