/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.BannerButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.RatingBarAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.SubjectAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.GridItemShareScreen;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.ShareCommand;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.PublishButtonModifier;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.ShareScreenAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.SocialReviewSharer;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.BannerButtonActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.MenuActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.RatingBarActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.ReviewViewActions;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.SubjectActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSocialPlatform;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSocialPlatformList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewDefault;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewModifier;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewParams;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewPerspective;

import com.chdryra.android.reviewer.NetworkServices.Social.Implementation.SocialPlatformList;
import com.chdryra.android.reviewer.NetworkServices.Social.Interfaces.PlatformAuthoriser;
import com.chdryra.android.reviewer.NetworkServices.Social.Interfaces.SocialPlatform;

/**
 * Created by: Rizwan Choudrey
 * On: 19/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryShareScreenView {

    public ReviewView buildView(String title,
                                SocialPlatformList socialPlatforms,
                                ReviewViewAdapter<?> reviewViewAdapter,
                                PlatformAuthoriser authoriser,
                                SocialReviewSharer sharer) {
        GvSocialPlatformList platforms = getGvSocialPlatforms(socialPlatforms);

        ShareScreenAdapter adapter = new ShareScreenAdapter(platforms, reviewViewAdapter);
        ShareCommand command = new ShareCommand(platforms, sharer);
        ReviewViewModifier modifier = new PublishButtonModifier(command);

        ReviewViewActions<GvSocialPlatform> actions = getActions(title, authoriser);
        ReviewViewPerspective<GvSocialPlatform> perspective =
                new ReviewViewPerspective<>(adapter, actions, getParams(), modifier);

        return new ReviewViewDefault<>(perspective);
    }

    @NonNull
    private ReviewViewParams getParams() {
        ReviewViewParams params = new ReviewViewParams();
        params.setGridAlpha(ReviewViewParams.GridViewAlpha.TRANSPARENT);

        return params;
    }

    @NonNull
    private GvSocialPlatformList getGvSocialPlatforms(SocialPlatformList platforms) {
        GvSocialPlatformList list = new GvSocialPlatformList();
        for(SocialPlatform platform : platforms) {
            list.add(new GvSocialPlatform(platform));
        }
        return list;
    }

    @NonNull
    private ReviewViewActions<GvSocialPlatform> getActions(String title, PlatformAuthoriser authoriser) {
        SubjectAction<GvSocialPlatform> sa = new SubjectActionNone<>();
        RatingBarAction<GvSocialPlatform> rb = new RatingBarActionNone<>();
        BannerButtonAction<GvSocialPlatform> bba = new BannerButtonActionNone<>(title);
        GridItemShareScreen gi = new GridItemShareScreen(authoriser);
        MenuAction<GvSocialPlatform> ma = new MenuActionNone<>(title);

        return new ReviewViewActions<>(sa, rb, bba, gi, ma);
    }
}
