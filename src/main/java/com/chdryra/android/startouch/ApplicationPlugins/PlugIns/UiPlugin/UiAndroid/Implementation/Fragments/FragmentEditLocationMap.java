/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Fragments;



import android.Manifest;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.DataSetObserver;
import android.database.MatrixCursor;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;

import com.chdryra.android.corelibrary.Activities.FragmentDeleteDone;
import com.chdryra.android.corelibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.corelibrary.LocationServices.LocationProvider;
import com.chdryra.android.corelibrary.LocationUtils.LocationClient;
import com.chdryra.android.corelibrary.LocationUtils.LocationClientGoogle;
import com.chdryra.android.corelibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.corelibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.corelibrary.TextUtils.StringFilterAdapter;
import com.chdryra.android.corelibrary.Widgets.ClearableAutoCompleteTextView;
import com.chdryra.android.startouch.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.corelibrary.Permissions.PermissionResult;
import com.chdryra.android.startouch.Application.Implementation.Strings;
import com.chdryra.android.startouch.Application.Interfaces.GeolocationSuite;
import com.chdryra.android.corelibrary.Permissions.PermissionsManager;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.LocationServicesPlugin.Api.LocationServices;
import com.chdryra.android.corelibrary.LocationServices.LocationId;
import com.chdryra.android.corelibrary.LocationServices.StringAutoCompleterLocation;
import com.chdryra.android.corelibrary.LocationServices.UserLocatedPlace;
import com.chdryra.android.corelibrary.LocationServices.AddressesSuggester;
import com.chdryra.android.corelibrary.LocationServices.LocatedPlace;
import com.chdryra.android.corelibrary.LocationServices.LocationDetails;
import com.chdryra.android.corelibrary.LocationServices.PlaceSearcher;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.startouch.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by: Rizwan Choudrey
 * On: sometime...
 * Email: rizwan.choudrey@gmail.com
 */
public class FragmentEditLocationMap extends FragmentDeleteDone implements
        LocationClientGoogle.Locatable,
        AddressesSuggester.AddressSuggestionsListener,
        PlaceSearcher.PlaceSearcherListener, OnMapReadyCallback,
        PermissionsManager.PermissionsCallback{

    private static final String TAG = TagKeyGenerator.getTag(FragmentEditLocationMap.class);
    private static final String LOCATION = TagKeyGenerator.getKey(FragmentEditLocationMap.class, "Location");
    private static final int PERMISSION_REQUEST = RequestCodeGenerator.getCode(FragmentEditLocationMap.class);

    private static final String ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;

    private static final int NO_LOCATION = R.string.dialog_location_no_suggestions;
    private static final float ZOOM = 15;
    private static final int NUMBER_DEFAULT_NAMES = 5;

    private static final int NAME_LOCATION_HINT = R.string.edit_text_name_location_hint;
    private static final int SEARCH_HINT = R.string.search_view_location_hint;

    private static final int LAYOUT = R.layout.fragment_review_location_map_edit;
    private static final int LOCATION_NAME_EDIT_TEXT = R.id.edit_text_name_location;
    private static final int REVERT_TO_PASSED_LOCATION_BUTTON = R.id.revert_location_image_button;
    private static final int MAP_VIEW = R.id.mapView;

    private static final int MENU = R.menu.menu_search_delete_done;
    private static final int MENU_ITEM_DELETE = R.id.menu_item_delete;
    private static final int MENU_ITEM_DONE = R.id.menu_item_done;
    private static final int MENU_ITEM_SEARCH = R.id.menu_item_search;
    private static final PermissionsManager.Permission LOCATION_PERMISSION = PermissionsManager.Permission
            .LOCATION;

    private GvLocation mCurrentLocation;
    private GoogleMap mGoogleMap;
    private MapView mMapView;
    private SearchView mSearchView;
    private MenuItem mSearchViewMenuItem;
    private ClearableAutoCompleteTextView mLocationName;
    private ImageButton mRevertButton;
    private LatLng mNewLatLng;
    private LocationClient mLocationClient;
    private String mSearchLocationName;
    private StringAutoCompleterLocation mAutoCompleter;
    private StringFilterAdapter mSearchAdapter;

    private PlaceSearcher mPlaceSearcher;
    private AddressesSuggester mAddressSuggester;

    private LocationEditListener mListener;
    private LocationDetails mDetails;
    private String mMarkerAddress;
    private LocationId mLocationId;

    public interface LocationEditListener {
        void onDelete(GvLocation location, Intent returnResult);

        void onDone(GvLocation currentLocation, GvLocation newLocation, Intent returnResult);
    }

    public static FragmentEditLocationMap newInstance(@Nullable GvLocation location) {
        return FactoryFragment.newFragment(FragmentEditLocationMap.class, LOCATION, location);
    }

    //Searching
    public void handleSearch() {
        AppInstanceAndroid app = AppInstanceAndroid.getInstance(getActivity());
        if(!app.getNetwork().isOnline(app.getUi().getCurrentScreen())) return;

        String query = getQuery();
        if (query == null || query.length() == 0) return;
        searchLocation(query);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mCurrentLocation = args.getParcelable(LOCATION);
            if(mCurrentLocation != null) {
                mMarkerAddress = mCurrentLocation.getAddress();
                mLocationId = mCurrentLocation.getLocationId();
            }
        }

        extractListener();
        setLocationServices();
        MapsInitializer.initialize(getActivity());
        setDeleteWhatTitle(GvLocation.TYPE.getDatumName());
        dismissOnDelete();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = extractViews(inflater, container);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);

        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        initUi();
    }

    @Override
    public void onLocated(Location location, CallbackMessage message) {
        if(message.isOk()) {
            LatLng latlang = new LatLng(location.getLatitude(), location.getLongitude());
            fetchMarkerAddress(latlang);
        }
    }

    @Override
    public void onConnected(Location location, CallbackMessage message) {
        if (mNewLatLng == null) onLocated(location, message);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(MENU, menu);

        mSearchViewMenuItem = menu.findItem(MENU_ITEM_SEARCH);
        mSearchView = (SearchView) mSearchViewMenuItem.getActionView();

        initSearchView(menu.findItem(MENU_ITEM_DELETE), menu.findItem(MENU_ITEM_DONE));
    }

    @Override
    protected boolean hasDataToDelete() {
        return mCurrentLocation != null;
    }

    @Override
    protected void onDeleteSelected() {
        mListener.onDelete(mCurrentLocation, getNewReturnData());
    }

    @Override
    protected void onDoneSelected() {
        if (mLocationName == null || mLocationName.length() == 0) {
            makeToast(Strings.Toasts.ENTER_LOCATION);
            setDismissOnDone(false);
            return;
        }

        setDismissOnDone(true);
        mListener.onDone(mCurrentLocation, createNewLocation(), getNewReturnData());
    }

    @Override
    public void onSearchResultsFound(ArrayList<LocationDetails> results) {
        if (results.size() == 0) {
            makeToast(Strings.Toasts.MAP_SEARCH_FAILED);
            mLocationName.setHint(getResources().getString(NAME_LOCATION_HINT));
        } else {
            mDetails = results.get(0);
            mMarkerAddress = mDetails.getAddress();
            mLocationId = new LocationId(mDetails.getProvider(), mDetails.getId());
            mLocationName.setText(mDetails.getName());
            setLatLng(mDetails.getLatLng());
        }
    }

    @Override
    public void onAddressSuggestionsFound(ArrayList<LocatedPlace> suggestions) {
        ArrayList<String> addresses = new ArrayList<>();

        for (LocatedPlace suggestion : suggestions) {
            addresses.add(suggestion.getDescription());
        }

        if (addresses.size() == 0) {
            addresses.add(getString(NO_LOCATION));
        } else if (mSearchLocationName != null) {
            addresses.add(0, mSearchLocationName);
        }

        //If done pressed and activity finished whilst this is being done.
        if (getActivity() != null) {
            mLocationName.setAdapter(new StringFilterAdapter(getActivity(), addresses,
                    mAutoCompleter));
        }
    }

    //Lifecycle methods
    @Override
    public void onStart() {
        super.onStart();
        mLocationClient = getLocationServices().newLocationClient();
        mLocationClient.connect(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mMapView != null) {
            mMapView.onResume();
        }
    }

    @Override
    public void onSaveInstanceState(@NotNull Bundle outState) {
        if (mMapView != null) {
            mMapView.onSaveInstanceState(outState);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mMapView != null) mMapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mLocationClient.disconnect();
    }

    @Override
    public void onLowMemory() {
        if (mMapView != null) mMapView.onLowMemory();
        super.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocationClient.disconnect();
        if (mMapView != null) mMapView.onDestroy();
    }

    @Nullable
    private String getQuery() {
        Intent intent = getActivity().getIntent();
        String query = null;
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            query = intent.getStringExtra(SearchManager.QUERY);
        } else if (Intent.ACTION_PICK.equals(intent.getAction())) {
            query = intent.getData().getLastPathSegment();
        }

        return query;
    }

    private void setLocationServices() {
        LocationServices services = getLocationServices().getLocationServices();
        mPlaceSearcher = services.newPlaceSearcher();
        mAddressSuggester = services.newAddressesSuggester();
    }

    private void extractListener() {
        try {
            mListener = (LocationEditListener) getActivity();
        } catch (ClassCastException e) {
            throw new RuntimeException("Activity must be a LocationEditListener!");
        }
    }

    @NonNull
    private View extractViews(LayoutInflater inflater, ViewGroup container) {
        View v = inflater.inflate(LAYOUT, container, false);

        mLocationName = (ClearableAutoCompleteTextView) v.findViewById(LOCATION_NAME_EDIT_TEXT);
        mRevertButton = (ImageButton) v.findViewById(REVERT_TO_PASSED_LOCATION_BUTTON);
        mMapView = (MapView) v.findViewById(MAP_VIEW);
        return v;
    }

    private void initSearchView(MenuItem deleteIcon, MenuItem doneIcon) {
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context
                .SEARCH_SERVICE);

        SearchableInfo info = searchManager.getSearchableInfo(getActivity().getComponentName());
        mSearchView.setSearchableInfo(info);
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setQueryHint(getResources().getString(SEARCH_HINT));
        mSearchView.setOnQueryTextFocusChangeListener(newIconsOrSearchViewListener(deleteIcon,
                doneIcon));
        mSearchView.setOnQueryTextListener(newFilterOnTextChangeListener());
    }

    private void initUi() {
        initLocationNameUi();
        initGoogleMapUi();
        initRevertButtonUi();
    }

    private void initLocationNameUi() {
        mLocationName.addTextChangedListener(newLocationNameTextWatcher());
    }

    @Override
    public void onPermissionsResult(int requestCode, List<PermissionResult> results) {
        if(requestCode == PERMISSION_REQUEST
                && results.size() == 1
                && results.get(0).isGranted(LOCATION_PERMISSION)) {
            enableMyLocation();
        }
        setMapListeners();
    }

    private void initGoogleMapUi() {
        AppInstanceAndroid app = AppInstanceAndroid.getInstance(getActivity());
        PermissionsManager permissions = app.getPermissions();
        if(permissions.hasPermissions(LOCATION_PERMISSION)) {
            enableMyLocation();
            setMapListeners();
        } else {
            permissions.requestPermissions(PERMISSION_REQUEST, this, LOCATION_PERMISSION);
        }
    }

    private void setMapListeners() {
        mGoogleMap.setOnInfoWindowClickListener(newOnInfoWindowClickListener());
        mGoogleMap.setOnMarkerDragListener(newOnMarkerDragListener());
    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(getActivity(), ACCESS_COARSE_LOCATION )
                == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getActivity(), ACCESS_FINE_LOCATION )
                        == PackageManager.PERMISSION_GRANTED) {
            mGoogleMap.setMyLocationEnabled(true);
            mGoogleMap.setOnMyLocationButtonClickListener(newOnMyLocationButtonClickListener());
        }
    }

    private void initRevertButtonUi() {
        if (mCurrentLocation != null) {
            mRevertButton.setOnClickListener(newRevertButtonClickListener());
            mRevertButton.performClick();
        } else {
            mRevertButton.setVisibility(View.GONE);
        }
    }

    @NonNull
    private GoogleMap.OnMarkerDragListener newOnMarkerDragListener() {
        return new GoogleMap.OnMarkerDragListener() {

            @Override
            public void onMarkerDragStart(Marker marker) {
            }

            @Override
            public void onMarkerDrag(Marker arg0) {
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                LatLng position = marker.getPosition();
                fetchMarkerAddress(position);
            }
        };
    }

    private void fetchMarkerAddress(final LatLng position) {
        mMarkerAddress = null;
        mLocationId = null;
        mAddressSuggester.fetchAddresses(position, 1,
                new AddressesSuggester.AddressSuggestionsListener() {
            @Override
            public void onAddressSuggestionsFound(ArrayList<LocatedPlace> addresses) {
                if(addresses.size() > 0) {
                    LocatedPlace place = addresses.get(0);
                    mMarkerAddress = place.getDescription();
                    mLocationId = place.getId();
                }
                setLatLng(position);
            }
        });
    }

    @NonNull
    private GoogleMap.OnInfoWindowClickListener newOnInfoWindowClickListener() {
        return new GoogleMap.OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker marker) {
                marker.hideInfoWindow();
            }
        };
    }

    @NonNull
    private GoogleMap.OnMyLocationButtonClickListener newOnMyLocationButtonClickListener() {
        return new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                mLocationClient.locate();
                return false;
            }
        };
    }

    @NonNull
    private TextWatcher newLocationNameTextWatcher() {
        return new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                updateMapMarker();
            }
        };
    }

    @NonNull
    private View.OnClickListener newRevertButtonClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearchLocationName = null;
                mMarkerAddress = mCurrentLocation.getAddress();
                setLatLng(mCurrentLocation.getLatLng());
                mLocationName.setText(mCurrentLocation.getName());
                mLocationId = mCurrentLocation.getLocationId();
                mLocationName.hideChrome();
            }
        };
    }

    private GvLocation createNewLocation() {
        String name = mLocationName.getText().toString().trim();
        String address = name;
        LocationId id;
        if(mCurrentLocation != null && mNewLatLng.equals(mCurrentLocation.getLatLng())) {
            address = mCurrentLocation.getAddress();
            id = mCurrentLocation.getLocationId();
        } else if(mDetails != null){
            address = mDetails.getAddress();
            id = new LocationId(mDetails.getProvider(), mDetails.getId());
        } else {
            if(mMarkerAddress != null) address = mMarkerAddress;
            id = mLocationId != null ? mLocationId : new LocationId(new LocationProvider(Strings.APP_NAME), mNewLatLng.toString());
        }

        return new GvLocation(mNewLatLng, name, address, id);
    }

    private void setLatLng(LatLng latlang) {
        mNewLatLng = latlang;
        updateSuggestionAdapters();
        zoomToLatLng();
    }

    private void zoomToLatLng() {
        if (mNewLatLng == null) return;
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mNewLatLng, ZOOM));
        updateMapMarker();
    }

    private void updateMapMarker() {
        MarkerOptions markerOptions = new MarkerOptions().position(mNewLatLng);
        markerOptions.title(mLocationName.getText().toString());
        if(mMarkerAddress != null) markerOptions.snippet(mMarkerAddress);
        markerOptions.draggable(true);
        mGoogleMap.clear();
        Marker marker = mGoogleMap.addMarker(markerOptions);
        marker.showInfoWindow();
    }

    private void searchLocation(String query) {
        mSearchLocationName = query;
        mPlaceSearcher.searchQuery(query, mNewLatLng, this);
        setSearchViewToSearching();
    }

    private void setSearchViewToSearching() {
        mSearchView.setQuery(null, false);
        mSearchViewMenuItem.collapseActionView();
        mLocationName.setHint(getResources().getString(R.string
                .progress_bar_search_location_title));
    }

    //Search suggestions
    private void invalidateSuggestions() {
        mSearchView.setSuggestionsAdapter(null);
    }

    private void updateSuggestionAdapters() {
        LocationServices api = getLocationServices().getLocationServices();
        mAutoCompleter = new StringAutoCompleterLocation(api.newAutoCompleter(new UserLocatedPlace(mNewLatLng)));
        mSearchAdapter = new StringFilterAdapter(getActivity(), new ArrayList<String>(), mAutoCompleter);
        mSearchAdapter.registerDataSetObserver(new LocationSuggestionsObserver());
        mLocationName.setText(null);

        mAddressSuggester.fetchAddresses(mNewLatLng, NUMBER_DEFAULT_NAMES, this);
    }

    private GeolocationSuite getLocationServices() {
        return AppInstanceAndroid.getInstance(getActivity()).getGeolocation();
    }

    @NonNull
    private View.OnFocusChangeListener newIconsOrSearchViewListener(final MenuItem deleteIcon,
                                                                    final MenuItem doneIcon) {
        return new View.OnFocusChangeListener() {
            //Overridden
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    deleteIcon.setVisible(false);
                    doneIcon.setVisible(false);
                } else {
                    deleteIcon.setVisible(true);
                    doneIcon.setVisible(true);
                    mSearchViewMenuItem.collapseActionView();
                }
            }
        };
    }

    @NonNull
    private SearchView.OnQueryTextListener newFilterOnTextChangeListener() {
        return new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() > 0 && mSearchAdapter != null) {
                    mSearchAdapter.filter(newText);
                } else {
                    invalidateSuggestions();
                }

                return false;
            }
        };
    }

    private void makeToast(String toast) {
        AppInstanceAndroid.getInstance(getActivity()).getUi().getCurrentScreen().showToast(toast);
    }

    private class LocationSuggestionsObserver extends DataSetObserver {
        @Override
        public void onChanged() {
            mSearchView.setSuggestionsAdapter(getSuggestionsCursorAdapter());
        }

        @Override
        public void onInvalidated() {
            invalidateSuggestions();
        }

        private CursorAdapter getSuggestionsCursorAdapter() {
            //TODO More efficient way of constructing an appropriate Suggestions Cursor Adapter.
            //For some inexplicable reason SearchView only accepts CursorAdapters for suggestions.
            //Place name suggestions are fetched using the ArrayAdapter LocationNameAdapter.
            //This is a (clunky) way of taking suggestions from LocationNameAdapter and putting in a
            // CursorAdapter.
            String[] columnNames = {"_id", SearchManager.SUGGEST_COLUMN_INTENT_DATA};
            String[] suggestion_row = new String[columnNames.length];
            MatrixCursor suggestions_cursor = new MatrixCursor(columnNames);
            for (int i = 0; i < mSearchAdapter.getCount(); ++i) {
                suggestion_row[0] = String.valueOf(mSearchAdapter.getItemId(i));
                suggestion_row[1] = mSearchAdapter.getItem(i);
                suggestions_cursor.addRow(suggestion_row);
                Log.i(TAG, suggestion_row[0] + ", " + suggestion_row[1]);
            }

            String[] from = {SearchManager.SUGGEST_COLUMN_INTENT_DATA};
            int[] to = {android.R.id.text1};
            return new SimpleCursorAdapter(getActivity(), android.R.layout.simple_list_item_1,
                    suggestions_cursor, from, to, 0);
        }
    }

    @Override
    public void onNotPermissioned() {

    }

}
