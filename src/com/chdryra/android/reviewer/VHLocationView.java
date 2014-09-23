/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

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
