/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders;

import android.widget.TextView;

import com.chdryra.android.mygenerallibrary.Viewholder.VHDataStringGetter;
import com.chdryra.android.mygenerallibrary.Viewholder.VHString;
import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolder;
import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolderBasic;
import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolderData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
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
class VhTextRating extends ViewHolderBasic {
    private static final int LAYOUT = R.layout.grid_cell_text_rating;
    private static final int STRING_VIEW = R.id.string_text_view;
    private static final int RATING_VIEW = R.id.rating_text_view;

    private VHDataStringGetter mGetter;
    private TextView mTextView;
    private TextView mRatingView;

    private VhTextRating() {
        super(LAYOUT, new int[]{STRING_VIEW, RATING_VIEW});
    }

    public VhTextRating(VHDataStringGetter getter) {
        this();
        mGetter = getter;
    }

    private void initDefaultGetter() {
        mGetter = new VHDataStringGetter() {
            @Override
            public String getString(ViewHolderData data) {
                return data.toString();
            }
        };
    }

    @Override
    public void updateView(ViewHolderData data) {
        if (mTextView == null) mTextView = (TextView) getView(STRING_VIEW);
        if (mRatingView == null) mRatingView = (TextView) getView(RATING_VIEW);
        GvData gvData = (GvData) data;
        mTextView.setText(mGetter.getString(gvData));
    }
}
