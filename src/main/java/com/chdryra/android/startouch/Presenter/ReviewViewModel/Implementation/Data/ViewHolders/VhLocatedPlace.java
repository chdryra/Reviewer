/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders;

import com.chdryra.android.corelibrary.Viewholder.ViewHolderBasic;
import com.chdryra.android.corelibrary.Viewholder.ViewHolderData;
import com.chdryra.android.startouch.R;

/**
 * Created by: Rizwan Choudrey
 * On: 12/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
class VhLocatedPlace extends ViewHolderBasic {
    private static final int LAYOUT = R.layout.located_view_list_item;
    private static final int NAME = R.id.located_place_name_text_view;

    //Constructors
    public VhLocatedPlace() {
        super(LAYOUT, new int[]{NAME});
    }

    //Overridden
    @Override
    public void updateView(ViewHolderData data) {
        VhdLocatedPlace dist = (VhdLocatedPlace) data;
        if (dist != null && dist.isValidForDisplay()) {
            setText(NAME, dist.getPlace().getDescription());
        }
    }
}
