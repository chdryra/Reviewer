/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation;

import android.view.View;

import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvAuthor;

/**
 * Created by: Rizwan Choudrey
 * On: 18/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GridItemLaunchAuthor extends GridItemLauncher<GvAuthor> {
    public GridItemLaunchAuthor(FactoryReviewView launchableFactory) {
        super(launchableFactory);
    }

    @Override
    public void onGridItemClick(GvAuthor item, int position, View v) {
        getApp().newReviewLauncher().launchReviews(item.getAuthorId());
    }

    @Override
    public void onGridItemLongClick(GvAuthor item, int position, View v) {
        onGridItemClick(item, position, v);
    }
}
