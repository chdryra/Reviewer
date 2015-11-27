/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 19 March, 2015
 */

package com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Activities;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.Interfaces.ReviewViewAdapter;
import com.chdryra.android.reviewer.Model.Interfaces.SocialPlatformList;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvSocialPlatform;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvSocialPlatformList;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.BannerButtonActionNone;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.MenuActionNone;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.RatingBarActionNone;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.ReviewViewActions;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.ReviewViewDefault;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.ReviewViewParams;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.ReviewViewPerspective;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.ShareScreenAdapter;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.ShareScreenGridItem;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.ShareScreenModifier;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.SubjectActionNone;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Interfaces.BannerButtonAction;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Interfaces.MenuAction;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Interfaces.RatingBarAction;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Interfaces.ReviewView;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Interfaces.SubjectAction;

/**
 * Created by: Rizwan Choudrey
 * On: 19/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class BuilderShareScreenView {

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
                new ReviewViewPerspective<>(adapter, params, actions, new ShareScreenModifier());

        return new ReviewViewDefault<>(perspective);
    }
}