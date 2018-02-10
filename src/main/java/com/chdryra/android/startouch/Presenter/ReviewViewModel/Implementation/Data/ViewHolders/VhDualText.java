/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders;

import com.chdryra.android.corelibrary.Viewholder.VHDDualString;
import com.chdryra.android.corelibrary.Viewholder.VHDualString;
import com.chdryra.android.corelibrary.Viewholder.ViewHolder;
import com.chdryra.android.startouch.R;

/**
 * Simple {@link ViewHolder} for {@link VHDualString}.
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
        this(LAYOUT, UPPER, LOWER);
    }

    VhDualText(int layout, int upper, int lower) {
        super(layout, upper, lower);
    }

    void updateView(String upper, String lower) {
        super.updateView(new VHDDualString(upper, lower));
    }
}
