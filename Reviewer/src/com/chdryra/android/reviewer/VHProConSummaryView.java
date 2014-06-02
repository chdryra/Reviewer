package com.chdryra.android.reviewer;


public class VHProConSummaryView extends VHDualStringView {
	private final static int LAYOUT = R.layout.grid_cell_procon_summary;
	private final static int UPPER = R.id.pros_text_view;
	private final static int LOWER = R.id.cons_text_view;
	
	public VHProConSummaryView() {
		super(LAYOUT, UPPER, LOWER);
	}
}
