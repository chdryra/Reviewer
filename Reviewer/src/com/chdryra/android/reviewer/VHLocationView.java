package com.chdryra.android.reviewer;

import com.chdryra.android.mygenerallibrary.GVData;
import com.chdryra.android.mygenerallibrary.VHStringView;
import com.chdryra.android.reviewer.GVLocationList.GVLocation;

class VHLocationView extends VHStringView {
	private static final int LAYOUT = R.layout.grid_cell_location;
	private static final int TEXTVIEW = R.id.text_view;
	
	public VHLocationView() {
		super(LAYOUT, TEXTVIEW, new GVDataStringGetter() {
			@Override
			public String getString(GVData data) {
				GVLocation location = (GVLocation)data;
				return location != null? location.getShortenedName() : null;
			}
		});
	}
}
