/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Fragments;


import android.app.Fragment;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;

import com.chdryra.android.corelibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.corelibrary.LocationUtils.LocationClient;
import com.chdryra.android.corelibrary.LocationUtils.LocationClientGoogle;
import com.chdryra.android.corelibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.corelibrary.Permissions.PermissionResult;
import com.chdryra.android.corelibrary.Permissions.PermissionsManager;
import com.chdryra.android.startouch.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.startouch.Application.Implementation.Strings;
import com.chdryra.android.startouch.Application.Interfaces.ApplicationInstance;
import com.chdryra.android.startouch.Application.Interfaces.UiSuite;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.Implementation.MenuUi;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.Implementation.MenuUpAppLevel;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.MenuActionItem;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions
        .Implementation.MaiUpAppLevel;
import com.chdryra.android.startouch.R;
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

import java.util.List;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;


public abstract class FragmentMapLocation extends Fragment implements
        LocationClientGoogle.Locatable,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnMapClickListener,
        OnInfoWindowClickListener, GoogleMap.
        OnInfoWindowLongClickListener,
        PermissionsManager.PermissionsCallback {
    private static final int LAYOUT = R.layout.fragment_review_location_map_view;
    private static final int MAP_VIEW = R.id.mapView;
    private static final int REVIEW_BUTTON = R.id.button_left;
    private static final int DONE_BUTTON = R.id.button_right;
    private static final float DEFAULT_ZOOM = 15;
    private static final PermissionsManager.Permission LOCATION = PermissionsManager.Permission
            .LOCATION;
    private static final int PERMISSION_REQUEST = RequestCodeGenerator.getCode
            (FragmentMapLocation.class);

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

    protected ApplicationInstance getApp() {
        return AppInstanceAndroid.getInstance(getActivity());
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
        markerOptions.snippet(location.getAddress());
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
        View v = inflater.inflate(LAYOUT, container, false);

        mGotoReviewButton = v.findViewById(REVIEW_BUTTON);
        mDoneButton = v.findViewById(DONE_BUTTON);
        mMapView = v.findViewById(MAP_VIEW);

        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                initGoogleMapUi(googleMap);
            }
        });

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
        mLocationClient = getApp().getGeolocation().newLocationClient();
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

    @Override
    public void onPermissionsResult(int requestCode, List<PermissionResult> results) {
        if (requestCode == PERMISSION_REQUEST
                && results.size() == 1
                && results.get(0).isGranted(LOCATION)) {
            enableMyLocation();
        }
        initialiseMap();
    }

    String getMenuTitle() {
        return Strings.Screens.LOCATION;
    }

    void onMapReady() {
        locateHere();
    }

    abstract void onGotoReviewSelected();

    Marker addMarker(DataLocation location) {
        return mGoogleMap.addMarker(newMarkerOptions(location));
    }

    private void setMapListeners() {
        mGoogleMap.setOnMarkerClickListener(this);
        mGoogleMap.setOnMapClickListener(this);
        mGoogleMap.setOnInfoWindowClickListener(this);
        mGoogleMap.setOnInfoWindowLongClickListener(this);
    }

    private void setMenu() {
        ApplicationInstance app = getApp();
        UiSuite ui = app.getUi();
        MenuActionItem<GvData> upAction = new MaiUpAppLevel<>(app);
        mMenu = new MenuUi(new MenuUpAppLevel(getMenuTitle(), upAction, ui));
    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(getActivity(), ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getActivity(), ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
            mGoogleMap.setMyLocationEnabled(true);
            mGoogleMap.setOnMyLocationButtonClickListener(newLocateMeListener());
        } else {

        }
    }

    private void initGoogleMapUi(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        AppInstanceAndroid app = AppInstanceAndroid.getInstance(getActivity());
        PermissionsManager permissions = app.getPermissions();
        if (permissions.hasPermissions(LOCATION)) {
            enableMyLocation();
            initialiseMap();
        } else {
            permissions.requestPermissions(PERMISSION_REQUEST, this, LOCATION);
        }
    }

    private void initialiseMap() {
        mGoogleMap.clear();
        setMapListeners();

        //Any map camera manipulation in onMapReady() can only be done post layout
        if (mMapView.getViewTreeObserver().isAlive()) {
            mMapView.getViewTreeObserver().addOnGlobalLayoutListener(
                    new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            onMapReady();
                            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                                mMapView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                            } else {
                                mMapView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                            }
                        }
                    });
        }
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
