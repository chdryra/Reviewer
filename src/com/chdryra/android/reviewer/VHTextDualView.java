/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import com.chdryra.android.mygenerallibrary.VHDualStringView;

public class VHTextDualView extends VHDualStringView {
	private static final int LAYOUT = R.layout.grid_cell_text_dual;
	private static final int UPPER = R.id.upper_text;
	private static final int LOWER = R.id.lower_text;
	
	public VHTextDualView() {
		super(LAYOUT, UPPER, LOWER);
	}
}
