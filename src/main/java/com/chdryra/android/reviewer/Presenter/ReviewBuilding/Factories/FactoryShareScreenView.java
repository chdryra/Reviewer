/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 19 March, 2015
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories;

import com.chdryra.android.reviewer.Model.Interfaces.Social.SocialPlatformList;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.BannerButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.RatingBarAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.SubjectAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.ShareScreenAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.ShareScreenGridItem;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.ShareScreenModifier;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .BannerButtonActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.MenuActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .RatingBarActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .ReviewViewActions;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .SubjectActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvSocialPlatform;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvSocialPlatformList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewDefault;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewParams;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View
        .ReviewViewPerspective;

/**
 * Created by: Rizwan Choudrey
 * On: 19/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryShareScreenView {

    public ReviewView buildView(String title, SocialPlatformList socialPlatforms,
                                ReviewViewAdapter<?> reviewViewAdapter) {
        GvSocialPlatformList platforms = new GvSocialPlatformList(socialPlatforms);
        ShareScreenAdapter adapter = new ShareScreenAdapter(platforms, reviewViewAdapter);

        SubjectAction<GvSocialPlatform> subjectAction = new SubjectActionNone<>();
        RatingBarAction<GvSocialPlatform> rb = new RatingBarActionNone<>();
        BannerButtonAction<GvSocialPlatform> bannerButton
                = new BannerButtonActionNone<>(title);
        ShareScreenGridItem gridItem = new ShareScreenGridItem();
        MenuAction<GvSocialPlatform> menuAction
                = new MenuActionNone<>(title);

        ReviewViewActions<GvSocialPlatform> actions
                = new ReviewViewActions<>(subjectAction, rb, bannerButton, gridItem, menuAction);

        ReviewViewParams params = new ReviewViewParams();
        params.setGridAlpha(ReviewViewParams.GridViewAlpha.TRANSPARENT);

        ReviewViewPerspective<GvSocialPlatform> perspective =
                new ReviewViewPerspective<>(adapter, actions, params, new ShareScreenModifier());

        return new ReviewViewDefault<>(perspective);
    }
}
