/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;



import android.widget.TextView;

import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.R;

/**
 * Created by: Rizwan Choudrey
 * On: 07/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class VhMapClusterWindow extends MapInfoWindow {
    private static final int LAYOUT = R.layout.review_map_cluster_window;
    private static final int SUBJECT = R.id.review_subject;
    private static final int RATING = R.id.review_rating_number;

    private final ReviewCluster mCluster;
    private final InfoUpdateListener mListener;

    private TextView mSubject;
    private TextView mRating;

    public VhMapClusterWindow(ReviewCluster cluster, InfoUpdateListener listener) {
        super(LAYOUT, new int[]{LAYOUT, SUBJECT, RATING});
        mCluster = cluster;
        mListener = listener;
    }

    @Override
    public void updateView() {
        if (mSubject == null) mSubject = (TextView) getView(SUBJECT);
        if (mRating == null) mRating = (TextView) getView(RATING);

        ReviewCluster.ClusterAverage average = mCluster.getAverage();
        int numReviews = average.getNumberReviews();
        String stem = numReviews == 1 ? Strings.REVIEW : Strings.REVIEWS;
        mSubject.setText(String.valueOf(numReviews) + " " + stem);
        mRating.setText(String.valueOf(average.getAverage()));
        mListener.onInfoUpdated();
    }
}