/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Fragments;


import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.chdryra.android.corelibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.corelibrary.LocationServices.LocationId;
import com.chdryra.android.corelibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.startouch.Application.Implementation.Strings;
import com.chdryra.android.startouch.Application.Interfaces.UiSuite;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Activities.ActivityNodeMapper;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.Implementation.ClusterInfoFactory;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.Implementation.ItemInfoFactory;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.Implementation.LocationPlotter;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.Implementation.ReviewCluster;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.Implementation.ReviewClusterItem;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.Implementation.ReviewClusterManager;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.Implementation.ReviewClusterRenderer;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.Implementation.ReviewInfoWindowAdapter;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.Interfaces.InfoWindowLauncher;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableCollection;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.startouch.Persistence.Interfaces.AuthorsRepo;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewNodeRepo;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvLocation;
import com.chdryra.android.startouch.Utils.ParcelablePacker;
import com.chdryra.android.startouch.Utils.RatingFormatter;
import com.chdryra.android.startouch.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.startouch.View.LauncherModel.Implementation.UiLauncherArgs;
import com.chdryra.android.startouch.View.LauncherModel.Interfaces.ReviewLauncher;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.MarkerManager;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;

public class FragmentNodeMapper extends FragmentMapLocation implements InfoWindowLauncher {
    private static final String PUBLISHED = TagKeyGenerator.getKey(FragmentNodeMapper.class,
            "published");

    private ReviewNode mNode;
    private boolean mIsPublished;
    private Marker mCurrentMarker;
    private ReviewInfoWindowAdapter mItemAdapter;
    private ReviewInfoWindowAdapter mClusterAdapter;
    private ReviewClusterRenderer mRenderer;
    private LocationPlotter mLocationPlotter;

    public static FragmentNodeMapper newInstance(boolean isPublished) {
        //Can't use FactoryFragment as Support fragment rather than normal fragment
        Bundle args = new Bundle();
        args.putBoolean(PUBLISHED, isPublished);
        FragmentNodeMapper fragment = new FragmentNodeMapper();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public String getMenuTitle() {
        setNode();
        return mNode.getSubject().toString() + " " +
                RatingFormatter.upToTwoSignificantDigits(mNode.getRating().getRating()) + "*";
    }

    @Override
    void onMapReady() {
        Bundle args = getArguments();
        mIsPublished = args != null && args.getBoolean(PUBLISHED);
        mLocationPlotter = new LocationPlotter(mNode.getLocations(), newManager(), getReviews());
        mLocationPlotter.bind();
    }

    @Override
    public void onDestroy() {
        if (mItemAdapter != null) mItemAdapter.unbind();
        if (mClusterAdapter != null) mClusterAdapter.unbind();
        if (mLocationPlotter != null) mLocationPlotter.unbind();
        super.onDestroy();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        resetInfoWindow(marker);
        return super.onMarkerClick(marker);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        super.onMapClick(latLng);
        resetInfoWindow(null);
    }

    @Override
    void onGotoReviewSelected() {
        if (mCurrentMarker != null && mIsPublished) {
            if (mRenderer.getClusterItem(mCurrentMarker) != null) {
                launchReview(mRenderer.getClusterItem(mCurrentMarker).getReviewId());
            } else if (mRenderer.getCluster(mCurrentMarker) != null) {
                launchCluster(mRenderer.getCluster(mCurrentMarker));
            }
        }
    }

    @Override
    protected void initButtonUI() {
        super.initButtonUI();
        setMarker(null);
        getGotoReviewButton().setVisibility(View.GONE);
    }

    @Override
    public void onInfoWindowLongClick(Marker marker) {
        if (mIsPublished) {
            if (mRenderer.getClusterItem(marker) != null) {
                launchAddress(mRenderer.getClusterItem(marker));
            } else if (mRenderer.getCluster(marker) != null) {
                launchAddresses(mRenderer.getCluster(marker));
            }
        }
    }

    @Override
    public void launchReview(ReviewId reviewId) {
        getReviewLauncher().launchAsList(reviewId);
    }

    @Override
    public void onLocated(Location location, CallbackMessage message) {
        if (message.isOk())
            zoomToLatLng(new LatLng(location.getLatitude(), location.getLongitude()));
    }

    @Override
    public void onConnected(Location location, CallbackMessage message) {
        onLocated(location, message);
    }

    @Override
    public void launchCluster(Cluster<ReviewClusterItem> cluster) {
        int size = cluster.getSize();
        String stem = size == 1 ? Strings.Mapping.LOCATION : Strings.Mapping.LOCATIONS;
        String subject = String.valueOf(size) + " " + stem;

        IdableCollection<ReviewReference> reviews = new ReviewCluster(cluster).getUniqueReviews();

        getReviewLauncher().launchAsList(getReviews().getMetaReview(reviews, subject));
    }

    private ReviewNodeRepo getReviews() {
        return getApp().getRepository().getReviews();
    }

    private ReviewLauncher getReviewLauncher() {
        return getApp().getUi().getLauncher().getReviewLauncher();
    }

    private void resetInfoWindow(@Nullable Marker marker) {
        setMarker(marker);
        mItemAdapter.resetInfoWindow(mCurrentMarker);
        mClusterAdapter.resetInfoWindow(mCurrentMarker);
    }

    private void launchAddress(ReviewClusterItem item) {
        GvLocation loc = getApp().getUi().getGvConverter().newConverterLocations().convert(item
                .getLocation());
        launchAddressView(loc);
    }

    private void launchAddressView(GvLocation location) {
        UiSuite ui = getApp().getUi();
        ParcelablePacker<GvLocation> packer = new ParcelablePacker<>();
        Bundle args = new Bundle();
        packer.packItem(ParcelablePacker.CurrentNewDatum.CURRENT, location, args);
        LaunchableConfig viewer = ui.getConfig().getViewer(DataLocation.TYPE_NAME);
        viewer.launch(new UiLauncherArgs(viewer.getDefaultRequestCode()).setBundle(args));
    }

    private void launchAddresses(Cluster<ReviewClusterItem> cluster) {
        String name = String.valueOf(cluster.getSize()) + " locations";
        String addresses = "Zoom in to see locations";
        GvLocation loc = new GvLocation(cluster.getPosition(), name, addresses, LocationId.nullId
                ());
        launchAddressView(loc);
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
        getGotoReviewButton().setEnabled(mIsPublished && marker != null);
        mCurrentMarker = marker;
    }

    private ClusterManager<ReviewClusterItem> newManager() {
        Activity activity = getActivity();
        GoogleMap map = getMap();

        ReviewClusterManager<ReviewClusterItem> clusterManager
                = new ReviewClusterManager<>(activity, map, this);
        mRenderer = new ReviewClusterRenderer(activity, map, clusterManager);
        clusterManager.setRenderer(mRenderer);

        AuthorsRepo repo = getApp().getRepository().getAuthors();
        mItemAdapter = new ReviewInfoWindowAdapter(activity, new ItemInfoFactory(mNode, this,
                repo, mRenderer));
        mClusterAdapter = new ReviewInfoWindowAdapter(activity, new ClusterInfoFactory(mRenderer,
                this));

        MarkerManager.Collection items = clusterManager.getMarkerCollection();
        MarkerManager.Collection clusters = clusterManager.getClusterMarkerCollection();
        items.setOnInfoWindowAdapter(mItemAdapter);
        items.setOnInfoWindowClickListener(mItemAdapter);
        items.setOnMarkerClickListener(this);
        clusters.setOnInfoWindowAdapter(mClusterAdapter);
        clusters.setOnInfoWindowClickListener(mClusterAdapter);
        clusters.setOnMarkerClickListener(this);

        map.setOnCameraIdleListener(clusterManager);
        map.setOnMarkerClickListener(clusterManager);
        map.setOnInfoWindowClickListener(clusterManager);
        map.setOnInfoWindowLongClickListener(clusterManager);
        map.setInfoWindowAdapter(clusterManager.getMarkerManager());

        return clusterManager;
    }
}
