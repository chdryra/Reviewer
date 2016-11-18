/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Fragments;


import android.app.Activity;
import android.support.annotation.Nullable;
import android.view.View;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepository;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.ConverterGv;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.GvConverterReviewNode;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.ReviewSelector;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.SelectorEqualsReviewId;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.VhMapInfoWindow;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewInfoAdapter implements GoogleMap.InfoWindowAdapter {
    private final Activity mActivity;
    private final ReviewNode mNode;
    private final AuthorsRepository mRepo;
    private final ConverterGv mConverter;
    private final Map<Marker, VhMapInfoWindow> mInflated;
    private final Map<Marker, DataLocation> mMarkersMap;

    public ReviewInfoAdapter(Activity activity,
                             ReviewNode node,
                             AuthorsRepository repo,
                             ConverterGv converter,
                             Map<Marker, DataLocation> markersMap) {
        mActivity = activity;
        mNode = node;
        mRepo = repo;
        mConverter = converter;
        mMarkersMap = markersMap;
        mInflated = new HashMap<>();
    }

    @Override
    @Nullable
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    @Nullable
    public View getInfoContents(Marker marker) {
        if (!mInflated.containsKey(marker)) {
            DataLocation location = mMarkersMap.get(marker);
            GvConverterReviewNode converter = mConverter.newConverterNodes(mRepo);
            SelectorEqualsReviewId selector = new SelectorEqualsReviewId(location.getReviewId());

            VhMapInfoWindow viewHolder = new VhMapInfoWindow(location, new ReviewSelector(selector),
                    mRepo, new UpdateListener(marker));
            viewHolder.inflate(mActivity, null);
            viewHolder.updateView(converter.convert(mNode));
            mInflated.put(marker, viewHolder);
        }

        return mInflated.get(marker).getView();
    }

    void onInfoWindowClick(Marker marker) {
        VhMapInfoWindow info = mInflated.get(marker);
        if(info != null) info.onClick();
    }

    void unbind() {
        for(VhMapInfoWindow window : mInflated.values()) {
            window.unbindFromReview();
        }
    }

    private class UpdateListener implements VhMapInfoWindow.InfoUpdateListener {
        private final Marker mMarker;

        private UpdateListener(Marker marker) {
            mMarker = marker;
        }

        @Override
        public void onInfoUpdated() {
            if (mMarker.isInfoWindowShown()) mMarker.showInfoWindow();
        }
    }
}
