/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 13 March, 2015
 */

package com.chdryra.android.reviewer.View.Dialogs;

import android.widget.EditText;

import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.GvDataModel.GvLocationList;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by: Rizwan Choudrey
 * On: 13/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class EditLocation extends AddEditLayout<GvLocationList.GvLocation> {
    public static final int LAYOUT = R.layout.dialog_location_edit;
    public static final int LOCATION = R.id.location_edit_edit_text;
    public static final int[] VIEWS = new int[]{LOCATION};

    private LatLng mLatLng;

    //Constructors
    public EditLocation(GvDataEditor editor) {
        super(GvLocationList.GvLocation.class, LAYOUT, VIEWS, LOCATION, editor);
    }

    //Overridden
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
