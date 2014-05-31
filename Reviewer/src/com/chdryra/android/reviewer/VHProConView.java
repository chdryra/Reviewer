package com.chdryra.android.reviewer;

import com.chdryra.android.mygenerallibrary.VHStringView;

class VHProConView extends VHStringView {
	private static final int LAYOUT_PRO = R.layout.grid_cell_pro;
	private static final int LAYOUT_CON = R.layout.grid_cell_con;
	private static final int TEXTVIEW = R.id.text_view;
	
	public VHProConView(boolean isPro) {
		super(isPro? LAYOUT_PRO : LAYOUT_CON, TEXTVIEW);
	}
}