/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders;

import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolderBasic;
import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolderData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.reviewer.R;

import static com.chdryra.android.reviewer.R.id.address;

/**
 * Created by: Rizwan Choudrey
 * On: 16/12/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class VhLocationFormatted extends ViewHolderBasic{
    private static final int LAYOUT = R.layout.formatted_locations;
    private static final int NAME = R.id.short_name;
    private static final int ADDRESS = address;

    public VhLocationFormatted() {
        super(LAYOUT, new int[]{NAME, ADDRESS});
    }

    @Override
    public void updateView(ViewHolderData data) {
        GvLocation location = (GvLocation) data;
        setText(NAME, location.getShortenedName());
        setText(ADDRESS, location.getAddress());
    }
}
