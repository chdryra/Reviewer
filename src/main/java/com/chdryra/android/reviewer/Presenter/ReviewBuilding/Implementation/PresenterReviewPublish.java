/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.reviewer.Application.ApplicationInstance;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.BannerButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.RatingBarAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.SubjectAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
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
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View
        .ReviewViewModifier;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewParams;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View
        .ReviewViewPerspective;
import com.chdryra.android.reviewer.Social.Implementation.SocialPlatformList;
import com.chdryra.android.reviewer.Social.Interfaces.PlatformAuthoriser;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPlatform;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.UiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 01/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class PresenterReviewPublish {
    private static final int FEED = RequestCodeGenerator.getCode("FeedScreen");
    private ApplicationInstance mApp;
    private ReviewView<GvSocialPlatform> mView;

    private PresenterReviewPublish(ApplicationInstance app,
                                   ReviewView<GvSocialPlatform> view) {
        mApp = app;
        mView = view;
    }

    public ReviewView<GvSocialPlatform> getView() {
        return mView;
    }

    public void  onQueuedToPublish(Activity activity) {
        mApp.getConfigUi().getFeedConfig();
        UiLauncher uiLauncher = mApp.getUiLauncher();
        uiLauncher.launch(mApp.getConfigUi().getFeedConfig(), FEED);
        activity.finish();
    }

    public static class Builder {
        private ApplicationInstance mApp;

        public Builder(ApplicationInstance app) {
            mApp = app;
        }

        public PresenterReviewPublish build(ReviewViewAdapter<?> reviewAdapter,
                                            PlatformAuthoriser authoriser,
                                            PublishAction.PublishCallback publishCallback,
                                            String title) {
            GvSocialPlatformList platforms = getGvSocialPlatforms(mApp.getSocialPlatformList());
            PublishScreenAdapter adapter = new PublishScreenAdapter(platforms, reviewAdapter);
            PublishAction publishAction = new PublishAction(mApp, publishCallback);
            ReviewViewModifier modifier = new PublishButton(platforms, publishAction);

            ReviewViewPerspective<GvSocialPlatform> perspective =
                    new ReviewViewPerspective<>(adapter, getActions(title, authoriser), getParams(), modifier);

            return new PresenterReviewPublish(mApp, new ReviewViewDefault<>(perspective));
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
}
