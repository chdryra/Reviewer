/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 12 March, 2015
 */

package com.chdryra.android.reviewer;

import android.widget.TextView;

import com.chdryra.android.mygenerallibrary.ViewHolderBasic;
import com.chdryra.android.mygenerallibrary.ViewHolderData;

/**
 * Created by: Rizwan Choudrey
 * On: 12/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class VhLocatedPlaceDistance extends ViewHolderBasic {
    private static final int LAYOUT = R.layout.located_view_list_item;
    private static final int NAME   = R.id.located_place_name_text_view;

    private TextView mName;

    public VhLocatedPlaceDistance() {
        super(LAYOUT, new int[]{NAME});
    }

    @Override
    public void updateView(ViewHolderData data) {
        if (mName == null) mName = (TextView) getView(NAME);

        VhdLocatedPlaceDistance dist = (VhdLocatedPlaceDistance) data;
        if (dist != null && dist.isValidForDisplay()) {
            mName.setText(dist.getPlace().getDescription());
        }
    }
}
