/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.Implementation;


import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;

/**
 * Created by: Rizwan Choudrey
 * On: 26/11/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class LongClickClusterManager<T extends ClusterItem> extends ClusterManager<T> implements GoogleMap.OnInfoWindowLongClickListener {
    private final GoogleMap.OnInfoWindowLongClickListener mLongClick;

    public LongClickClusterManager(Context context,
                                   GoogleMap map,
                                   GoogleMap.OnInfoWindowLongClickListener longClick) {
        super(context, map);
        mLongClick = longClick;
    }

    @Override
    public void onInfoWindowLongClick(Marker marker) {
        mLongClick.onInfoWindowLongClick(marker);
    }
}
