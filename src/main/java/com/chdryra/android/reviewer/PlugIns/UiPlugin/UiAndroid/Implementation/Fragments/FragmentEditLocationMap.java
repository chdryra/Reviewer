/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Fragments;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.database.MatrixCursor;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.Toast;

import com.chdryra.android.mygenerallibrary.Widgets.ClearableAutoCompleteTextView;
import com.chdryra.android.mygenerallibrary.Activities.FragmentDeleteDone;
import com.chdryra.android.mygenerallibrary.LocationUtils.LocationClientConnector;
import com.chdryra.android.reviewer.LocationServices.Implementation.StringAutoCompleterLocation;
import com.chdryra.android.mygenerallibrary.TextUtils.StringFilterAdapter;
import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.LocationServices.Interfaces.AddressesSuggester;
import com.chdryra.android.reviewer.LocationServices.Interfaces.LocatedPlace;
import com.chdryra.android.reviewer.LocationServices.Interfaces.LocationDetails;
import com.chdryra.android.reviewer.LocationServices.Interfaces.PlaceSearcher;
import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.Api.LocationServicesApi;
import com.chdryra.android.reviewer.LocationServices.Implementation.UserLocatedPlace;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.reviewer.R;
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

/**
 * Created by: Rizwan Choudrey
 * On: sometime...
 * Email: rizwan.choudrey@gmail.com
 */
public class FragmentEditLocationMap extends FragmentDeleteDone implements
        LocationClientConnector.Locatable,
        AddressesSuggester.AddressSuggestionsListener,
        PlaceSearcher.PlaceSearcherListener, OnMapReadyCallback {

    private static final String TAG = "FragmentEditLocationMap";
    private static final String LOCATION = TAG + ".location";

    private static final String NO_LOCATION = "no suggestions found...";
    private static final float ZOOM = 15;
    private static final int NUMBER_DEFAULT_NAMES = 5;

    private static final int NAME_LOCATION_HINT = R.string.edit_text_name_location_hint;
    private static final int SEARCH_HINT = R.string.search_view_location_hint;
    private static final int TOAST_SEARCH_FAILED = R.string.toast_map_search_failed;
    private static final int TOAST_ENTER_LOCATION = R.string.toast_enter_location;

    private static final int LAYOUT = R.layout.fragment_review_location_map_edit;
    private static final int LOCATION_NAME_EDIT_TEXT = R.id.edit_text_name_location;
    private static final int REVERT_TO_PASSED_LOCATION_BUTTON = R.id.revert_location_image_button;
    private static final int MAP_VIEW = R.id.mapView;

    private static final int MENU = R.menu.menu_search_delete_done;
    private static final int MENU_ITEM_DELETE = R.id.menu_item_delete;
    private static final int MENU_ITEM_DONE = R.id.menu_item_done;
    private static final int MENU_ITEM_SEARCH = R.id.menu_item_search;

    private GvLocation mCurrentLocation;
    private GoogleMap mGoogleMap;
    private MapView mMapView;
    private SearchView mSearchView;
    private MenuItem mSearchViewMenuItem;
    private ClearableAutoCompleteTextView mLocationName;
    private ImageButton mRevertButton;
    private LatLng mNewLatLng;
    private LocationClientConnector mLocationClient;
    private String mSearchLocationName;
    private StringAutoCompleterLocation mAutoCompleter;
    private StringFilterAdapter mSearchAdapter;

    private PlaceSearcher mPlaceSearcher;
    private AddressesSuggester mAddressSuggester;

    private LocationEditListener mListener;

    public interface LocationEditListener {
        void onDelete(GvLocation location, Intent returnResult);

        void onDone(GvLocation currentLocation, GvLocation newLocation, Intent returnResult);
    }

    public static FragmentEditLocationMap newInstance(@Nullable GvLocation location) {
        return FactoryFragment.newFragment(FragmentEditLocationMap.class, LOCATION, location);
    }

    //Searching
    public void handleSearch() {
        String query = getQuery();
        if (query == null || query.length() == 0) return;
        searchLocation(query);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) mCurrentLocation = args.getParcelable(LOCATION);

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
    public void onLocated(Location location) {
        setLatLng(new LatLng(location.getLatitude(), location.getLongitude()));
    }

    @Override
    public void onLocationClientConnected(Location location) {
        if (mNewLatLng == null) onLocated(location);
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
            makeToast(TOAST_ENTER_LOCATION);
            setDismissOnDone(false);
            return;
        }

        setDismissOnDone(true);
        mListener.onDone(mCurrentLocation, createNewLocation(), getNewReturnData());
    }

    @Override
    public void onSearchResultsFound(ArrayList<LocationDetails> results) {
        if (results.size() == 0) {
            makeToast(TOAST_SEARCH_FAILED);
            mLocationName.setHint(getResources().getString(NAME_LOCATION_HINT));
        } else {
            LocationDetails firstHit = results.get(0);
            setLatLng(firstHit.getLatLng());
            mLocationName.setText(firstHit.getName());
        }
    }

    @Override
    public void onAddressSuggestionsFound(ArrayList<LocatedPlace> suggestions) {
        ArrayList<String> addresses = new ArrayList<>();

        for (LocatedPlace suggestion : suggestions) {
            addresses.add(suggestion.getDescription());
        }

        if (addresses.size() == 0) {
            addresses.add(NO_LOCATION);
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
        mLocationClient = new LocationClientConnector(getActivity(), this);
        mLocationClient.connect();
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
        LocationServicesApi services
                = ApplicationInstance.getInstance(getActivity()).getLocationServices();
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

    private void initGoogleMapUi() {
        //TODO permissions
        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.setOnMyLocationButtonClickListener(newOnMyLocationButtonClickListener());
        mGoogleMap.setOnInfoWindowClickListener(newOnInfoWindowClickListener());
        mGoogleMap.setOnMarkerDragListener(newOnMarkerDragListener());
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
                setLatLng(marker.getPosition());
            }
        };
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
                setLatLng(mCurrentLocation.getLatLng());
                mLocationName.setText(mCurrentLocation.getName());
                mLocationName.hideChrome();
            }
        };
    }

    private GvLocation createNewLocation() {
        return new GvLocation(mNewLatLng, mLocationName.getText().toString().trim());
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
        ApplicationInstance app = ApplicationInstance.getInstance(getActivity());
        LocationServicesApi api = app.getLocationServices();
        mAutoCompleter = new StringAutoCompleterLocation(api.newAutoCompleter(new UserLocatedPlace(mNewLatLng)));
        mSearchAdapter = new StringFilterAdapter(getActivity(), null, mAutoCompleter);
        mSearchAdapter.registerDataSetObserver(new LocationSuggestionsObserver());
        mLocationName.setText(null);

        mAddressSuggester.fetchAddresses(mNewLatLng, NUMBER_DEFAULT_NAMES, this);
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

    private void makeToast(int messageId) {
        Toast.makeText(getActivity(), getResources().getString(messageId), Toast.LENGTH_SHORT)
                .show();
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
