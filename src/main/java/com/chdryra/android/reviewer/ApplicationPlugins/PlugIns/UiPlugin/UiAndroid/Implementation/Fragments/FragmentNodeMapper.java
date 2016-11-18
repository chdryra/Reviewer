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
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.References.Implementation.DataValue;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefDataList;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepository;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.ConverterGv;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.ReviewLauncher;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;

import java.util.HashMap;
import java.util.Map;

public class FragmentNodeMapper extends FragmentMapLocation {
    private ReviewNode mNode;
    private Map<Marker, DataLocation> mMarkersMap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMarkersMap = new HashMap<>();
        setRetainInstance(true);
    }

    public void setNode(ReviewNode node) {
        mNode = node;
    }

    @Override
    void onMapReady() {
        AuthorsRepository repo = getApp().getRepository().getAuthorsRepository();
        ConverterGv converter = getApp().getUi().getGvConverter();
        getMap().setInfoWindowAdapter(new ReviewInfoAdapter(getActivity(), mNode, repo, converter, mMarkersMap));
        RefDataList<DataLocation> locations = mNode.getLocations();
        locations.dereference(new DataReference.DereferenceCallback<IdableList<DataLocation>>() {
            @Override
            public void onDereferenced(DataValue<IdableList<DataLocation>> value) {
                if(value.hasValue()) plotLocations(value.getData());
            }
        });
    }

    private void plotLocations(IdableList<DataLocation> data) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (DataLocation location : data) {
            Marker marker = addMarker(location);
            mMarkersMap.put(marker, location);
            builder.include(marker.getPosition());
        }

        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(builder.build(), 0);
        GoogleMap map = getMap();
        map.moveCamera(cu);
        map.animateCamera(cu);
    }

    @Override
    public void onInfoWindowLongClick(Marker marker) {
        getReviewLauncher().launchFormatted(mMarkersMap.get(marker).getReviewId());
    }

    @Override
    void onGotoReviewSelected() {
        if(mNode.getChildren().size() > 0) {
            getReviewLauncher().launchAsList(mNode);
        } else {
            getReviewLauncher().launchAsList(mNode.getReviewId());
        }
    }

    private ReviewLauncher getReviewLauncher() {
        return getApp().getUi().getLauncher().getReviewLauncher();
    }

    private ApplicationInstance getApp() {
        return AppInstanceAndroid.getInstance(getActivity());
    }

}
