/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.reviewer.Application.ApplicationInstance;
import com.chdryra.android.reviewer.Application.Strings;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.BannerButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.ContextualButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.RatingBarAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.SubjectAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ActivityResultListener;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.BannerButtonActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.MenuActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.RatingBarActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.ReviewViewActions;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.SubjectActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSocialPlatform;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSocialPlatformList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewDefault;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewParams;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewPerspective;
import com.chdryra.android.reviewer.Social.Implementation.SocialPlatformList;
import com.chdryra.android.reviewer.Social.Interfaces.AuthorisationListener;
import com.chdryra.android.reviewer.Social.Interfaces.LoginUi;
import com.chdryra.android.reviewer.Social.Interfaces.PlatformAuthoriser;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPlatform;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.UiLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;

/**
 * Created by: Rizwan Choudrey
 * On: 01/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class PresenterReviewPublish implements ActivityResultListener{
    private static final int FEED = RequestCodeGenerator.getCode("FeedScreen");
    private final ApplicationInstance mApp;
    private final ReviewView<GvSocialPlatform> mView;
    private LoginUi mAuthUi;

    private PresenterReviewPublish(ApplicationInstance app,
                                   ReviewView<GvSocialPlatform> view) {
        mApp = app;
        mView = view;
    }

    public ReviewView<GvSocialPlatform> getView() {
        return mView;
    }

    public void seekAuthorisation(SocialPlatform<?> platform,
                                  LaunchableUi authLaunchable,
                                  AuthorisationListener listener) {
        UiLauncher launcher = mApp.getUiLauncher();
        mAuthUi = platform.getLoginUi(authLaunchable, listener);
        mAuthUi.launchUi(launcher);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(mAuthUi != null) mAuthUi.onActivityResult(requestCode, resultCode, data);
    }

    public void onQueuedToPublish(Activity activity) {
        mApp.getCurrentScreen().showToast(Strings.Toasts.PUBLISHING);
        mApp.getConfigUi().getUsersFeed();
        UiLauncher uiLauncher = mApp.getUiLauncher();
        uiLauncher.launchAndClearBackStack(mApp.getConfigUi().getUsersFeed(), FEED);
        activity.finish();
    }

    public void onFailedToQueue(CallbackMessage message) {
        mApp.getCurrentScreen().showToast(Strings.Toasts.PROBLEM_PUBLISHING + ": " + message.getMessage());
    }

    public static class Builder {
        private final ApplicationInstance mApp;

        public Builder(ApplicationInstance app) {
            mApp = app;
        }

        public PresenterReviewPublish build(ReviewViewAdapter<?> reviewAdapter,
                                            PlatformAuthoriser authoriser,
                                            PublishAction.PublishCallback publishCallback) {
            GvSocialPlatformList platforms = getGvSocialPlatforms(mApp.getSocialPlatformList());
            PublishScreenAdapter adapter = new PublishScreenAdapter(platforms, reviewAdapter);
            PublishAction publishAction = new PublishAction(mApp, publishCallback);

            ReviewViewActions<GvSocialPlatform> actions
                    = getActions(authoriser, platforms, publishAction);

            ReviewViewPerspective<GvSocialPlatform> perspective =
                    new ReviewViewPerspective<>(adapter, actions, getParams());

            return new PresenterReviewPublish(mApp, new ReviewViewDefault<>(perspective));
        }

        @NonNull
        private ReviewViewParams getParams() {
            ReviewViewParams params = new ReviewViewParams();
            params.getGridViewParams().setGridAlpha(ReviewViewParams.GridViewAlpha.TRANSPARENT);

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
        private ReviewViewActions<GvSocialPlatform> getActions(PlatformAuthoriser authoriser,
                                                               GvSocialPlatformList platforms,
                                                               PublishAction publishAction) {
            String title = Strings.Screens.SHARE;
            SubjectAction<GvSocialPlatform> sa = new SubjectActionNone<>();
            RatingBarAction<GvSocialPlatform> rb = new RatingBarActionNone<>();
            BannerButtonAction<GvSocialPlatform> bba = new BannerButtonActionNone<>(title);
            GridItemShareScreen gi = new GridItemShareScreen(authoriser);
            MenuAction<GvSocialPlatform> ma = new MenuActionNone<>(title);
            ContextualButtonAction<GvSocialPlatform> cb = new PublishButton(platforms, publishAction);
            return new ReviewViewActions<>(sa, rb, bba, gi, ma, cb);
        }
    }
}
