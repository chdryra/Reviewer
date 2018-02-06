/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;



import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.google.maps.android.clustering.Cluster;

/**
 * Created by: Rizwan Choudrey
 * On: 17/02/2017
 * Email: rizwan.choudrey@gmail.com
 */
public interface InfoWindowLauncher {
    void launchReview(ReviewId reviewId);

    void launchCluster(Cluster<ReviewClusterItem> cluster);
}
