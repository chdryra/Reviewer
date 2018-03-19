/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid
        .Implementation.UiManagers.Implementation;


import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.chdryra.android.startouch.Utils.RatingFormatter;
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

    public ReviewClusterRenderer(Context context,
                                 GoogleMap map,
                                 ClusterManager<ReviewClusterItem> clusterManager) {
        super(context, map, clusterManager);
        mContext = context;
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
        float clusterRating = item.getRating();
        String rating = RatingFormatter.twoSignificantDigits(clusterRating) + "*";
        TextView text = new TextView(mContext);
        text.setText(rating);

        return getBitmapDescriptor(text);
    }

    @NonNull
    private BitmapDescriptor getIcon(Cluster<ReviewClusterItem> cluster) {
        ReviewCluster reviews = new ReviewCluster(cluster);
        ReviewCluster.ClusterAverage average = reviews.getAverage();
        String rating = RatingFormatter.twoSignificantDigits(average.getAverage()) + "*";
        TextView text = new TextView(mContext);
        String string = rating + " (" + String.valueOf(reviews.getNumReviews()) + ")";
        text.setText(string);

        return getBitmapDescriptor(text);
    }

    @NonNull
    private BitmapDescriptor getBitmapDescriptor(TextView text) {
        IconGenerator generator = new IconGenerator(mContext);
        generator.setContentView(text);
        Bitmap icon = generator.makeIcon();
        return BitmapDescriptorFactory.fromBitmap(icon);
    }
}
