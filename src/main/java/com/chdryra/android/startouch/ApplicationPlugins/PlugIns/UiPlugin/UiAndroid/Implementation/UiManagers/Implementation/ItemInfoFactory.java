/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.Implementation;


import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.Interfaces.InfoWindowLauncher;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.startouch.Persistence.Interfaces.AuthorsRepo;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.ReviewSelector;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.SelectorEqualsReviewId;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by: Rizwan Choudrey
 * On: 25/11/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ItemInfoFactory implements ReviewInfoWindowAdapter.InfoWindowFactory {
    private final ReviewNode mNode;
    private final InfoWindowLauncher mLauncher;
    private final AuthorsRepo mRepo;
    private final ReviewClusterRenderer mRenderer;

    public ItemInfoFactory(ReviewNode node,
                           InfoWindowLauncher launcher,
                           AuthorsRepo repo,
                           ReviewClusterRenderer renderer) {
        mNode = node;
        mLauncher = launcher;
        mRepo = repo;
        mRenderer = renderer;
    }

    @Override
    public MapInfoWindow newInfoWindow(Marker marker) {
        DataLocation location = mRenderer.getClusterItem(marker).getLocation();
        SelectorEqualsReviewId selector = new SelectorEqualsReviewId(location.getReviewId());

        return new VhMapInfoWindow(location, mNode, new ReviewSelector(selector),
                mLauncher, mRepo,
                new MapInfoWindow.InfoUpdateListener(marker));
    }
}
