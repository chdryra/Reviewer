/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders;

import com.chdryra.android.mygenerallibrary.Viewholder.VHDualString;
import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolder;
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
class VhDualTextSmall extends VhDualText {
    private static final int LAYOUT = R.layout.grid_cell_text_dual_small;
    private static final int UPPER = R.id.upper_text_small;
    private static final int LOWER = R.id.lower_text_small;

    VhDualTextSmall() {
        super(LAYOUT, UPPER, LOWER);
    }
}
