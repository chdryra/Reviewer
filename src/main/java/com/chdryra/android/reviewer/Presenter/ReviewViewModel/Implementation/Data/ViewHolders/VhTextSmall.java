/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders;

import com.chdryra.android.mygenerallibrary.Viewholder.VHDataStringGetter;
import com.chdryra.android.mygenerallibrary.Viewholder.VHString;
import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolder;
import com.chdryra.android.reviewer.R;

/**
 * Simple {@link ViewHolder} for displaying strings on a
 * grid cell.
 * <p/>
 * <p>
 * Just {@link VHString} using a package-specific XML layout for separation and encapsulation
 * purposes.
 * </p>
 */
public class VhTextSmall extends VHString {
    private static final int LAYOUT = R.layout.grid_cell_text_small;
    private static final int TEXTVIEW = R.id.grid_cell_text_view_small;

    public VhTextSmall() {
        super(LAYOUT, TEXTVIEW);
    }

    public VhTextSmall(VHDataStringGetter getter) {
        super(LAYOUT, TEXTVIEW, getter);
    }
}
