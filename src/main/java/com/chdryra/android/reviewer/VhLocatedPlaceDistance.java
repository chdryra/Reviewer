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
    private static final int LAYOUT   = R.layout.located_view_list_item;
    private static final int NAME     = R.id.located_place_name_text_view;
    private static final int DISTANCE = R.id.located_place_distance_text_view;

    private TextView mName;
    private TextView mDistance;

    public VhLocatedPlaceDistance() {
        super(LAYOUT, new int[]{NAME, DISTANCE});
    }

    @Override
    public void updateView(ViewHolderData data) {
        if (mName == null) mName = (TextView) getView(NAME);
        if (mDistance == null) mDistance = (TextView) getView(DISTANCE);

        VhdLocatedPlaceDistance dist = (VhdLocatedPlaceDistance) data;
        if (dist != null && dist.isValidForDisplay()) {
            mName.setText(getFormattedName(dist.getPlace()));
            mDistance.setText(getDistanceString(dist));
        }
    }

    private String getFormattedName(LocatedPlace place) {
        String name = place.getName();
        String address = place.getAddress();

        return name + ", " + address;
    }

    private String getDistanceString(VhdLocatedPlaceDistance dist) {
        int distance = dist.getDistance();
        return distance == -1 ? "-" : String.valueOf(distance) + "m";
    }
}
