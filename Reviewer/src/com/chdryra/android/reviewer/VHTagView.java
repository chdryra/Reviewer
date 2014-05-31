package com.chdryra.android.reviewer;

import com.chdryra.android.mygenerallibrary.VHStringView;

class VHTagView extends VHStringView{
	private static final int LAYOUT = R.layout.grid_cell_tag;
	private static final int TEXTVIEW = R.id.text_view;
	
	public VHTagView() {
		super(LAYOUT, TEXTVIEW);
	}
}