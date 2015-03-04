/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 4 March, 2015
 */

package com.chdryra.android.reviewer;

import android.widget.EditText;
import android.widget.ListView;

import com.chdryra.android.myandroidwidgets.ClearableEditText;
import com.chdryra.android.mygenerallibrary.LocationClientConnector;
import com.chdryra.android.mygenerallibrary.PlaceAutoCompleteSuggester;
import com.chdryra.android.mygenerallibrary.StringFilterAdapter;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by: Rizwan Choudrey
 * On: 04/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class LayoutLocation extends GvDataEditLayout<GvLocationList.GvLocation> {
    public static final  String LATLNG             = "com.chdryra.android.reviewer.latlng";
    public static final  String FROM_IMAGE         = "com.chdryra.android.reviewer.from_image";
    public static final  int    LAYOUT             = R.layout.dialog_location;
    public static final  int    NAME               = R.id.location_edit_text;
    public static final  int[]  VIEWS              = new int[]{NAME};
    private static final int    NUMBER_SUGGESTIONS = 10;
    private static final int    SEARCHING_NEARBY   = R.string.edit_text_searching_near_here;
    private static final int    SEARCHING_IMAGE    = R.string.edit_text_searching_near_image;
    private static final int    NO_LOCATION        = R.string.edit_text_no_suggestions;

    private DialogFragmentLocationListener mListener;
    private ClearableEditText              mNameEditText;
    private ListView                       mLocationNameSuggestions;
    private LatLng                         mLatLng;
    private LocationClientConnector        mLocationClient;

    private PlaceAutoCompleteSuggester mAutoCompleter;
    private StringFilterAdapter        mFilter;

    public interface DialogFragmentLocationListener {
        public void onLocationChosen(GvLocationList.GvLocation location);

        public void onMapRequested(GvLocationList.GvLocation location);
    }

    public LayoutLocation(GvDataAdder adder) {
        super(GvLocationList.GvLocation.class, LAYOUT, VIEWS, NAME, adder);
    }

    public LayoutLocation(GvDataEditor editor) {
        super(GvLocationList.GvLocation.class, LAYOUT, VIEWS, NAME, editor);
    }

    @Override
    public GvLocationList.GvLocation createGvData() {
        return new GvLocationList.GvLocation();
    }

    @Override
    public void updateLayout(GvLocationList.GvLocation location) {
        ((EditText) getView(NAME)).setText(location.getName());
    }
}
