/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 13 March, 2015
 */

package com.chdryra.android.reviewer;

import android.widget.EditText;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by: Rizwan Choudrey
 * On: 13/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class LayoutLocationEdit extends GvDataEditLayout<GvLocationList.GvLocation> {
    public static final int   LAYOUT   = R.layout.dialog_location_edit;
    public static final int   LOCATION = R.id.location_edit_edit_text;
    public static final int[] VIEWS    = new int[]{LOCATION};

    private LatLng mLatLng;

    public LayoutLocationEdit(GvDataEditor editor) {
        super(GvLocationList.GvLocation.class, LAYOUT, VIEWS, LOCATION, editor);
    }

    @Override
    public GvLocationList.GvLocation createGvData() {
        return new GvLocationList.GvLocation(mLatLng, ((EditText) getView(LOCATION)).getText()
                .toString()
                .trim());
    }

    @Override
    public void updateLayout(GvLocationList.GvLocation location) {
        ((EditText) getView(LOCATION)).setText(location.getName());
        mLatLng = location.getLatLng();
    }
}
