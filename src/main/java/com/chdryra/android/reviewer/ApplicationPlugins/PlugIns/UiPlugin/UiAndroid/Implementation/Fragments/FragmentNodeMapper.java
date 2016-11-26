/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Fragments;


import android.app.Activity;
import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.Application.Interfaces.ApplicationInstance;
import com.chdryra.android.reviewer.Application.Interfaces.UiSuite;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Activities.ActivityNodeMapper;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.ClusterInfoFactory;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.ItemInfoFactory;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.LongClickClusterManager;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.ReviewClusterItem;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.ReviewClusterRenderer;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.ReviewInfoWindowAdapter;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.IdableDataCollection;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableCollection;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.References.Implementation.DataValue;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepository;
import com.chdryra.android.reviewer.Utils.RatingFormatter;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.MarkerManager;
import com.google.maps.android.clustering.Cluster;

public class FragmentNodeMapper extends FragmentMapLocation {
    private ReviewNode mNode;
    private IdableList<ReviewReference> mReviews;
    private Marker mCurrentMarker;
    private ReviewInfoWindowAdapter mItemAdapter;
    private ReviewInfoWindowAdapter mClusterAdapter;
    private ReviewClusterRenderer mRenderer;
    private LongClickClusterManager<ReviewClusterItem> mClusterManager;

    @Override
    public String getMenuTitle() {
        setNode();
        return mNode.getSubject().toString() + " " +
                RatingFormatter.upToTwoSignificantDigits(mNode.getRating().getRating()) + "*";
    }

    @Override
    void onMapReady() {
        mNode.getReviews().dereference(new DataReference
                .DereferenceCallback<IdableList<ReviewReference>>() {
            @Override
            public void onDereferenced(DataValue<IdableList<ReviewReference>> value) {
                if (value.hasValue()) {
                    mReviews = value.getData();
                    plotLocations();
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
    void onGotoReviewSelected() {
        if (mCurrentMarker != null) onInfoWindowLongClick(mCurrentMarker);
    }

    @Override
    protected void initButtonUI() {
        super.initButtonUI();
        setMarker(null);
    }

    @Override
    public void onDestroy() {
        mItemAdapter.unbind();
        mClusterAdapter.unbind();
        super.onDestroy();
    }

    @Override
    public void onInfoWindowLongClick(Marker marker) {
        if (mRenderer.getClusterItem(marker) != null) {
            launchReview(mRenderer.getClusterItem(marker));
        } else if (mRenderer.getCluster(marker) != null) {
            launchMetaReview(mRenderer.getCluster(marker));
        }
    }

    private ApplicationInstance getApp() {
        return AppInstanceAndroid.getInstance(getActivity());
    }

    private void launchReview(ReviewClusterItem item) {
        UiSuite ui = getApp().getUi();
        ui.getCurrentScreen().showToast(Strings.LOADING);
        ui.getLauncher().getReviewLauncher().launchAsList(item.getReference().getReviewId());
    }

    private void launchMetaReview(Cluster<ReviewClusterItem> cluster) {
        IdableCollection<ReviewReference> reviews = new IdableDataCollection<>();
        for (ReviewClusterItem item : cluster.getItems()) {
            reviews.add(item.getReference());
        }

        String subject = String.valueOf(cluster.getSize()) + " " + Strings.REVIEWS;
        ReviewNode node
                = getApp().getRepository().getReviewsRepository().getMetaReview(reviews, subject);

        UiSuite ui = getApp().getUi();
        ui.getCurrentScreen().showToast(Strings.LOADING);
        ui.getLauncher().getReviewLauncher().launchAsList(node);
    }

    private void setNode() {
        try {
            ActivityNodeMapper activity = (ActivityNodeMapper) getActivity();
            mNode = activity.getReviewNode();
        } catch (ClassCastException e) {
            throw new RuntimeException(e);
        }
    }

    private void setMarker(@Nullable Marker marker) {
        getGotoReviewButton().setEnabled(marker != null);
        mCurrentMarker = marker;
    }

    private void plotLocations() {
        mNode.getLocations().dereference(new DataReference
                .DereferenceCallback<IdableList<DataLocation>>() {
            @Override
            public void onDereferenced(DataValue<IdableList<DataLocation>> value) {
                if (value.hasValue()) plotLocations(value.getData());
            }
        });
    }

    private void plotLocations(IdableList<DataLocation> locations) {
        setClusterManager();
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (DataLocation location : locations) {
            ReviewClusterItem item = new ReviewClusterItem(getReference(location), location);
            mClusterManager.addItem(item);
            builder.include(item.getPosition());
        }

        zoomToLatLng(builder.build().getCenter());
    }

    private void setClusterManager() {
        Activity activity = getActivity();
        GoogleMap map = getMap();

        mClusterManager = new LongClickClusterManager<>(activity, map, this);
        mRenderer = new ReviewClusterRenderer(activity, map, mClusterManager, mReviews);
        mClusterManager.setRenderer(mRenderer);

        AuthorsRepository repo = getApp().getRepository().getAuthorsRepository();
        mItemAdapter = new ReviewInfoWindowAdapter(activity, new ItemInfoFactory(mNode, repo,
                mRenderer));
        mClusterAdapter = new ReviewInfoWindowAdapter(activity, new ClusterInfoFactory(mRenderer));

        MarkerManager.Collection items = mClusterManager.getMarkerCollection();
        MarkerManager.Collection clusters = mClusterManager.getClusterMarkerCollection();
        items.setOnInfoWindowAdapter(mItemAdapter);
        items.setOnInfoWindowClickListener(mItemAdapter);
        items.setOnMarkerClickListener(this);
        clusters.setOnInfoWindowAdapter(mClusterAdapter);
        clusters.setOnInfoWindowClickListener(mClusterAdapter);
        clusters.setOnMarkerClickListener(this);

        map.setOnCameraIdleListener(mClusterManager);
        map.setOnMarkerClickListener(mClusterManager);
        map.setOnInfoWindowClickListener(mClusterManager);
        map.setOnInfoWindowLongClickListener(mClusterManager);
        map.setInfoWindowAdapter(mClusterManager.getMarkerManager());
    }

    private ReviewReference getReference(DataLocation location) {
        for (ReviewReference review : mReviews) {
            if (review.getReviewId().equals(location.getReviewId())) {
                return review;
            }
        }

        throw new RuntimeException("Review not found!");
    }

}
