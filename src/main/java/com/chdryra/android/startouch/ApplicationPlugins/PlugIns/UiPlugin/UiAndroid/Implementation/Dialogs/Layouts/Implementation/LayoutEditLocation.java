/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid
        .Implementation.Dialogs.Layouts.Implementation;


import android.widget.EditText;

import com.chdryra.android.corelibrary.LocationServices.LocationId;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Dialogs.Layouts.Interfaces.GvDataEditor;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvLocation;
import com.chdryra.android.startouch.R;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by: Rizwan Choudrey
 * On: 13/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class LayoutEditLocation extends AddEditLayoutBasic<GvLocation> {
    private static final int LAYOUT = R.layout.dialog_location_edit;
    private static final int LOCATION = R.id.location_edit_edit_text;

    private LatLng mLatLng;
    private String mAddress;
    private LocationId mId;

    //Constructors
    public LayoutEditLocation(GvDataEditor editor) {
        super(GvLocation.class, new LayoutHolder(LAYOUT, LOCATION), LOCATION, editor);
    }

    //Overridden
    @Override
    public GvLocation createGvDataFromInputs() {
        return new GvLocation(mLatLng, ((EditText) getView(LOCATION)).getText()
                .toString().trim(), mAddress, mId);
    }

    @Override
    public void updateView(GvLocation location) {
        ((EditText) getView(LOCATION)).setText(location.getName());
        mLatLng = location.getLatLng();
        mAddress = location.getAddress();
        mId = location.getLocationId();
    }
}
