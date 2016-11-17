/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Fragments;



import android.os.Bundle;

import com.chdryra.android.reviewer.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.reviewer.Application.Interfaces.ApplicationInstance;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Activities.ActivityNodeMapper;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ListItemBinder;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ListReference;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefDataList;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.ReviewLauncher;

import java.util.Collection;

public class FragmentNodeMapper extends FragmentMapLocation {
    private ReviewNode mNode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            ActivityNodeMapper activity = (ActivityNodeMapper)getActivity();
            mNode = activity.getNode();
        } catch (ClassCastException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    void onMapReady() {
        RefDataList<DataLocation> locations = mNode.getLocations();
        locations.bindToItems(new ListItemBinder<DataLocation>() {
            @Override
            public void onItemAdded(DataLocation value) {
                addMarker(value);
            }

            @Override
            public void onItemRemoved(DataLocation value) {

            }

            @Override
            public void onListChanged(Collection<DataLocation> newItems) {

            }

            @Override
            public void onInvalidated(ListReference<DataLocation, ?> reference) {

            }
        });
    }

    @Override
    void onGotoReviewSelected() {
        ApplicationInstance app = AppInstanceAndroid.getInstance(getActivity());
        ReviewLauncher launcher = app.getUi().getLauncher().getReviewLauncher();
        if(mNode.getChildren().size() > 0) {
            launcher.launchAsList(mNode);
        } else {
            launcher.launchAsList(mNode.getReviewId());
        }
    }
}
