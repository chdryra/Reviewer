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
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.LaunchOptionsCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvNode;

/**
 * Created by: Rizwan Choudrey
 * On: 18/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GridItemReviewsList extends GridItemLauncher<GvNode> {
    private final LaunchOptionsCommand mCommand;

    public GridItemReviewsList(FactoryReviewView launchableFactory, LaunchOptionsCommand command) {
        super(launchableFactory);
        mCommand = command;
    }

    @Override
    public void onGridItemLongClick(GvNode item, int position, View v) {
        mCommand.execute(item.getAuthorId(), getApp().newUiLauncher());
    }
}
