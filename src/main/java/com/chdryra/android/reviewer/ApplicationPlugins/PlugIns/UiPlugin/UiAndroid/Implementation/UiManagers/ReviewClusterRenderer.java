/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;


import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;

/**
 * Created by: Rizwan Choudrey
 * On: 26/11/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewClusterRenderer extends DefaultClusterRenderer<ReviewClusterItem> {
    private final Context mContext;
    private final IdableList<ReviewReference> mReviews;

    public ReviewClusterRenderer(Context context,
                                 GoogleMap map,
                                 ClusterManager<ReviewClusterItem> clusterManager,
                                 IdableList<ReviewReference> reviews) {
        super(context, map, clusterManager);
        mContext = context;
        mReviews = reviews;
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
    protected boolean shouldRenderAsCluster(Cluster<ReviewClusterItem> cluster) {
        return cluster.getSize() > 1;
    }

    @NonNull
    private BitmapDescriptor getIcon(ReviewClusterItem item) {
        String rating = String.valueOf(getRating(item.getLocation().getReviewId())) + "*";
        TextView text = new TextView(mContext);
        text.setText(rating);

        return getBitmapDescriptor(text);
    }

    @NonNull
    private BitmapDescriptor getIcon(Cluster<ReviewClusterItem> cluster) {
        ReviewCluster reviews = new ReviewCluster(cluster);
        ReviewCluster.ClusterAverage average = reviews.getAverage();
        String rating = String.valueOf(average.getAverage()) + "*";
        TextView text = new TextView(mContext);
        text.setText(rating + " (" + String.valueOf(cluster.getSize()) + ")");

        return getBitmapDescriptor(text);
    }

    @NonNull
    private BitmapDescriptor getBitmapDescriptor(TextView text) {
        IconGenerator generator = new IconGenerator(mContext);
        generator.setContentView(text);
        Bitmap icon = generator.makeIcon();
        return BitmapDescriptorFactory.fromBitmap(icon);
    }

    private float getRating(ReviewId reviewId) {
        for (ReviewReference review : mReviews) {
            if (review.getReviewId().equals(reviewId)) {
                return review.getRating().getRating();
            }
        }

        return 0f;
    }
}
