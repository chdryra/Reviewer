/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions
        .Implementation;


import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.LaunchBespokeViewCommand;
import com.chdryra.android.startouch.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.startouch.View.LauncherModel.Interfaces.UiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 16/12/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class GridItemBespokeView<T extends GvData> extends GridItemConfigLauncher<T> {
    private final LaunchBespokeViewCommand mBespokeView;

    public GridItemBespokeView(UiLauncher launcher,
                               FactoryReviewView launchableFactory,
                               LaunchableConfig dataConfig,
                               LaunchBespokeViewCommand bespokeView) {
        super(launcher, launchableFactory, dataConfig);
        mBespokeView = bespokeView;
    }

    @Override
    protected void launchViewerIfPossible(T item, int position) {
        mBespokeView.execute(position);
    }
}
