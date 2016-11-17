/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Fragments;


import android.app.Fragment;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.mygenerallibrary.LocationUtils.LocationClient;
import com.chdryra.android.mygenerallibrary.LocationUtils.LocationClientGoogle;
import com.chdryra.android.reviewer.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.reviewer.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.jetbrains.annotations.NotNull;

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
public abstract class FragmentMapLocation extends Fragment implements
        LocationClientGoogle.Locatable {
    private static final int LAYOUT = R.layout.fragment_review_location_map_view;
    private static final int MAP_VIEW = R.id.mapView;
    private static final int REVIEW_BUTTON = R.id.button_left;
    private static final int DONE_BUTTON = R.id.button_right;

    private GoogleMap mGoogleMap;
    private MapView mMapView;

    private Button mGotoReviewButton;
    private Button mDoneButton;

    private LocationClient mLocationClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapsInitializer.initialize(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = extractViews(inflater, container, savedInstanceState);

        initButtonUI();

        return v;
    }

    public GoogleMap getMap() {
        return mGoogleMap;
    }

    @Override
    public void onLocated(Location location, CallbackMessage message) {

    }

    @Override
    public void onConnected(Location location, CallbackMessage message) {

    }

    @Override
    public void onStart() {
        super.onStart();
        mLocationClient = AppInstanceAndroid.getInstance(getActivity()).getLocationServices().newLocationClient();
        mLocationClient.connect(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mMapView != null) mMapView.onResume();
    }

    @Override
    public void onSaveInstanceState(@NotNull Bundle outState) {
        if (mMapView != null) mMapView.onSaveInstanceState(outState);
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

    @NonNull
    private View extractViews(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View v = inflater.inflate(LAYOUT, container, false);

        mMapView = (MapView) v.findViewById(MAP_VIEW);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mGoogleMap = googleMap;
                initGoogleMapUi();
            }
        });
        mGotoReviewButton = (Button) v.findViewById(REVIEW_BUTTON);
        mDoneButton = (Button) v.findViewById(DONE_BUTTON);

        return v;
    }

    private void initGoogleMapUi() {
        try {
            mGoogleMap.setMyLocationEnabled(true);
            mGoogleMap.setOnMyLocationButtonClickListener(newLocateMeListener());
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        mGoogleMap.setOnInfoWindowClickListener(newHideMarkerInfoListener());
        mGoogleMap.clear();
        onMapReady();
    }

    void onMapReady() {

    }

    @NonNull
    private OnInfoWindowClickListener newHideMarkerInfoListener() {
        return new OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                marker.hideInfoWindow();
            }
        };
    }

    @NonNull
    private GoogleMap.OnMyLocationButtonClickListener newLocateMeListener() {
        return new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                mLocationClient.locate();
                return false;
            }
        };
    }

    private void initButtonUI() {
        setButtonTexts();
        mGotoReviewButton.setOnClickListener(new View.OnClickListener() {
            //Overridden
            @Override
            public void onClick(View v) {
                onGotoReviewSelected();
            }
        });
        mDoneButton.setOnClickListener(new View.OnClickListener() {
            //Overridden
            @Override
            public void onClick(View v) {
                onDoneSelected();
            }
        });
    }

    private void setButtonTexts() {
        mGotoReviewButton.setText(getResources().getString(R.string.button_goto_review));
        mDoneButton.setText(getResources().getString(R.string.gl_action_done_text));
    }

    private void onDoneSelected() {
        getActivity().finish();
    }

    abstract void onGotoReviewSelected();

    Marker addMarker(DataLocation location) {
        MarkerOptions markerOptions = new MarkerOptions().position(location.getLatLng());
        markerOptions.title(location.getShortenedName());
        markerOptions.draggable(false);
        return mGoogleMap.addMarker(markerOptions);
    }
}
