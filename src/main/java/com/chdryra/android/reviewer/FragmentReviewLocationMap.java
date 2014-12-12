/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.database.MatrixCursor;
import android.os.AsyncTask;
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
import com.chdryra.android.remoteapifetchers.FetcherPlacesAPI;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

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
public class FragmentReviewLocationMap extends FragmentDeleteDone implements
        LocationClientConnector.Locatable, PlaceSuggester.SuggestionsListener {
    private final static String TAG                  = "FragmentReviewLocationMap";
    private static final String NO_LOCATION          = "no suggestions found...";
    private static final float  DEFAULT_ZOOM         = 15;
    private static final int    NUMBER_DEFAULT_NAMES = 5;

    private GvLocationList.GvLocation mCurrent;
    private GoogleMap                 mGoogleMap;
    private MapView                   mMapView;

    private SearchView          mSearchView;
    private MenuItem            mSearchViewMenuItem;
    private StringFilterAdapter mSearchAdapter;

    private ClearableAutoCompleteTextView mLocationName;
    private ImageButton                   mRevertButton;

    private LatLng mNewLatLng;

    private LocationClientConnector mLocationClient;
    private String                  mSearchLocationName;

    private PlaceAutoCompleteSuggester mAutoCompleter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = LauncherUI.getArgsForActivity(getActivity());
        if (args != null) {
            mCurrent = (GvLocationList.GvLocation) InputHandlerGvData.unpackItem(InputHandlerGvData
                    .CurrentNewDatum.CURRENT, args);
        }

        MapsInitializer.initialize(getActivity());

        setDeleteWhatTitle(GvLocationList.GVTYPE.getDatumString());
        dismissOnDelete();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_review_location_map, container, false);

        mMapView = (MapView) v.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mGoogleMap = ((MapView) v.findViewById(R.id.mapView)).getMap();
        mLocationName = (ClearableAutoCompleteTextView) v.findViewById(R.id
                .edit_text_name_location);
        mRevertButton = (ImageButton) v.findViewById(R.id.revert_location_image_button);

        initUI();

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
    protected boolean hasDataToDelete() {
        return mCurrent != null;
    }

    @Override
    protected void onDeleteSelected() {
        InputHandlerGvData.packItem(InputHandlerGvData.CurrentNewDatum.CURRENT, mCurrent,
                getNewReturnData());
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
        InputHandlerGvData.packItem(InputHandlerGvData.CurrentNewDatum.CURRENT, mCurrent, i);
        InputHandlerGvData.packItem(InputHandlerGvData.CurrentNewDatum.NEW, createGVData(), i);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mLocationClient = new LocationClientConnector(activity, this);
        mLocationClient.connect();
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

        gotoSearchLocation(query);
    }

    @Override
    public void onLocated(LatLng latLng) {
        setLatLng(latLng);
    }

    @Override
    public void onLocationClientConnected(LatLng latLng) {
        if (mNewLatLng == null) onLocated(latLng);
    }

    @Override
    public void onSuggestionsFound(ArrayList<String> addresses) {
        if (addresses.size() == 0) {
            addresses.add(NO_LOCATION);
        } else if (mSearchLocationName != null) {
            addresses.add(0, mSearchLocationName);
        }

        mLocationName.setAdapter(new StringFilterAdapter(getActivity(), addresses,
                mAutoCompleter));
    }

    private GvLocationList.GvLocation createGVData() {
        return new GvLocationList.GvLocation(mNewLatLng, mLocationName.getText().toString().trim());
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
            @Override
            public boolean onMyLocationButtonClick() {
                mLocationClient.locate();
                return false;
            }
        });
        mGoogleMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker marker) {
                marker.hideInfoWindow();
            }
        });
        mGoogleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {

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

        //mGoogleMap.setInfoWindowAdapter(new InfoWindowAdapterRated());
    }

    private void initLocationNameUI() {
        mLocationName.addTextChangedListener(new TextWatcher() {

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
        if (mCurrent != null) {
            mRevertButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSearchLocationName = null;
                    setLatLng(mCurrent.getLatLng());
                    mLocationName.setText(mCurrent.getName());
                    mLocationName.hideChrome();
                }
            });
            mRevertButton.performClick();
        } else {
            mRevertButton.setVisibility(View.GONE);
        }
    }

    private void gotoSearchLocation(String query) {
        mSearchLocationName = query;
        new MapSearchTask().execute(mSearchLocationName);
        mSearchView.setQuery(null, false);
        mSearchViewMenuItem.collapseActionView();
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

    /**
     * Fragment performs search on a separate thread using this task.
     */
    private class MapSearchTask extends AsyncTask<String, Void, LatLng> {
        private ProgressDialog pd;

        @Override
        protected LatLng doInBackground(String... params) {
            try {
                return FetcherPlacesAPI.fetchLatLng(params[0]);
            } catch (JSONException e) {
                Toast.makeText(getActivity(), getResources().getString(R.string
                        .toast_map_search_failed), Toast.LENGTH_SHORT).show();
                return null;
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(getActivity());
            pd.setTitle(getResources().getString(R.string.progress_bar_search_location_title));
            pd.setMessage(getResources().getString(R.string.progress_bar_search_location_message));
            pd.setCancelable(false);
            pd.setIndeterminate(true);
            pd.show();
        }

        @Override
        protected void onPostExecute(LatLng latlng) {
            setLatLng(latlng);
            pd.dismiss();
        }
    }

    private class LocationSuggestionsObserver extends DataSetObserver {
        public void onChanged() {
            mSearchView.setSuggestionsAdapter(getSuggestionsCursorAdapter());
        }

        public void onInvalidated() {
            invalidateSuggestions();
        }

    }
}
