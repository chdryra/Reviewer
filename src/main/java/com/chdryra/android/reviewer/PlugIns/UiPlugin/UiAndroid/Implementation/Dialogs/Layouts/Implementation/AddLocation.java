/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Implementation;

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
import com.chdryra.android.reviewer.LocationServices.Interfaces.LocationDetailsFetcher;
import com.chdryra.android.reviewer.LocationServices.Interfaces.NearestPlacesSuggester;
import com.chdryra.android.reviewer.LocationServices.Interfaces.ReviewerLocationServices;
import com.chdryra.android.reviewer.LocationServices.Interfaces.LocatedPlace;
import com.chdryra.android.reviewer.LocationServices.Interfaces.LocationDetails;
import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.LocationServicesGoogle.Implementation.GooglePlace;
import com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Interfaces.GvDataAdder;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.VhdLocatedPlace;
import com.chdryra.android.reviewer.R;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 04/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class AddLocation extends AddEditLayoutBasic<GvLocation>
        implements LocationClientConnector.Locatable,
        NearestPlacesSuggester.NearestPlacesListener,
        LocationDetailsFetcher.LocationDetailsListener {
    public static final int LAYOUT = R.layout.dialog_location_add;
    public static final int NAME = R.id.location_add_edit_text;
    public static final int LIST = R.id.suggestions_list_view;

    public static final String LATLNG = "com.chdryra.android.reviewer.latlng";
    public static final String FROM_IMAGE = "com.chdryra.android.reviewer.from_image";

    private static final int SEARCHING_NEARBY = R.string.edit_text_searching_near_here;
    private static final int SEARCHING_IMAGE = R.string.edit_text_searching_near_image;
    private static final int NO_LOCATION = R.string.edit_text_no_suggestions;

    private Activity mActivity;
    private LatLng mCurrentLatLng;
    private LatLng mSelectedLatLng;
    private ViewHolderAdapterFiltered mFilteredAdapter;

    private String mNoLocation;
    private String mSearching;
    private int mHint;

    private VhdLocatedPlace mNoLocationMessage;
    private VhdLocatedPlace mSearchingMessage;

    private EditText mNameEditText;
    private ViewHolderDataList<VhdLocatedPlace> mCurrentLatLngPlaces;

    private ReviewerLocationServices mLocationServices;
    private LocationDetailsFetcher mFetcher;
    private NearestPlacesSuggester mSuggester;

    public AddLocation(GvDataAdder adder, ReviewerLocationServices locationServices) {
        super(GvLocation.class, new LayoutHolder(LAYOUT, NAME, LIST), NAME, adder);
        mLocationServices = locationServices;
        mFetcher = mLocationServices.newLocationDetailsFetcher();
        mSuggester = mLocationServices.newNearestPlacesSuggester();
    }

    private void fetchPlaceDetails(LocatedPlace place) {
        mNameEditText.setText(null);
        mNameEditText.setHint(R.string.edit_text_fetching_location_hint);
        mFetcher.fetchPlaceDetails(place, this);
    }

    private void findPlaceSuggestions() {
        //Initial suggestions
        mSuggester.fetchSuggestions(new GooglePlace(mCurrentLatLng), this);

        //Whilst initial suggestions are being found....
        ViewHolderDataList<VhdLocatedPlace> message = new VhDataList<>();
        message.add(mSearchingMessage);
        setNewSuggestionsAdapter(message);
    }

    private void setNewSuggestionsAdapter(ViewHolderDataList<VhdLocatedPlace> names) {
        LocatedPlace place = new GooglePlace(mCurrentLatLng);
        ViewHolderAdapterFiltered.QueryFilter filter = mLocationServices.newAutoCompleter(place);
        mFilteredAdapter = new ViewHolderAdapterFiltered(mActivity, names, filter);
        ((ListView) getView(LIST)).setAdapter(mFilteredAdapter);
    }

    private void onLatLngFound(LatLng latLng) {
        mCurrentLatLng = latLng;
        mSelectedLatLng = latLng;
        setMessages();
        findPlaceSuggestions();
    }

    private void setMessages() {
        mNoLocationMessage = new VhdLocatedPlace(new GooglePlace(mCurrentLatLng, mNoLocation));
        mSearchingMessage = new VhdLocatedPlace(new GooglePlace(mCurrentLatLng, mSearching));
    }

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
    public void onNearestPlacesFound(ArrayList<LocatedPlace> suggestions) {
        mCurrentLatLngPlaces = new VhDataList<>();
        if (suggestions.size() == 0) {
            mCurrentLatLngPlaces.add(mNoLocationMessage);
        } else {
            for (LocatedPlace suggestion : suggestions) {
                mCurrentLatLngPlaces.add(new VhdLocatedPlace(suggestion));
            }
        }

        setNewSuggestionsAdapter(mCurrentLatLngPlaces);
    }

    @Override
    public void onPlaceDetailsFound(LocationDetails details) {
        mSelectedLatLng = details.getLatLng();
        mNameEditText.setText(details.getDescription());
        mNameEditText.setHint(mHint);
    }
}
