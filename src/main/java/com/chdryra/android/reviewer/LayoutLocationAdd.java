/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 4 March, 2015
 */

package com.chdryra.android.reviewer;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.chdryra.android.myandroidwidgets.ClearableEditText;
import com.chdryra.android.mygenerallibrary.LocationClientConnector;
import com.chdryra.android.mygenerallibrary.PlaceAutoCompleteSuggester;
import com.chdryra.android.mygenerallibrary.PlaceSuggester;
import com.chdryra.android.mygenerallibrary.StringFilterAdapter;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 04/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class LayoutLocationAdd extends GvDataEditLayout<GvLocationList.GvLocation>
        implements LocationClientConnector.Locatable, PlaceSuggester.SuggestionsListener {
    public static final int   LAYOUT = R.layout.dialog_location;
    public static final int   NAME   = R.id.location_edit_text;
    public static final int   LIST   = R.id.suggestions_list_view;
    public static final int[] VIEWS  = new int[]{NAME, LIST};

    public static final String LATLNG     = "com.chdryra.android.reviewer.latlng";
    public static final String FROM_IMAGE = "com.chdryra.android.reviewer.from_image";

    private static final int NUMBER_SUGGESTIONS = 10;
    private static final int SEARCHING_NEARBY   = R.string.edit_text_searching_near_here;
    private static final int SEARCHING_IMAGE    = R.string.edit_text_searching_near_image;
    private static final int NO_LOCATION        = R.string.edit_text_no_suggestions;

    private LatLng                  mLatLng;
    private LocationClientConnector mLocationClient;
    private PlaceAutoCompleteSuggester mAutoCompleter;
    private StringFilterAdapter        mFilter;
    private Activity                mActivity;

    private boolean mLatLngProvided = false;
    private boolean mFromImage      = false;

    public LayoutLocationAdd(GvDataAdder adder) {
        super(GvLocationList.GvLocation.class, LAYOUT, VIEWS, NAME, adder);
    }

    @Override
    public GvLocationList.GvLocation createGvData() {
        String name = ((EditText) getView(NAME)).getText().toString().trim();
        return new GvLocationList.GvLocation(mLatLng, name);
    }

    @Override
    public void updateLayout(GvLocationList.GvLocation location) {
        ((EditText) getView(NAME)).setText(location.getName());
    }

    @Override
    public View createLayoutUi(Context context, GvLocationList.GvLocation data) {
        View v = super.createLayoutUi(context, data);
        final ClearableEditText name = (ClearableEditText) getView(NAME);
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mFilter != null) mFilter.filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        if (mFromImage) name.setHint(R.string.edit_text_name_image_location_hint);

        ListView suggestionsList = (ListView) getView(LIST);
        suggestionsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String locationName = (String) parent.getAdapter().getItem(position);
                name.setText(locationName);
            }
        });

        if (mLatLngProvided) findPlaceSuggestions();

        return v;
    }

    @Override
    public void onActivityAttached(Activity activity, Bundle args) {
        mActivity = activity;
        mLocationClient = new LocationClientConnector(mActivity, this);
        LatLng latLng = args.getParcelable(LATLNG);
        if (latLng != null) {
            mLatLng = latLng;
            mFromImage = args.getBoolean(FROM_IMAGE);
            mLatLngProvided = true;
        } else {
            mLocationClient.connect();
        }
    }

    @Override
    public void onLocated(LatLng latLng) {
        onLatLngFound(latLng);
    }

    @Override
    public void onLocationClientConnected(LatLng latLng) {
        onLatLngFound(latLng);
    }

    @Override
    public void onSuggestionsFound(ArrayList<String> addresses) {
        if (addresses.size() == 0) addresses.add(mActivity.getResources().getString(NO_LOCATION));
        setNewSuggestionsAdapter(addresses);
    }

    private void onLatLngFound(LatLng latLng) {
        mLatLng = latLng;
        mFromImage = false;
        findPlaceSuggestions();
    }

    private void findPlaceSuggestions() {
        //Autocomplete suggestions
        mAutoCompleter = new PlaceAutoCompleteSuggester(mLatLng);

        //Initial suggestions
        PlaceSuggester suggester = new PlaceSuggester(mActivity, mLatLng, this);
        suggester.getSuggestions(NUMBER_SUGGESTIONS);

        //Whilst initial suggestions are being found....
        ArrayList<String> message = new ArrayList<String>();
        message.add(mActivity.getResources().getString(mFromImage ? SEARCHING_IMAGE :
                SEARCHING_NEARBY));
        setNewSuggestionsAdapter(message);
    }

    private void setNewSuggestionsAdapter(ArrayList<String> suggestions) {
        mFilter = new StringFilterAdapter(mActivity, suggestions, mAutoCompleter);
        ((ListView) getView(LIST)).setAdapter(mFilter);
    }
}
