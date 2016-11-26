/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers;


import android.app.Activity;
import android.support.annotation.Nullable;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewInfoWindowAdapter implements GoogleMap.InfoWindowAdapter, GoogleMap
        .OnInfoWindowClickListener {
    private final Activity mActivity;
    private final InfoWindowFactory mFactory;
    private final Map<Marker, MapInfoWindow> mInflated;

    interface InfoWindowFactory {
        MapInfoWindow newInfoWindow(Marker marker);
    }

    public ReviewInfoWindowAdapter(Activity activity, InfoWindowFactory factory) {
        mActivity = activity;
        mFactory = factory;
        mInflated = new HashMap<>();
    }

    public void unbind() {
        for (MapInfoWindow window : mInflated.values()) {
            window.unbindFromReview();
        }
    }

    @Override
    @Nullable
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    @Nullable
    public View getInfoContents(final Marker marker) {
        if (!mInflated.containsKey(marker)) {
            MapInfoWindow viewHolder = mFactory.newInfoWindow(marker);
            viewHolder.inflate(mActivity, null);
            viewHolder.updateView();
            mInflated.put(marker, viewHolder);
        }

        return mInflated.get(marker).getView();
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        MapInfoWindow info = mInflated.get(marker);
        if (info != null) info.onClick();
    }
}
