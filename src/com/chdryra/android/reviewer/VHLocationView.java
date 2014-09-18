package com.chdryra.android.reviewer;

import com.chdryra.android.mygenerallibrary.GVData;
import com.chdryra.android.reviewer.GVLocationList.GVLocation;

class VHLocationView extends VHTextView {
	
	public VHLocationView(final boolean showAt) {
		super(new GVDataStringGetter() {
			@Override
			public String getString(GVData data) {
				GVLocation location = (GVLocation)data;
				String at = showAt? "@" : "";
				return location != null? at + location.getShortenedName() : null;
			}
		});
	}
}
