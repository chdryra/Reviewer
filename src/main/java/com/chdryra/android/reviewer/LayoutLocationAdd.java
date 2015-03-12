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
import com.chdryra.android.mygenerallibrary.ViewHolderAdapterFiltered;
import com.chdryra.android.mygenerallibrary.ViewHolderDataList;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 04/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class LayoutLocationAdd extends GvDataEditLayout<GvLocationList.GvLocation>
        implements LocationClientConnector.Locatable, NearestNamesSuggester.SuggestionsListener {
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
    private LocatedPlaceAutoCompleter mAutoCompleter;
    private ViewHolderAdapterFiltered mFilter;
    private Activity                mActivity;

    private boolean mLatLngProvided = false;

    private String mNoLocation;
    private String mSearching;
    private int    mHint;

    private VhdLocatedPlaceDistance mNoLocationPlace;
    private VhdLocatedPlaceDistance mSearchingPlace;

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

        name.setHint(mHint);

        ListView suggestionsList = (ListView) getView(LIST);
        suggestionsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VhdLocatedPlaceDistance location = (VhdLocatedPlaceDistance) parent
                        .getAdapter().getItem(position);
                name.setText(location.getPlace().getName());
            }
        });

        if (mLatLngProvided) findPlaceSuggestions();

        return v;
    }

    @Override
    public void onActivityAttached(Activity activity, Bundle args) {
        mActivity = activity;
        mNoLocation = mActivity.getResources().getString(NO_LOCATION);

        LatLng latLng = args.getParcelable(LATLNG);
        if (latLng != null) {
            mLatLng = latLng;
            boolean fromImage = args.getBoolean(FROM_IMAGE);
            mLatLngProvided = true;

            mSearching = mActivity.getResources().getString(fromImage ? SEARCHING_IMAGE :
                    SEARCHING_NEARBY);
            mHint = fromImage ? R.string.edit_text_name_image_location_hint
                    : R.string.edit_text_name_current_location_hint;
            setMessages();

        } else {
            mSearching = mActivity.getResources().getString(SEARCHING_NEARBY);
            mHint = R.string.edit_text_name_current_location_hint;
            setMessages();
            LocationClientConnector locationClient = new LocationClientConnector(mActivity, this);
            locationClient.connect();
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
    public void onNearestNamesSuggested(ArrayList<LocatedPlace> names) {
        ViewHolderDataList<VhdLocatedPlaceDistance> places = new ViewHolderDataList<>();
        if (names.size() == 0) {
            places.add(mNoLocationPlace);
        } else {
            for (LocatedPlace name : names) {
                places.add(new VhdLocatedPlaceDistance(name, mLatLng));
            }
        }

        setNewSuggestionsAdapter(places);
    }

    private void onLatLngFound(LatLng latLng) {
        mLatLng = latLng;
        setMessages();
        findPlaceSuggestions();
    }

    private void setMessages() {
        mNoLocationPlace = new VhdLocatedPlaceDistance(new LocatedPlace(mLatLng, mNoLocation, "",
                "NoLocationMessage"), null);
        mSearchingPlace = new VhdLocatedPlaceDistance(new LocatedPlace(mLatLng, mSearching, "",
                "SearchingMessage"), null);
    }

    private void findPlaceSuggestions() {
        //Autocomplete suggestions
        mAutoCompleter = new LocatedPlaceAutoCompleter(mLatLng);

        //Initial suggestions
        NearestNamesSuggester suggester = new NearestNamesSuggester(mLatLng, this);
        suggester.fetchSuggestions(NUMBER_SUGGESTIONS);

        //Whilst initial suggestions are being found....
        ViewHolderDataList<VhdLocatedPlaceDistance> message = new ViewHolderDataList<>();
        message.add(mSearchingPlace);
        setNewSuggestionsAdapter(message);
    }

    private void setNewSuggestionsAdapter(ViewHolderDataList<VhdLocatedPlaceDistance> names) {
        mFilter = new ViewHolderAdapterFiltered(mActivity, names, mAutoCompleter);
        ((ListView) getView(LIST)).setAdapter(mFilter);
    }
}
