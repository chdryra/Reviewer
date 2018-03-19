/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid
        .Implementation.UiManagers.Implementation;


import com.chdryra.android.startouch.Application.Implementation.Strings;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.Interfaces.InfoWindowLauncher;
import com.chdryra.android.startouch.R;
import com.chdryra.android.startouch.Utils.RatingFormatter;
import com.google.maps.android.clustering.Cluster;

/**
 * Created by: Rizwan Choudrey
 * On: 07/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class VhMapClusterWindow extends MapInfoWindow {
    private static final int LAYOUT = R.layout.map_cluster_window;
    private static final int SUBJECT = R.id.review_subject;
    private static final int RATING = R.id.review_rating_number;
    private static final int LOCATIONS = R.id.cluster_num_locations;

    private final Cluster<ReviewClusterItem> mCluster;
    private final InfoWindowLauncher mLauncher;
    private final InfoUpdateListener mListener;

    public VhMapClusterWindow(Cluster<ReviewClusterItem> cluster, InfoWindowLauncher launcher,
                              InfoUpdateListener listener) {
        super(LAYOUT, new int[]{LAYOUT, SUBJECT, RATING, LOCATIONS});
        mCluster = cluster;
        mLauncher = launcher;
        mListener = listener;
    }

    @Override
    public void updateView() {
        ReviewCluster cluster = new ReviewCluster(mCluster);
        ReviewCluster.ClusterAverage average = cluster.getAverage();
        int numReviews = average.getNumberReviews();
        String stem = numReviews == 1 ? Strings.Mapping.REVIEW : Strings.Mapping.REVIEWS;
        setText(SUBJECT, String.valueOf(numReviews) + " " + stem);
        setText(RATING, RatingFormatter.twoSignificantDigits(average.getAverage()));
        setText(LOCATIONS, mCluster.getSize() + " " + Strings.Mapping.LOCATIONS);

        mListener.onInfoUpdated();
    }

    @Override
    void onClick() {
        mLauncher.launchCluster(mCluster);
    }
}