/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.View.GvDataModel.Implementation.ViewHolders;

import com.chdryra.android.mygenerallibrary.VHDDualString;
import com.chdryra.android.mygenerallibrary.VHDualString;
import com.chdryra.android.reviewer.R;

/**
 * Simple {@link com.chdryra.android.mygenerallibrary.ViewHolder} for {@link VHDualString}.
 * Displays an
 * upper and
 * lower string.
 * <p/>
 * <p>
 * Just {@link VHDualString} using a package-specific XML layout for separation and
 * encapsulation
 * purposes.
 * </p>
 */
class VhDualText extends VHDualString {
    private static final int LAYOUT = R.layout.grid_cell_text_dual;
    private static final int UPPER = R.id.upper_text;
    private static final int LOWER = R.id.lower_text;

    VhDualText() {
        super(LAYOUT, UPPER, LOWER);
    }

    protected void updateView(String upper, String lower) {
        super.updateView(new VHDDualString(upper, lower));
    }
}
