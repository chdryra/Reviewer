/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;

import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolderBasic;
import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolderData;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by: Rizwan Choudrey
 * On: 25/11/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class MapInfoWindow extends ViewHolderBasic {

    public MapInfoWindow(int layoutId, int[] viewIds) {
        super(layoutId, viewIds);
    }

    @Override
    public void updateView(ViewHolderData data) {
        updateView();
    }

    void updateView() {

    }

    void unbindFromReview() {

    }

    void onClick() {

    }

    public static class InfoUpdateListener {
        private final Marker mMarker;

        public InfoUpdateListener(Marker marker) {
            mMarker = marker;
        }

        public void onInfoUpdated() {
            if (mMarker.isInfoWindowShown()) mMarker.showInfoWindow();
        }
    }
}
