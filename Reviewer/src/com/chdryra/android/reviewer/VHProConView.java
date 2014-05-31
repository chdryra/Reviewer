package com.chdryra.android.reviewer;

import com.chdryra.android.mygenerallibrary.VHStringView;

class VHProConView extends VHStringView {
	private static final int LAYOUT = R.layout.grid_cell_procon;
	private static final int TEXTVIEW = R.id.text_view;
	
	public VHProConView() {
		super(LAYOUT, TEXTVIEW);
	}
}