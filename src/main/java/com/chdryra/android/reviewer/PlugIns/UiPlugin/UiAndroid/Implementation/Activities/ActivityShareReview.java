/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Activities;

import android.app.Activity;
import android.content.Intent;

import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryShareScreenView;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.SocialReviewSharer;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.Social.Implementation.PublishingAction;
import com.chdryra.android.reviewer.Social.Interfaces.AuthorisationListener;
import com.chdryra.android.reviewer.Social.Interfaces.PlatformAuthoriser;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPlatform;
import com.chdryra.android.reviewer.Social.Interfaces.LoginUi;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.LaunchableUiLauncher;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 19/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ActivityShareReview extends ActivityReviewView implements PlatformAuthoriser {
    private static final int SOCIAL = R.string.activity_title_share;
    private LoginUi mAuthUi;

    @Override
    protected ReviewView createReviewView() {
        ApplicationInstance app = ApplicationInstance.getInstance(this);

        FactoryShareScreenView factory = new FactoryShareScreenView();
        return factory.buildView(getResources().getString(SOCIAL),
                app.getSocialPlatformList(),
                app.getReviewBuilderAdapter(), this,
                new SocialReviewSharerAndroid(ActivityUsersFeed.class));
    }

    @Override
    public void seekAuthorisation(SocialPlatform<?> platform, AuthorisationListener listener) {
        LaunchableUiLauncher launcher = ApplicationInstance.getInstance(this).getUiLauncher();
        mAuthUi = platform.getLoginUi(this, new ActivitySocialAuthUi(), listener);
        mAuthUi.launchUi(launcher);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mAuthUi.onActivityResult(requestCode, resultCode, data);
    }

    private class SocialReviewSharerAndroid implements SocialReviewSharer {
        private final Class<? extends Activity> mActivityToPublish;

        private SocialReviewSharerAndroid(Class<? extends Activity> activityToPublish) {
            mActivityToPublish = activityToPublish;
        }

        @Override
        public void share(ReviewId published, ArrayList<String> selectedPublishers) {
            Activity activity = ActivityShareReview.this;

            Intent intent = new Intent(activity, mActivityToPublish);
            intent.putExtra(PublishingAction.PUBLISHED, published.toString());
            intent.putStringArrayListExtra(PublishingAction.PLATFORMS, selectedPublishers);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);

            activity.startActivity(intent);
        }
    }
}
