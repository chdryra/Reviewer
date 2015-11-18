/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 18 June, 2015
 */

package com.chdryra.android.reviewer.View.ActivitiesFragments;

import android.app.Activity;
import android.app.Fragment;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.chdryra.android.mygenerallibrary.LocationClientConnector;
import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataPacker;
import com.chdryra.android.reviewer.View.GvDataModel.GvLocationList;
import com.chdryra.android.reviewer.View.Launcher.Implementation.LauncherUiImpl;
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
    private static final float DEFAULT_ZOOM = 15;

    private GvLocationList.GvLocation mCurrent;
    private GoogleMap mGoogleMap;
    private MapView mMapView;

    private Button mGotoReviewButton;
    private Button mGotoMapsButton;
    private Button mDoneButton;

    private LocationClientConnector mLocationClient;

    private void onDoneSelected() {
        getActivity().finish();
    }

    private void onGotoMapsSelected() {

    }

    private void onGotoReviewSelected() {
        ApplicationInstance admin = ApplicationInstance.getInstance(getActivity());
        admin.launchReview(getActivity(), mCurrent.getReviewId());
    }

    private void initUI() {
        initGoogleMapUI();
        initButtonUI();
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
        mGoogleMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

            //Overridden
            @Override
            public void onInfoWindowClick(Marker marker) {
                marker.hideInfoWindow();
            }
        });
        zoomToLatLng();
    }

    private void initButtonUI() {
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

    //Overridden
    @Override
    public void onLocated(Location location) {

    }

    @Override
    public void onLocationClientConnected(Location location) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Activity activity = getActivity();

        mLocationClient = new LocationClientConnector(activity, this);
        mLocationClient.connect();

        Bundle args = LauncherUiImpl.getArgsForActivity(activity);
        mCurrent = (GvLocationList.GvLocation) GvDataPacker.unpackItem(GvDataPacker
                .CurrentNewDatum.CURRENT, args);

        MapsInitializer.initialize(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_review_location_map_view, container, false);

        mMapView = (MapView) v.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mGoogleMap = ((MapView) v.findViewById(R.id.mapView)).getMap();
        mGotoReviewButton = (Button) v.findViewById(R.id.button_left);
        mGotoMapsButton = (Button) v.findViewById(R.id.button_middle);
        mDoneButton = (Button) v.findViewById(R.id.button_right);

        mGotoReviewButton.setText(getResources().getString(R.string.button_goto_review));
        mGotoMapsButton.setText(getResources().getString(R.string.button_goto_google_maps));
        mDoneButton.setText(getResources().getString(R.string.gl_action_done_text));

        initUI();

        return v;
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
}
