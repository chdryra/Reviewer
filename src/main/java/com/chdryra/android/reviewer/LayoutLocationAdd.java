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
import android.location.Location;
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
import com.chdryra.android.remoteapifetchers.GpPlaceDetailsResult;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 04/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class LayoutLocationAdd extends GvDataEditLayout<GvLocationList.GvLocation>
        implements LocationClientConnector.Locatable,
        GpNearestNamesSuggester.SuggestionsListener,
        GpPlaceDetailsFetcher.DetailsListener {
    public static final int   LAYOUT = R.layout.dialog_location_add;
    public static final int   NAME   = R.id.location_add_edit_text;
    public static final int   LIST   = R.id.suggestions_list_view;
    public static final int[] VIEWS  = new int[]{NAME, LIST};

    public static final String LATLNG     = "com.chdryra.android.reviewer.latlng";
    public static final String FROM_IMAGE = "com.chdryra.android.reviewer.from_image";

    private static final int NUMBER_SUGGESTIONS = 10;
    private static final int SEARCHING_NEARBY   = R.string.edit_text_searching_near_here;
    private static final int SEARCHING_IMAGE    = R.string.edit_text_searching_near_image;
    private static final int NO_LOCATION        = R.string.edit_text_no_suggestions;

    private Activity                              mActivity;
    private LatLng                                mCurrentLatLng;
    private LatLng                                mSelectedLatLng;
    private ViewHolderAdapterFiltered             mFilteredAdapter;
    private ViewHolderAdapterFiltered.QueryFilter mQueryFilter;

    private boolean mLatLngProvided = false;

    private String mNoLocation;
    private String mSearching;
    private int    mHint;

    private VhdLocatedPlace mNoLocationMessage;
    private VhdLocatedPlace mSearchingMessage;

    private EditText                            mNameEditText;
    private ViewHolderDataList<VhdLocatedPlace> mCurrentLatLngPlaces;

    public LayoutLocationAdd(GvDataAdder adder) {
        super(GvLocationList.GvLocation.class, LAYOUT, VIEWS, NAME, adder);
    }

    @Override
    public GvLocationList.GvLocation createGvData() {
        String name = ((EditText) getView(NAME)).getText().toString().trim();
        return new GvLocationList.GvLocation(mSelectedLatLng, name);
    }

    @Override
    public void updateLayout(GvLocationList.GvLocation location) {
        ((EditText) getView(NAME)).setText(location.getName());
    }

    @Override
    public View createLayoutUi(Context context, GvLocationList.GvLocation data) {
        View v = super.createLayoutUi(context, data);
        mNameEditText = (ClearableEditText) getView(NAME);
        mNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mFilteredAdapter != null) mFilteredAdapter.filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mNameEditText.setHint(mHint);

        ListView suggestionsList = (ListView) getView(LIST);
        suggestionsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VhdLocatedPlace location = (VhdLocatedPlace) parent.getAdapter().getItem(position);
                fetchPlaceDetails(location.getPlace());
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
            mCurrentLatLng = latLng;
            mSelectedLatLng = latLng;
            boolean fromImage = args.getBoolean(FROM_IMAGE);
            mLatLngProvided = true;

            mSearching = mActivity.getResources().getString(fromImage ? SEARCHING_IMAGE :
                    SEARCHING_NEARBY);
            mHint = fromImage ? R.string.edit_text_name_image_location_hint
                    : R.string.edit_text_add_a_location;
            setMessages();

        } else {
            mSearching = mActivity.getResources().getString(SEARCHING_NEARBY);
            mHint = R.string.edit_text_add_a_location;
            setMessages();
            LocationClientConnector locationClient = new LocationClientConnector(mActivity, this);
            locationClient.connect();
        }
    }

    @Override
    public void onAdd(GvLocationList.GvLocation data) {
        super.onAdd(data);
        setNewSuggestionsAdapter(mCurrentLatLngPlaces);
    }

    @Override
    public void onLocated(Location location) {
        onLatLngFound(new LatLng(location.getLatitude(), location.getLongitude()));
    }

    @Override
    public void onLocationClientConnected(Location location) {
        onLocated(location);
    }

    @Override
    public void onNearestNamesSuggested(ArrayList<LocatedPlace> places) {
        mCurrentLatLngPlaces = new ViewHolderDataList<>();
        if (places.size() == 0) {
            mCurrentLatLngPlaces.add(mNoLocationMessage);
        } else {
            for (LocatedPlace place : places) {
                mCurrentLatLngPlaces.add(new VhdLocatedPlace(place));
            }
        }

        setNewSuggestionsAdapter(mCurrentLatLngPlaces);
    }

    @Override
    public void onPlaceDetailsFound(GpPlaceDetailsResult details) {
        mSelectedLatLng = details.getGeometry().getLatLng();
        mNameEditText.setText(details.getName().getString());
        mNameEditText.setHint(mHint);
    }

    private void fetchPlaceDetails(LocatedPlace place) {
        mNameEditText.setText(null);
        mNameEditText.setHint(R.string.edit_text_fetching_location_hint);
        GpPlaceDetailsFetcher fetcher = new GpPlaceDetailsFetcher(place, LayoutLocationAdd.this);
        fetcher.fetchDetails();
    }

    private void onLatLngFound(LatLng latLng) {
        mCurrentLatLng = latLng;
        mSelectedLatLng = latLng;
        setMessages();
        findPlaceSuggestions();
    }

    private void setMessages() {
        mNoLocationMessage = new VhdLocatedPlace(new LocatedPlace(mCurrentLatLng, mNoLocation));
        mSearchingMessage = new VhdLocatedPlace(new LocatedPlace(mCurrentLatLng, mSearching));
    }

    private void findPlaceSuggestions() {
        //Autocomplete suggestions
        mQueryFilter = new GpAutoCompleter(mCurrentLatLng);

        //Initial suggestions
        GpNearestNamesSuggester suggester = new GpNearestNamesSuggester(mCurrentLatLng, this);
        suggester.fetchSuggestions(NUMBER_SUGGESTIONS);

        //Whilst initial suggestions are being found....
        ViewHolderDataList<VhdLocatedPlace> message = new ViewHolderDataList<>();
        message.add(mSearchingMessage);
        setNewSuggestionsAdapter(message);
    }

    private void setNewSuggestionsAdapter(ViewHolderDataList<VhdLocatedPlace> names) {
        mFilteredAdapter = new ViewHolderAdapterFiltered(mActivity, names, mQueryFilter);
        ((ListView) getView(LIST)).setAdapter(mFilteredAdapter);
    }
}
