/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid
        .Implementation.UiManagers.Implementation;


import android.support.annotation.NonNull;

import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.IdableDataCollection;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableCollection;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewReference;
import com.google.maps.android.clustering.Cluster;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by: Rizwan Choudrey
 * On: 26/11/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class ReviewCluster {
    private final Cluster<ReviewClusterItem> mCluster;

    public ReviewCluster(Cluster<ReviewClusterItem> cluster) {
        mCluster = cluster;
    }

    public ClusterAverage getAverage() {
        float average = 0f;
        int size = 0;

        if (mCluster.getSize() > 0) {
            Set<ReviewId> ids = new HashSet<>();
            for (ReviewClusterItem item : mCluster.getItems()) {
                ReviewReference reference = item.getReference();
                ReviewId reviewId = reference.getReviewId();
                if (ids.contains(reviewId)) continue;
                ids.add(reviewId);
                average += reference.getRating().getRating();
            }
            size = ids.size();
            average /= size;
        }

        return new ClusterAverage(average, size);
    }

    public IdableCollection<ReviewReference> getUniqueReviews() {
        IdableCollection<ReviewReference> references = new IdableDataCollection<>();
        references.addAll(getReviewReferences());
        return references;
    }

    int getNumReviews() {
        return getReviewReferences().size();
    }

    @NonNull
    private Set<ReviewReference> getReviewReferences() {
        Set<ReviewReference> set = new HashSet<>();
        for (ReviewClusterItem item : mCluster.getItems()) {
            set.add(item.getReference());
        }
        return set;
    }

    public static class ClusterAverage {
        private final float mAverage;
        private final int mNumberReviews;

        private ClusterAverage(float average, int numberReviews) {
            mAverage = average;
            mNumberReviews = numberReviews;
        }

        public float getAverage() {
            return mAverage;
        }

        int getNumberReviews() {
            return mNumberReviews;
        }
    }
}
