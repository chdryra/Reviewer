/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class GridCellLayout extends FrameLayout {

	public GridCellLayout(Context context) {
		super(context);
	}

   public GridCellLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
 
    public GridCellLayout(Context context, AttributeSet attrs) {
        super(context, attrs, R.attr.gridCellStyle);
    }

}
