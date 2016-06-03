/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import com.chdryra.android.reviewer.Application.ApplicationInstance;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.BannerButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.RatingBarAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.SubjectAction;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .BannerButtonActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.GridItemFeedScreen;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.MenuActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.RatingBarExpandGrid;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.SubjectActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReview;
import com.chdryra.android.reviewer.View.Configs.ConfigUi;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;

/**
 * Created by: Rizwan Choudrey
 * On: 03/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewsListActions {
    public PresenterReviewsList.Actions getDefaultActions(ApplicationInstance app) {
        FactoryReviewView launchableFactory = app.getLaunchableFactory();
        ConfigUi configUi = app.getConfigUi();
        LaunchableUi reviewBuildUi = configUi.getBuildReview().getLaunchable();

        GridItemFeedScreen gi = new GridItemFeedScreen(launchableFactory,
                configUi.getShareEdit().getLaunchable(), reviewBuildUi);

        SubjectAction<GvReview> sa = new SubjectActionNone<>();

        RatingBarAction<GvReview> rb = new RatingBarExpandGrid<>(launchableFactory);

        BannerButtonAction<GvReview> bba = new BannerButtonActionNone<>();

        MenuAction<GvReview> ma = new MenuActionNone<>();

        return new PresenterReviewsList.Actions(sa, rb, bba, gi, ma);
    }
}
