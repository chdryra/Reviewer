/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;


import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepository;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.ReviewSelector;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.SelectorEqualsReviewId;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by: Rizwan Choudrey
 * On: 25/11/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ItemInfoFactory implements ReviewInfoWindowAdapter.InfoWindowFactory {
    private final ReviewNode mNode;
    private final AuthorsRepository mRepo;
    private final ReviewClusterRenderer mRenderer;

    public ItemInfoFactory(ReviewNode node,
                           AuthorsRepository repo,
                           ReviewClusterRenderer renderer) {
        mNode = node;
        mRepo = repo;
        mRenderer = renderer;
    }

    @Override
    public MapInfoWindow newInfoWindow(Marker marker) {
        DataLocation location = mRenderer.getClusterItem(marker).getLocation();
        SelectorEqualsReviewId selector = new SelectorEqualsReviewId(location.getReviewId());

        return new VhMapInfoWindow(location, mNode, new ReviewSelector(selector), mRepo,
                new MapInfoWindow.InfoUpdateListener(marker));
    }
}
