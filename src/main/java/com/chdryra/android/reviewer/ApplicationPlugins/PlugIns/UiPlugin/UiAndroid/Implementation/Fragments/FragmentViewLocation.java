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
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.chdryra.android.mygenerallibrary.LocationUtils.LocationClientConnector;
import com.chdryra.android.mygenerallibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.reviewer.Application.ApplicationInstance;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.reviewer.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
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
public class FragmentViewLocation extends Fragment implements
        LocationClientConnector.Locatable {
    private final static String LOCATION = TagKeyGenerator.getKey(FragmentViewLocation.class, "Location");
    private static final float DEFAULT_ZOOM = 15;

    public static final int LAYOUT = R.layout.fragment_review_location_map_view;
    public static final int MAP_VIEW = R.id.mapView;
    public static final int REVIEW_BUTTON = R.id.button_left;
    public static final int GMAPS_BUTTON = R.id.button_middle;
    public static final int DONE_BUTTON = R.id.button_right;

    private GvLocation mCurrent;
    private GoogleMap mGoogleMap;
    private MapView mMapView;

    private Button mGotoReviewButton;
    private Button mGotoMapsButton;
    private Button mDoneButton;

    private LocationClientConnector mLocationClient;

    public static Fragment newInstance(@Nullable GvLocation location) {
        return FactoryFragment.newFragment(FragmentViewLocation.class, LOCATION, location);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) mCurrent = args.getParcelable(LOCATION);
        MapsInitializer.initialize(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = extractViews(inflater, container, savedInstanceState);

        initUi();

        return v;
    }

    @Override
    public void onLocated(Location location) {

    }

    @Override
    public void onLocationClientConnected(Location location) {

    }

    @Override
    public void onStart() {
        super.onStart();
        mLocationClient = new LocationClientConnector(getActivity(), this);
        mLocationClient.connect();
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
        mGoogleMap = mMapView.getMap();
        mGotoReviewButton = (Button) v.findViewById(REVIEW_BUTTON);
        mGotoMapsButton = (Button) v.findViewById(GMAPS_BUTTON);
        mDoneButton = (Button) v.findViewById(DONE_BUTTON);

        return v;
    }

    private void initUi() {
        initGoogleMapUi();
        initButtonUI();
    }

    private void initGoogleMapUi() {
        //TODO handle permissions
        try {
            mGoogleMap.setMyLocationEnabled(true);
            mGoogleMap.setOnMyLocationButtonClickListener(newLocateMeListener());
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        mGoogleMap.setOnInfoWindowClickListener(newHideMarkerInfoListener());
        zoomToLatLng();
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
        mGotoMapsButton.setOnClickListener(new View.OnClickListener() {
            //Overridden
            @Override
            public void onClick(View v) {
                onGotoMapsSelected();
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
        mGotoMapsButton.setText(getResources().getString(R.string.button_goto_google_maps));
        mDoneButton.setText(getResources().getString(R.string.gl_action_done_text));
    }

    private void onDoneSelected() {
        getActivity().finish();
    }

    private void onGotoMapsSelected() {

    }

    private void onGotoReviewSelected() {
        ApplicationInstance app = ApplicationInstance.getInstance(getActivity());
        app.launchReview(getActivity(), mCurrent.getReviewId());
    }

    private void zoomToLatLng() {
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mCurrent.getLatLng(),
                DEFAULT_ZOOM));
        updateMapMarker();
    }

    private void updateMapMarker() {
        MarkerOptions markerOptions = new MarkerOptions().position(mCurrent.getLatLng());
        markerOptions.title(mCurrent.getShortenedName());
        markerOptions.draggable(false);
        mGoogleMap.clear();
        Marker marker = mGoogleMap.addMarker(markerOptions);
        marker.showInfoWindow();
    }
}
