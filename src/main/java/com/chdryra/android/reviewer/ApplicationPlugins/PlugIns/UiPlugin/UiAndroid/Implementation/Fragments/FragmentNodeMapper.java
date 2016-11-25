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
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chdryra.android.reviewer.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.Application.Interfaces.ApplicationInstance;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Activities.ActivityNodeMapper;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.ClusterInfoFactory;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.ItemInfoFactory;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.ReviewClusterItem;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.ReviewInfoWindowAdapter;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.References.Implementation.DataValue;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefDataList;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepository;
import com.chdryra.android.reviewer.Utils.RatingFormatter;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.ReviewLauncher;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.MarkerManager;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;

import java.util.HashMap;
import java.util.Map;

public class FragmentNodeMapper extends FragmentMapLocation {
    private ReviewNode mNode;
    private Map<Marker, ReviewClusterItem> mMarkersMap;
    private Map<Marker, Cluster<ReviewClusterItem>> mClusterMarkersMap;
    private Marker mCurrentMarker;
    private ReviewInfoWindowAdapter mItemAdapter;
    private ReviewInfoWindowAdapter mClusterAdapter;
    private IdableList<ReviewReference> mReviews;
    private ClusterManager<ReviewClusterItem> mClusterManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMarkersMap = new HashMap<>();
        mClusterMarkersMap = new HashMap<>();
    }

    @Override
    public String getMenuTitle() {
        setNode();
        return mNode.getSubject().toString() + " " +
                RatingFormatter.upToTwoSignificantDigits(mNode.getRating().getRating()) + "*";
    }

    @Override
    void onMapReady() {
        setClusterManager();

        final RefDataList<DataLocation> locations = mNode.getLocations();
        mNode.getReviews().dereference(new DataReference
                .DereferenceCallback<IdableList<ReviewReference>>() {
            @Override
            public void onDereferenced(DataValue<IdableList<ReviewReference>> value) {
                if (value.hasValue()) {
                    mReviews = value.getData();
                    locations.dereference(new DataReference
                            .DereferenceCallback<IdableList<DataLocation>>() {
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
    public void onInfoWindowLongClick(Marker marker) {
        if (mMarkersMap.containsKey(marker)) {
            getApp().getUi().getCurrentScreen().showToast(Strings.LOADING);
            launchReview(marker);
        } else if (mClusterMarkersMap.containsKey(marker)) {
            getApp().getUi().getCurrentScreen().showToast(Strings.LOADING);
            launchMetaReview(marker);
        }
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
        super.onDestroy();
    }

    private ReviewLauncher getReviewLauncher() {
        return getApp().getUi().getLauncher().getReviewLauncher();
    }

    private ApplicationInstance getApp() {
        return AppInstanceAndroid.getInstance(getActivity());
    }

    private void setClusterManager() {
        AuthorsRepository repo = getApp().getRepository().getAuthorsRepository();
        Activity activity = getActivity();

        mClusterManager = new ClusterManager<>(activity, getMap());
        mClusterManager.setRenderer(new ReviewClusterRenderer(activity, getMap(), mClusterManager));

        mItemAdapter = new ReviewInfoWindowAdapter(activity,
                new ItemInfoFactory(mNode, repo, mMarkersMap));
        mClusterAdapter = new ReviewInfoWindowAdapter(activity,
                new ClusterInfoFactory(mClusterMarkersMap));
        MarkerManager.Collection markers = mClusterManager.getMarkerCollection();
        MarkerManager.Collection clusters = mClusterManager.getClusterMarkerCollection();
        markers.setOnInfoWindowAdapter(mItemAdapter);
        markers.setOnInfoWindowClickListener(mItemAdapter);
        markers.setOnMarkerClickListener(this);
        clusters.setOnInfoWindowAdapter(mClusterAdapter);
        clusters.setOnInfoWindowClickListener(mClusterAdapter);
        clusters.setOnMarkerClickListener(this);

        getMap().setOnMarkerClickListener(mClusterManager);
        getMap().setOnCameraIdleListener(mClusterManager);
        getMap().setInfoWindowAdapter(mClusterManager.getMarkerManager());
        getMap().setOnInfoWindowClickListener(mClusterManager.getMarkerManager());
    }

    @NonNull
    private BitmapDescriptor getIcon(ReviewClusterItem item) {
        String rating = String.valueOf(getRating(item.getLocation().getReviewId())) + "*";
        TextView text = new TextView(getActivity());
        text.setText(rating);

        return getBitmapDescriptor(text);
    }

    @NonNull
    private BitmapDescriptor getIcon(Cluster<ReviewClusterItem> cluster) {
        String rating = String.valueOf(ReviewClusterItem.getAverage(cluster)) + "*";
        TextView text = new TextView(getActivity());
        text.setText(rating + " (" + String.valueOf(cluster.getSize()) + ")");

        return getBitmapDescriptor(text);
    }

    @NonNull
    private BitmapDescriptor getBitmapDescriptor(TextView text) {
        IconGenerator generator = new IconGenerator(getActivity());
        generator.setContentView(text);
        Bitmap icon = generator.makeIcon();
        return BitmapDescriptorFactory.fromBitmap(icon);
    }

    private void launchMetaReview(Marker marker) {
        Cluster<ReviewClusterItem> cluster = mClusterMarkersMap.get(marker);
        IdableList<ReviewReference> reviews = new IdableDataList<>(mReviews.getReviewId());
        for (ReviewClusterItem item : cluster.getItems()) {
            reviews.add(item.getReference());
        }
        String subject = String.valueOf(cluster.getSize()) + " " + Strings.REVIEWS;
        ReviewNode node = getApp().getRepository().getReviewsRepository().getMetaReview
                (reviews, subject);
        getReviewLauncher().launchAsList(node);
    }

    private void launchReview(Marker marker) {
        ReviewClusterItem item = mMarkersMap.get(marker);
        ReviewId reviewId = item.getReference().getReviewId();
        getReviewLauncher().launchAsList(reviewId);
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

    private void plotLocations(IdableList<DataLocation> locations) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (DataLocation location : locations) {
            ReviewClusterItem item = new ReviewClusterItem(getReviewReference(location), location);
            mClusterManager.addItem(item);
            builder.include(item.getPosition());
        }

        zoomToLatLng(builder.build().getCenter());
    }

    private ReviewReference getReviewReference(DataLocation location) {
        for (ReviewReference review : mReviews) {
            if (review.getReviewId().equals(location.getReviewId())) {
                return review;
            }
        }

        throw new RuntimeException("Review not found!");
    }

    private float getRating(ReviewId reviewId) {
        for (ReviewReference review : mReviews) {
            if (review.getReviewId().equals(reviewId)) {
                return review.getRating().getRating();
            }
        }

        return 0f;
    }

    private class ReviewClusterRenderer extends DefaultClusterRenderer<ReviewClusterItem> {
        private ReviewClusterRenderer(Context context,
                                      GoogleMap map,
                                      ClusterManager<ReviewClusterItem> clusterManager) {
            super(context, map, clusterManager);
        }

        @Override
        protected void onBeforeClusterRendered(Cluster<ReviewClusterItem> cluster, MarkerOptions
                markerOptions) {
            markerOptions.icon(getIcon(cluster));
        }

        @Override
        protected void onBeforeClusterItemRendered(ReviewClusterItem item, MarkerOptions
                markerOptions) {
            markerOptions.icon(getIcon(item));
        }

        @Override
        protected void onClusterRendered(Cluster<ReviewClusterItem> cluster, Marker marker) {
            mClusterMarkersMap.put(marker, cluster);
        }

        @Override
        protected void onClusterItemRendered(ReviewClusterItem clusterItem, Marker marker) {
            mMarkersMap.put(marker, clusterItem);
        }

        @Override
        protected boolean shouldRenderAsCluster(Cluster<ReviewClusterItem> cluster) {
            return cluster.getSize() > 1;
        }
    }
}
