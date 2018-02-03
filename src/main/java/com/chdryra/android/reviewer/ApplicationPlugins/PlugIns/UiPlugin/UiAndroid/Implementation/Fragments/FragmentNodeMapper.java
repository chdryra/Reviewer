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
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.chdryra.android.mygenerallibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.reviewer.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.Application.Interfaces.ApplicationInstance;
import com.chdryra.android.reviewer.Application.Interfaces.UiSuite;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Activities.ActivityNodeMapper;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.ClusterInfoFactory;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.InfoWindowLauncher;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.ItemInfoFactory;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.LongClickClusterManager;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.ReviewCluster;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.ReviewClusterItem;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.ReviewClusterRenderer;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.ReviewInfoWindowAdapter;


import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableCollection;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.References.Implementation.DataValue;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.mygenerallibrary.LocationServices.LocationId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepo;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.reviewer.Utils.ParcelablePacker;
import com.chdryra.android.reviewer.Utils.RatingFormatter;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.LauncherModel.Implementation.UiLauncherArgs;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.MarkerManager;
import com.google.maps.android.clustering.Cluster;

public class FragmentNodeMapper extends FragmentMapLocation implements InfoWindowLauncher {
    private static final String PUBLISHED = TagKeyGenerator.getKey(FragmentNodeMapper.class,
            "published");
    private static final int PADDING = 100;

    private ReviewNode mNode;
    private IdableList<ReviewReference> mReviews;
    private Marker mCurrentMarker;
    private ReviewInfoWindowAdapter mItemAdapter;
    private ReviewInfoWindowAdapter mClusterAdapter;
    private ReviewClusterRenderer mRenderer;
    private boolean mIsPublished;

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
                launchReview(mRenderer.getClusterItem(mCurrentMarker).getReference().getReviewId());
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
    public void onDestroy() {
        mItemAdapter.unbind();
        mClusterAdapter.unbind();
        super.onDestroy();
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
        getApp().getUi().getLauncher().getReviewLauncher().launchAsList(reviewId);
    }

    @Override
    public void launchCluster(Cluster<ReviewClusterItem> cluster) {
        int size = cluster.getSize();
        String stem = size == 1 ? Strings.Mapping.LOCATION : Strings.Mapping.LOCATIONS;
        String subject = String.valueOf(size) + " " + stem;

        IdableCollection<ReviewReference> reviews = new ReviewCluster(cluster).getUniqueReviews();
        ReviewNode node
                = getApp().getRepository().getReviews().getMetaReview(reviews, subject);

        getApp().getUi().getLauncher().getReviewLauncher().launchAsList(node);
    }

    private ApplicationInstance getApp() {
        return AppInstanceAndroid.getInstance(getActivity());
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
        LongClickClusterManager<ReviewClusterItem> clusterManager = newClusterManager();
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        int i = 0;
        for (DataLocation location : locations) {
            ReviewClusterItem item = new ReviewClusterItem(getReference(location), location);
            clusterManager.addItem(item);
            builder.include(item.getPosition());
            i++;
        }

        if(i > 0) {
            getMap().animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), PADDING));
        }
    }

    private LongClickClusterManager<ReviewClusterItem> newClusterManager() {
        Activity activity = getActivity();
        GoogleMap map = getMap();

        LongClickClusterManager<ReviewClusterItem> clusterManager
                = new LongClickClusterManager<>(activity, map, this);
        mRenderer = new ReviewClusterRenderer(activity, map, clusterManager, mReviews);
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

    private ReviewReference getReference(DataLocation location) {
        for (ReviewReference review : mReviews) {
            if (review.getReviewId().equals(location.getReviewId())) {
                return review;
            }
        }

        throw new RuntimeException("Review not found!");
    }
}
