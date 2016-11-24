/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Fragments;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chdryra.android.reviewer.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.Application.Interfaces.ApplicationInstance;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Activities.ActivityNodeMapper;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.References.Implementation.DataValue;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefDataList;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepository;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.ConverterGv;
import com.chdryra.android.reviewer.Utils.RatingFormatter;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.ReviewLauncher;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;

import java.util.HashMap;
import java.util.Map;

public class FragmentNodeMapper extends FragmentMapLocation {
    private ReviewNode mNode;
    private Map<Marker, DataLocation> mMarkersMap;
    private Marker mCurrentMarker;
    private ReviewInfoAdapter mAdapter;
    private IdableList<ReviewReference> mReviews;

    private void setNode() {
        try {
            ActivityNodeMapper activity = (ActivityNodeMapper) getActivity();
            mNode = activity.getReviewNode();
        } catch (ClassCastException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMarkersMap = new HashMap<>();
    }

    @Override
    public String getMenuTitle() {
        setNode();
        return mNode.getSubject().toString() + " " +
                RatingFormatter.upToTwoSignificantDigits(mNode.getRating().getRating()) + "*";
    }

    @Override
    void onMapReady() {
        AuthorsRepository repo = getApp().getRepository().getAuthorsRepository();
        ConverterGv converter = getApp().getUi().getGvConverter();
        mAdapter = new ReviewInfoAdapter(getActivity(), mNode, repo, converter, mMarkersMap);
        getMap().setInfoWindowAdapter(mAdapter);

        final RefDataList<DataLocation> locations = mNode.getLocations();
        mNode.getReviews().dereference(new DataReference.DereferenceCallback<IdableList<ReviewReference>>() {
            @Override
            public void onDereferenced(DataValue<IdableList<ReviewReference>> value) {
                if(value.hasValue()) {
                    mReviews = value.getData();
                    locations.dereference(new DataReference.DereferenceCallback<IdableList<DataLocation>>() {
                        @Override
                        public void onDereferenced(DataValue<IdableList<DataLocation>> value) {
                            if (value.hasValue()) plotLocations(value.getData());
                        }
                    });
                }
            }
        });
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        setMarker(marker);
        return super.onMarkerClick(marker);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        super.onMapClick(latLng);
        setMarker(null);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        mAdapter.onInfoWindowClick(marker);
    }

    @Override
    public void onInfoWindowLongClick(Marker marker) {
        getReviewLauncher().launchFormatted(mMarkersMap.get(marker).getReviewId());
    }

    @Override
    void onGotoReviewSelected() {
        if (mCurrentMarker != null) {
            getApp().getUi().getCurrentScreen().showToast(Strings.LOADING);
            getReviewLauncher().launchFormatted(mMarkersMap.get(mCurrentMarker).getReviewId());
        }
    }

    @Override
    protected void initButtonUI() {
        super.initButtonUI();
        setMarker(null);
    }

    @Override
    public void onDestroy() {
        mAdapter.unbind();
        super.onDestroy();
    }

    private ReviewLauncher getReviewLauncher() {
        return getApp().getUi().getLauncher().getReviewLauncher();
    }

    private ApplicationInstance getApp() {
        return AppInstanceAndroid.getInstance(getActivity());
    }

    private void setMarker(@Nullable Marker marker) {
        getGotoReviewButton().setEnabled(marker != null);
        mCurrentMarker = marker;
    }

    private void plotLocations(IdableList<DataLocation> locations) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (DataLocation location : locations) {
            Marker marker = addMarker(location);
            mMarkersMap.put(marker, location);
            builder.include(marker.getPosition());
        }

        zoomToLatLng(builder.build().getCenter());
    }

    @NonNull
    @Override
    protected MarkerOptions newMarkerOptions(DataLocation location) {
        TextView text = new TextView(getActivity());
        String rating = String.valueOf(getRating(location.getReviewId())) + "*";
        text.setText(rating);

        IconGenerator generator = new IconGenerator(getActivity());
        generator.setContentView(text);
        Bitmap icon = generator.makeIcon();
        BitmapDescriptor iconBm = BitmapDescriptorFactory.fromBitmap(icon);

        MarkerOptions marker = super.newMarkerOptions(location);
        return marker.position(location.getLatLng()).icon(iconBm);
    }

    private float getRating(ReviewId reviewId) {
        for(ReviewReference review : mReviews) {
            if(review.getReviewId().equals(reviewId)) {
                return review.getRating().getRating();
            }
        }

        return 0f;
    }
}
