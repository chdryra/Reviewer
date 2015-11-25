/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 19 March, 2015
 */

package com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Builders;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.Models.Social.Interfaces.SocialPlatformList;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvSocialPlatform;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvSocialPlatformList;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.BannerButtonActionNone;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.MenuActionNone;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.RatingBarActionNone;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.ShareScreenAdapter;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.ShareScreenGridItem;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.ShareScreenModifier;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.SubjectActionNone;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Interfaces.BannerButtonAction;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Interfaces.MenuAction;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Interfaces.RatingBarAction;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Interfaces.ReviewView;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Interfaces.SubjectAction;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.ReviewViewActions;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.ReviewViewDefault;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.ReviewViewParams;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.ReviewViewPerspective;

/**
 * Created by: Rizwan Choudrey
 * On: 19/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class BuilderShareScreen {

    public ReviewView buildView(String title, SocialPlatformList socialPlatforms,
                                ReviewBuilderAdapter builder) {
        GvSocialPlatformList platforms = new GvSocialPlatformList(socialPlatforms);
        ShareScreenAdapter adapter = new ShareScreenAdapter(platforms, builder);

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
