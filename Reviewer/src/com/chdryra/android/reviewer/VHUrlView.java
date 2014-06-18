package com.chdryra.android.reviewer;

import com.chdryra.android.mygenerallibrary.GVData;
import com.chdryra.android.reviewer.GVUrlList.GVUrl;

public class VHUrlView extends VHTextView {
	private static final int LAYOUT = R.layout.grid_cell_url;
	private static final int TEXTVIEW = R.id.link;
	
	public VHUrlView() {
		super(LAYOUT, TEXTVIEW, new GVDataStringGetter() {
			@Override
			public String getString(GVData data) {
				GVUrl url = (GVUrl)data;
				return url != null? url.toShortenedString() : null;
			}
		});
	}
}
