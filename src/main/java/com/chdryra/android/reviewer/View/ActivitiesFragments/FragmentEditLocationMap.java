/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.View.ActivitiesFragments;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.database.MatrixCursor;
import android.location.Location;
import android.os.Bundle;
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

import com.chdryra.android.myandroidwidgets.ClearableAutoCompleteTextView;
import com.chdryra.android.mygenerallibrary.FragmentDeleteDone;
import com.chdryra.android.mygenerallibrary.LocationClientConnector;
import com.chdryra.android.mygenerallibrary.PlaceAutoCompleteSuggester;
import com.chdryra.android.mygenerallibrary.PlaceSuggester;
import com.chdryra.android.mygenerallibrary.StringFilterAdapter;
import com.chdryra.android.remoteapifetchers.GpPlaceSearchResults;
import com.chdryra.android.remoteapifetchers.GpPlaceSearcher;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.Utils.GpLocatedPlaceConverter;
import com.chdryra.android.reviewer.Utils.LocatedPlace;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvLocation;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation.GvDataPacker;
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
 * UI Fragment: location map. Google Map of location passed in the arguments, or current location
 * if null.
 * <p/>
 * <p>
 * In addition:
 * <ul>
 * <li>Text entry for location name. Autocompletes with nearby suggestions.</li>
 * <li>Search icon in ActionBar to perform search.</li>
 * <li>If location passed to Fragment, then also a revert button to revert back to
 * passed location.</li>
 * </ul>
 * </p>
 */
public class FragmentEditLocationMap extends FragmentDeleteDone implements
        LocationClientConnector.Locatable,
        PlaceSuggester.SuggestionsListener,
        GpPlaceSearcher.SearchListener, OnMapReadyCallback {
    private final static String LOCATION = "com.chdryra.android.reviewer.View.ActivitiesFragments.FragmentEditLocationMap.location";
    private final static String TAG = "FragmentEditLocationMap";
    private static final String NO_LOCATION = "no suggestions found...";
    private static final float DEFAULT_ZOOM = 15;
    private static final int NUMBER_DEFAULT_NAMES = 5;

    private GvLocation mCurrentLocation;
    private GoogleMap mGoogleMap;
    private MapView mMapView;

    private SearchView mSearchView;
    private MenuItem mSearchViewMenuItem;
    private StringFilterAdapter mSearchAdapter;

    private ClearableAutoCompleteTextView mLocationName;
    private ImageButton mRevertButton;

    private LatLng mNewLatLng;

    private LocationClientConnector mLocationClient;
    private String mSearchLocationName;

    private PlaceAutoCompleteSuggester mAutoCompleter;

    private GvDataPacker<GvLocation> mDataPacker;

    public static FragmentEditLocationMap newInstance(GvLocation location) {
        return FactoryFragment.newFragment(FragmentEditLocationMap.class, LOCATION, location);
    }

    public void handleSearch() {
        Intent intent = getActivity().getIntent();
        String query;
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            query = intent.getStringExtra(SearchManager.QUERY);
        } else if (Intent.ACTION_PICK.equals(intent.getAction())) {
            query = intent.getData().getLastPathSegment();
        } else {
            return;
        }

        searchLocation(query);
    }

    //private methods
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

    private GvLocation createGvData() {
        String name = mLocationName.getText().toString().trim();
        return new GvLocation(mNewLatLng, name);
    }

    private void invalidateSuggestions() {
        mSearchView.setSuggestionsAdapter(null);
    }

    private void initUI() {
        initLocationNameUI();
        initGoogleMapUI();
        initRevertButtonUI();
    }

    private void initGoogleMapUI() {
        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.setOnMyLocationButtonClickListener(new GoogleMap
                .OnMyLocationButtonClickListener() {
            //Overridden
            @Override
            public boolean onMyLocationButtonClick() {
                mLocationClient.locate();
                return false;
            }
        });
        mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

            //Overridden
            @Override
            public void onInfoWindowClick(Marker marker) {
                marker.hideInfoWindow();
            }
        });
        mGoogleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {

            //Overridden
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
        });
    }

    private void initLocationNameUI() {
        mLocationName.addTextChangedListener(new TextWatcher() {

            //Overridden
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
        });
    }

    private void initRevertButtonUI() {
        if (mCurrentLocation != null) {
            mRevertButton.setOnClickListener(new View.OnClickListener() {
                //Overridden
                @Override
                public void onClick(View v) {
                    mSearchLocationName = null;
                    setLatLng(mCurrentLocation.getLatLng());
                    mLocationName.setText(mCurrentLocation.getName());
                    mLocationName.hideChrome();
                }
            });
            mRevertButton.performClick();
        } else {
            mRevertButton.setVisibility(View.GONE);
        }
    }

    private void searchLocation(String query) {
        mSearchLocationName = query;
        mSearchView.setQuery(null, false);
        mSearchViewMenuItem.collapseActionView();
        mLocationName.setHint(getResources().getString(R.string
                .progress_bar_search_location_title));
        GpPlaceSearcher searcher = new GpPlaceSearcher(query, this);
        searcher.fetchResults();
    }

    private void setLatLng(LatLng latlang) {
        mNewLatLng = latlang;
        updateSuggestionAdapters();
        zoomToLatLng();
    }

    private void updateSuggestionAdapters() {
        mAutoCompleter = new PlaceAutoCompleteSuggester(mNewLatLng);
        PlaceSuggester suggestions = new PlaceSuggester(getActivity(), mNewLatLng, this);

        mSearchAdapter = new StringFilterAdapter(getActivity(), null, mAutoCompleter);
        mSearchAdapter.registerDataSetObserver(new LocationSuggestionsObserver());

        mLocationName.setText(null);
        suggestions.getSuggestions(NUMBER_DEFAULT_NAMES);
    }

    private void zoomToLatLng() {
        if (mNewLatLng == null) return;
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mNewLatLng, DEFAULT_ZOOM));
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

    //Overridden
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        initUI();
    }

    @Override
    protected boolean hasDataToDelete() {
        return mCurrentLocation != null;
    }

    @Override
    protected void onDeleteSelected() {
        mDataPacker.packItem(GvDataPacker.CurrentNewDatum.CURRENT, mCurrentLocation, getNewReturnData());
    }

    @Override
    protected void onDoneSelected() {
        if (mLocationName.length() == 0) {
            Toast.makeText(getActivity(), R.string.toast_enter_location, Toast.LENGTH_SHORT).show();
            setDismissOnDone(false);
            return;
        } else {
            setDismissOnDone(true);
        }

        Intent i = getNewReturnData();
        mDataPacker.packItem(GvDataPacker.CurrentNewDatum.CURRENT, mCurrentLocation, i);
        mDataPacker.packItem(GvDataPacker.CurrentNewDatum.NEW, createGvData(), i);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataPacker = new GvDataPacker<>();
        Bundle args = getArguments();
        if (args != null) mCurrentLocation = args.getParcelable(LOCATION);
        MapsInitializer.initialize(getActivity());
        setDeleteWhatTitle(GvLocation.TYPE.getDatumName());
        dismissOnDelete();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_review_location_map_edit, container, false);

        mLocationName = (ClearableAutoCompleteTextView) v.findViewById(R.id
                .edit_text_name_location);
        mRevertButton = (ImageButton) v.findViewById(R.id.revert_location_image_button);
        mMapView = (MapView) v.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search_delete_done, menu);

        final MenuItem deleteIcon = menu.findItem(R.id.menu_item_delete);
        final MenuItem doneIcon = menu.findItem(R.id.menu_item_done);
        mSearchViewMenuItem = menu.findItem(R.id.menu_item_search);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context
                .SEARCH_SERVICE);

        mSearchView = (SearchView) mSearchViewMenuItem.getActionView();
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity()
                .getComponentName()));
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setQueryHint(getResources().getString(R.string.search_view_location_hint));
        mSearchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
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
        });

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //Overridden
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
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof Activity) {
            mLocationClient = new LocationClientConnector((Activity)context, this);
            mLocationClient.connect();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
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
        if (mMapView != null) {
            mMapView.onPause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mLocationClient.disconnect();
    }

    @Override
    public void onLowMemory() {
        if (mMapView != null) {
            mMapView.onLowMemory();
        }
        super.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocationClient.disconnect();
        if (mMapView != null) {
            mMapView.onDestroy();
        }
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
    public void onSuggestionsFound(ArrayList<String> addresses) {
        if (addresses == null) addresses = new ArrayList<>();

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

    @Override
    public void onSearchResultsFound(GpPlaceSearchResults results) {
        ArrayList<LocatedPlace> places = GpLocatedPlaceConverter.convert(results);
        if (places.size() == 0) {
            Toast.makeText(getActivity(), getResources().getString(R.string
                    .toast_map_search_failed), Toast.LENGTH_SHORT).show();
        } else {
            setLatLng(places.get(0).getLatLng());
        }
        mLocationName.setHint(getResources().getString(R.string.edit_text_name_location_hint));
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

    }
}
