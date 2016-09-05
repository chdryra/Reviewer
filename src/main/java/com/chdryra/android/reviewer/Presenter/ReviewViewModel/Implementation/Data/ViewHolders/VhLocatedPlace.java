/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders;

import android.widget.TextView;

import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolderBasic;
import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolderData;
import com.chdryra.android.reviewer.R;

/**
 * Created by: Rizwan Choudrey
 * On: 12/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
class VhLocatedPlace extends ViewHolderBasic {
    private static final int LAYOUT = R.layout.located_view_list_item;
    private static final int NAME = R.id.located_place_name_text_view;

    private TextView mName;

    //Constructors
    public VhLocatedPlace() {
        super(LAYOUT, new int[]{NAME});
    }

    //Overridden
    @Override
    public void updateView(ViewHolderData data) {
        if (mName == null) mName = (TextView) getView(NAME);

        VhdLocatedPlace dist = (VhdLocatedPlace) data;
        if (dist != null && dist.isValidForDisplay()) {
            mName.setText(dist.getPlace().getDescription());
        }
    }
}
