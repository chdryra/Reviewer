/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import com.chdryra.android.mygenerallibrary.VHString;

/**
 * Simple {@link com.chdryra.android.mygenerallibrary.ViewHolder} for displaying strings on a
 * grid cell.
 * <p/>
 * <p>
 * Just {@link VHString} using a package-specific XML layout for separation and encapsulation
 * purposes.
 * </p>
 */
class VhText extends VHString {
    private static final int LAYOUT   = R.layout.grid_cell_text;
    private static final int TEXTVIEW = R.id.grid_cell_text_view;

    public VhText() {
        super(LAYOUT, TEXTVIEW);
    }

    VhText(VHDataStringGetter getter) {
        super(LAYOUT, TEXTVIEW, getter);
    }

}
