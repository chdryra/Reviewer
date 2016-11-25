/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Fragments;


import android.app.Fragment;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.mygenerallibrary.LocationUtils.LocationClient;
import com.chdryra.android.mygenerallibrary.LocationUtils.LocationClientGoogle;
import com.chdryra.android.reviewer.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.Application.Interfaces.UiSuite;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.MenuUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.MenuUpAppLevel;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuActionItem;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MaiUpAppLevel;
import com.chdryra.android.reviewer.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.jetbrains.annotations.NotNull;

public abstract class FragmentMapLocation extends Fragment implements
        LocationClientGoogle.Locatable,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnMapClickListener,
        OnInfoWindowClickListener, GoogleMap.
        OnInfoWindowLongClickListener {
    private static final int LAYOUT = R.layout.fragment_review_location_map_view;
    private static final int MAP_VIEW = R.id.mapView;
    private static final int REVIEW_BUTTON = R.id.button_left;
    private static final int DONE_BUTTON = R.id.button_right;
    private static final float DEFAULT_ZOOM = 15;

    private GoogleMap mGoogleMap;
    private MapView mMapView;
    private Button mGotoReviewButton;
    private Button mDoneButton;
    private LocationClient mLocationClient;
    private MenuUi mMenu;

    public GoogleMap getMap() {
        return mGoogleMap;
    }

    protected Button getGotoReviewButton() {
        return mGotoReviewButton;
    }

    protected void initButtonUI() {
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

    protected void zoomToLatLng(LatLng latLng) {
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM));
    }

    @NonNull
    protected MarkerOptions newMarkerOptions(DataLocation location) {
        MarkerOptions markerOptions = new MarkerOptions().position(location.getLatLng());
        markerOptions.title(location.getShortenedName());
        markerOptions.draggable(false);
        return markerOptions;
    }

    protected void locateHere() {
        mLocationClient.locate();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
        MapsInitializer.initialize(getActivity());
        setMenu();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, android.view.MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        mMenu.inflate(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        return mMenu.onItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = extractViews(inflater, container, savedInstanceState);

        initButtonUI();

        setMenu();

        return v;
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
        mLocationClient = AppInstanceAndroid.getInstance(getActivity()).getLocationServices()
                .newLocationClient();
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

    @Override
    public void onInfoWindowClick(Marker marker) {
        onMarkerClick(marker);
    }

    @Override
    public void onInfoWindowLongClick(Marker marker) {
        onMarkerClick(marker);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.isInfoWindowShown()) {
            marker.hideInfoWindow();
        } else {
            marker.showInfoWindow();
        }

        return false;
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    String getMenuTitle() {
        return Strings.Screens.LOCATION;
    }

    void onMapReady() {
        locateHere();
    }

    protected void setMapListeners() {
        mGoogleMap.setOnMarkerClickListener(this);
        mGoogleMap.setOnMapClickListener(this);
        mGoogleMap.setOnInfoWindowClickListener(this);
        mGoogleMap.setOnInfoWindowLongClickListener(this);
    }

    abstract void onGotoReviewSelected();

    Marker addMarker(DataLocation location) {
        return mGoogleMap.addMarker(newMarkerOptions(location));
    }

    private void setMenu() {
        AppInstanceAndroid app = AppInstanceAndroid.getInstance(getActivity());
        UiSuite ui = app.getUi();
        MenuActionItem<GvData> upAction = new MaiUpAppLevel<>(app);

        MenuAction<GvData> action = new MenuUpAppLevel(getMenuTitle(), upAction, ui);
        mMenu = new MenuUi(action);
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
                initGoogleMapUi(googleMap);
            }
        });

        mGotoReviewButton = (Button) v.findViewById(REVIEW_BUTTON);
        mDoneButton = (Button) v.findViewById(DONE_BUTTON);

        return v;
    }

    private void initGoogleMapUi(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        try {
            mGoogleMap.setMyLocationEnabled(true);
            mGoogleMap.setOnMyLocationButtonClickListener(newLocateMeListener());
        } catch (SecurityException e) {
            e.printStackTrace();
        }

        mGoogleMap.clear();
        setMapListeners();
        onMapReady();
    }

    @NonNull
    private GoogleMap.OnMyLocationButtonClickListener newLocateMeListener() {
        return new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                locateHere();
                return false;
            }
        };
    }

    private void setButtonTexts() {
        mGotoReviewButton.setText(getResources().getString(R.string.button_goto_review));
        mDoneButton.setText(getResources().getString(R.string.gl_action_done_text));
    }

    private void onDoneSelected() {
        getActivity().finish();
    }
}
