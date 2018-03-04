/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid
        .Implementation.UiManagers.Implementation;


import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.Interfaces.InfoWindowLauncher;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by: Rizwan Choudrey
 * On: 25/11/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ClusterInfoFactory implements ReviewInfoWindowAdapter.InfoWindowFactory {
    private final ReviewClusterRenderer mRenderer;
    private final InfoWindowLauncher mLauncher;

    public ClusterInfoFactory(ReviewClusterRenderer renderer, InfoWindowLauncher launcher) {
        mRenderer = renderer;
        mLauncher = launcher;
    }

    @Override
    public MapInfoWindow newInfoWindow(Marker marker) {
        return new VhMapClusterWindow(mRenderer.getCluster(marker),
                mLauncher, new MapInfoWindow.InfoUpdateListener(marker));
    }
}
