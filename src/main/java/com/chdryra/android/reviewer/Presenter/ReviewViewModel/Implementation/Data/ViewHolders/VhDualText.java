/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders;

import android.widget.TextView;

import com.chdryra.android.mygenerallibrary.Viewholder.VHDDualString;
import com.chdryra.android.mygenerallibrary.Viewholder.VHDualString;
import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolder;
import com.chdryra.android.reviewer.R;

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
        super(LAYOUT, UPPER, LOWER);
    }

    void updateView(String upper, String lower) {
        super.updateView(new VHDDualString(upper, lower));
    }

    protected void updateUpper(String lower) {
        TextView view = (TextView) getView(UPPER);
        super.updateView(new VHDDualString(view.getText().toString(), lower));
    }
}
