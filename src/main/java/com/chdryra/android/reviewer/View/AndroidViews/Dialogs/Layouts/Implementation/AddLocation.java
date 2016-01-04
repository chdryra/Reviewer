/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 4 March, 2015
 */

package com.chdryra.android.reviewer.View.AndroidViews.Dialogs.Layouts.Implementation;

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
import com.chdryra.android.mygenerallibrary.VhDataList;
import com.chdryra.android.mygenerallibrary.ViewHolderAdapterFiltered;
import com.chdryra.android.mygenerallibrary.ViewHolderDataList;
import com.chdryra.android.remoteapifetchers.GpNearestNamesSuggester;
import com.chdryra.android.remoteapifetchers.GpPlaceDetailsFetcher;
import com.chdryra.android.remoteapifetchers.GpPlaceDetailsResult;
import com.chdryra.android.remoteapifetchers.GpPlaceSearchResults;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .VhdLocatedPlace;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.Utils.GpAutoCompleter;
import com.chdryra.android.reviewer.Utils.GpLocatedPlaceConverter;
import com.chdryra.android.reviewer.Utils.LocatedPlace;
import com.chdryra.android.reviewer.View.AndroidViews.Dialogs.Layouts.Interfaces.GvDataAdder;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 04/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class AddLocation extends AddEditLayoutBasic<GvLocation>
        implements LocationClientConnector.Locatable,
        GpNearestNamesSuggester.SuggestionsListener,
        GpPlaceDetailsFetcher.DetailsListener {
    public static final int LAYOUT = R.layout.dialog_location_add;
    public static final int NAME = R.id.location_add_edit_text;
    public static final int LIST = R.id.suggestions_list_view;
    public static final int[] VIEWS = new int[]{NAME, LIST};

    public static final String LATLNG = "com.chdryra.android.reviewer.latlng";
    public static final String FROM_IMAGE = "com.chdryra.android.reviewer.from_image";

    private static final int SEARCHING_NEARBY = R.string.edit_text_searching_near_here;
    private static final int SEARCHING_IMAGE = R.string.edit_text_searching_near_image;
    private static final int NO_LOCATION = R.string.edit_text_no_suggestions;

    private Activity mActivity;
    private LatLng mCurrentLatLng;
    private LatLng mSelectedLatLng;
    private ViewHolderAdapterFiltered mFilteredAdapter;
    private ViewHolderAdapterFiltered.QueryFilter mQueryFilter;

    private String mNoLocation;
    private String mSearching;
    private int mHint;

    private VhdLocatedPlace mNoLocationMessage;
    private VhdLocatedPlace mSearchingMessage;

    private EditText mNameEditText;
    private ViewHolderDataList<VhdLocatedPlace> mCurrentLatLngPlaces;

    //Constructors
    public AddLocation(GvDataAdder adder) {
        super(GvLocation.class, LAYOUT, VIEWS, NAME, adder);
    }

    private void fetchPlaceDetails(LocatedPlace place) {
        mNameEditText.setText(null);
        mNameEditText.setHint(R.string.edit_text_fetching_location_hint);
        String placeId = place.getId().getId();
        GpPlaceDetailsFetcher fetcher = new GpPlaceDetailsFetcher(placeId, AddLocation.this);
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
        suggester.fetchSuggestions();

        //Whilst initial suggestions are being found....
        ViewHolderDataList<VhdLocatedPlace> message = new VhDataList<>();
        message.add(mSearchingMessage);
        setNewSuggestionsAdapter(message);
    }

    private void setNewSuggestionsAdapter(ViewHolderDataList<VhdLocatedPlace> names) {
        mFilteredAdapter = new ViewHolderAdapterFiltered(mActivity, names, mQueryFilter);
        ((ListView) getView(LIST)).setAdapter(mFilteredAdapter);
    }

    //Overridden
    @Override
    public GvLocation createGvDataFromInputs() {
        String name = ((EditText) getView(NAME)).getText().toString().trim();
        return new GvLocation(mSelectedLatLng, name);
    }

    @Override
    public void onAdd(GvLocation data) {
        super.onAdd(data);
        setNewSuggestionsAdapter(mCurrentLatLngPlaces);
    }

    @Override
    public void updateLayout(GvLocation location) {
        ((EditText) getView(NAME)).setText(location.getName());
    }

    @Override
    public View createLayoutUi(Context context, GvLocation data) {
        View v = super.createLayoutUi(context, data);
        mNameEditText = (ClearableEditText) getView(NAME);
        mNameEditText.addTextChangedListener(new TextWatcher() {
            //Overridden
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
            //Overridden
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VhdLocatedPlace location = (VhdLocatedPlace) parent.getAdapter().getItem(position);
                fetchPlaceDetails(location.getPlace());
            }
        });

        if (mCurrentLatLng != null) {
            onLatLngFound(mCurrentLatLng);
        } else {
            LocationClientConnector locationClient = new LocationClientConnector(mActivity, this);
            locationClient.connect();
        }

        return v;
    }

    @Override
    public void onActivityAttached(Activity activity, Bundle args) {
        mActivity = activity;
        mNoLocation = mActivity.getResources().getString(NO_LOCATION);
        mSearching = mActivity.getResources().getString(SEARCHING_NEARBY);
        mHint = R.string.edit_text_add_a_location;

        mCurrentLatLng = args.getParcelable(LATLNG);
        if (mCurrentLatLng != null && args.getBoolean(FROM_IMAGE)) {
            mSearching = mActivity.getResources().getString(SEARCHING_IMAGE);
            mHint = R.string.edit_text_name_image_location_hint;
        }
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
    public void onNearestNamesSuggested(GpPlaceSearchResults results) {
        ArrayList<LocatedPlace> places = GpLocatedPlaceConverter.convert(results);
        mCurrentLatLngPlaces = new VhDataList<>();
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
}
