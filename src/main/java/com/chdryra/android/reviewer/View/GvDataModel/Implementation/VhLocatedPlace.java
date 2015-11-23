/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 12 March, 2015
 */

package com.chdryra.android.reviewer.View.GvDataModel.Implementation;

import android.widget.TextView;

import com.chdryra.android.mygenerallibrary.ViewHolderBasic;
import com.chdryra.android.mygenerallibrary.ViewHolderData;
import com.chdryra.android.reviewer.R;

/**
 * Created by: Rizwan Choudrey
 * On: 12/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class VhLocatedPlace extends ViewHolderBasic {
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
