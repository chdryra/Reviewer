/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Implementation;


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

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.mygenerallibrary.LocationServices.LocationAutoCompleter;
import com.chdryra.android.mygenerallibrary.LocationServices.LocatedPlace;
import com.chdryra.android.mygenerallibrary.LocationServices.LocationDetails;
import com.chdryra.android.mygenerallibrary.LocationServices.LocationDetailsFetcher;
import com.chdryra.android.mygenerallibrary.LocationServices.LocationId;
import com.chdryra.android.mygenerallibrary.LocationServices.LocationProvider;
import com.chdryra.android.mygenerallibrary.LocationServices.NearestPlacesSuggester;
import com.chdryra.android.mygenerallibrary.LocationServices.UserLocatedPlace;
import com.chdryra.android.mygenerallibrary.LocationUtils.LocationClientGoogle;
import com.chdryra.android.mygenerallibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.mygenerallibrary.Viewholder.VhDataList;
import com.chdryra.android.mygenerallibrary.Viewholder.VhQueryFilter;
import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolderAdapterFiltered;
import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolderDataList;
import com.chdryra.android.mygenerallibrary.Widgets.ClearableEditText;
import com.chdryra.android.reviewer.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.LocationServicesPlugin.Api.LocationServices;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Interfaces.GvDataAdder;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.NullLocatedPlace;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.VhdLocatedPlace;
import com.chdryra.android.reviewer.R;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by: Rizwan Choudrey
 * On: 04/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class AddLocation extends AddEditLayoutBasic<GvLocation>
        implements LocationClientGoogle.Locatable,
        NearestPlacesSuggester.NearestPlacesListener,
        LocationDetailsFetcher.LocationDetailsListener {
    private static final int LAYOUT = R.layout.dialog_location_add;
    private static final int NAME = R.id.location_add_edit_text;
    private static final int LIST = R.id.suggestions_list_view;

    public static final String LATLNG = TagKeyGenerator.getKey(AddLocation.class, "LatLng");
    public static final String FROM_IMAGE = TagKeyGenerator.getKey(AddLocation.class, "FromImage");

    private static final String NO_LOCATIONS = Strings.EditTexts.NO_SUGGESTIONS;
    private static final String SEARCHING_HERE = Strings.EditTexts.SEARCHING_NEAR_HERE;
    private static final String SEARCHING_PHOTO = Strings.EditTexts.SEARCHING_NEAR_PHOTO;

    private Context mContext;
    private LatLng mCurrentLatLng;
    private LatLng mSelectedLatLng;
    private ViewHolderAdapterFiltered mFilteredAdapter;

    private String mSearching;
    private String mHint;

    private VhdLocatedPlace mNoLocationMessage;
    private VhdLocatedPlace mSearchingMessage;

    private EditText mNameEditText;
    private ViewHolderDataList<VhdLocatedPlace> mCurrentLatLngPlaces;

    private final LocationServices mLocationServices;
    private final LocationDetailsFetcher mFetcher;
    private NearestPlacesSuggester mSuggester;
    private LocationAutoCompleter mAutoCompleter;

    private LocationDetails mDetails;

    public AddLocation(GvDataAdder adder, LocationServices locationServices) {
        super(GvLocation.class, new LayoutHolder(LAYOUT, NAME, LIST), NAME, adder);
        mLocationServices = locationServices;
        mFetcher = mLocationServices.newLocationDetailsFetcher();
    }

    private void fetchPlaceDetails(LocatedPlace place) {
        mNameEditText.setText(null);
        mNameEditText.setHint(R.string.edit_text_fetching_location_hint);
        mFetcher.fetchPlaceDetails(place, this);
    }

    private void findPlaceSuggestions() {
        //Initial suggestions
        mSuggester.fetchSuggestions(new UserLocatedPlace(mCurrentLatLng), this);

        //Whilst initial suggestions are being found....
        ViewHolderDataList<VhdLocatedPlace> message = new VhDataList<>();
        message.add(mSearchingMessage);
        setNewSuggestionsAdapter(message);
    }

    private void setNewSuggestionsAdapter(ViewHolderDataList<VhdLocatedPlace> names) {
        LocatedPlace place = new UserLocatedPlace(mCurrentLatLng);
        mAutoCompleter = mLocationServices.newAutoCompleter(place);
        mFilteredAdapter = new ViewHolderAdapterFiltered<>(mContext, names,
                new VhdLocatedPlace(new NullLocatedPlace()), new VhdLocatedPlaceFilter(mAutoCompleter));
        ((ListView) getView(LIST)).setAdapter(mFilteredAdapter);
    }

    private void onLatLngFound(LatLng latLng) {
        mCurrentLatLng = latLng;
        mSelectedLatLng = latLng;
        setMessages();
        findPlaceSuggestions();
    }

    private void setMessages() {
        mNoLocationMessage = new VhdLocatedPlace(new UserLocatedPlace(mCurrentLatLng, NO_LOCATIONS));
        mSearchingMessage = new VhdLocatedPlace(new UserLocatedPlace(mCurrentLatLng, mSearching));
    }

    @Override
    public GvLocation createGvDataFromInputs() {
        String name = ((EditText) getView(NAME)).getText().toString().trim();
        String address = mDetails != null ? mDetails.getAddress() : name;
        LocationId locationId = mDetails != null ?
                new LocationId(mDetails.getProvider(), mDetails.getId())
                : new LocationId(new LocationProvider(Strings.APP_NAME), mSelectedLatLng.toString());
        return new GvLocation(mSelectedLatLng, name, address, locationId);
    }

    @Override
    public void onAdd(GvLocation data) {
        super.onAdd(data);
        setNewSuggestionsAdapter(mCurrentLatLngPlaces);
    }

    @Override
    public void updateView(GvLocation location) {
        ((EditText) getView(NAME)).setText(location.getName());
    }

    @Override
    public View createLayoutUi(Context context, GvLocation data) {
        mContext = context;
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
            AppInstanceAndroid.getInstance(mContext).getGeolocation().newLocationClient().connect(this);
        }

        return v;
    }

    @Override
    public void onActivityAttached(Activity activity, Bundle args) {
        mSuggester = mLocationServices.newNearestPlacesSuggester();
        mCurrentLatLng = args.getParcelable(LATLNG);
        if (mCurrentLatLng != null && args.getBoolean(FROM_IMAGE)) {
            mSearching = SEARCHING_PHOTO;
            mHint = Strings.EditTexts.Hints.NAME_IMAGE_LOCATION;
        } else {
            mSearching = SEARCHING_HERE;
            mHint = Strings.EditTexts.Hints.ADD_LOCATION;
        }
    }

    @Override
    public void onLocated(Location location, CallbackMessage message) {
        if(message.isOk()) {
            onLatLngFound(new LatLng(location.getLatitude(), location.getLongitude()));
        }
    }

    @Override
    public void onConnected(Location location, CallbackMessage message) {
        onLocated(location, message);
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
    public void onNotPermissioned() {

    }

    @Override
    public void onPlaceDetailsFound(LocationDetails details) {
        mDetails = details;
        mSelectedLatLng = details.getLatLng();
        mNameEditText.setText(details.getName());
        mNameEditText.setHint(mHint);
    }

    @Override
    public void onActivityStopped() {
        mAutoCompleter.disconnectFromProvider();
        super.onActivityStopped();
    }

    private static class VhdLocatedPlaceFilter implements VhQueryFilter<VhdLocatedPlace> {
        private final LocationAutoCompleter mAutoCompleter;

        private VhdLocatedPlaceFilter(LocationAutoCompleter autoCompleter) {
            mAutoCompleter = autoCompleter;
        }

        @Override
        public VhDataList<VhdLocatedPlace> filter(String query) {
            List<LocatedPlace> filterResults = mAutoCompleter.filter(query);
            VhDataList<VhdLocatedPlace> list = new VhDataList<>();
            for(LocatedPlace place : filterResults) {
                list.add(new VhdLocatedPlace(place));
            }

            return list;
        }
    }
}
