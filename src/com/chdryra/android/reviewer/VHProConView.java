/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;


class VHProConView extends VHTextView {
	private static final int LAYOUT_PRO = R.layout.grid_cell_pro;
	private static final int LAYOUT_CON = R.layout.grid_cell_con;
	private static final int TV_PRO = R.id.pro;
	private static final int TV_CON = R.id.con;
	
	public VHProConView(boolean isPro) {
		super(isPro? LAYOUT_PRO : LAYOUT_CON, isPro? TV_PRO : TV_CON);
	}
}