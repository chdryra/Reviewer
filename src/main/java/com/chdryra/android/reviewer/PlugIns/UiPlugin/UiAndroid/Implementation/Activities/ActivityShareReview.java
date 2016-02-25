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
import com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Implementation.DialogSocialLogin;

import com.chdryra.android.reviewer.Social.Interfaces.AuthorisationListener;
import com.chdryra.android.reviewer.Social.Interfaces.OAuthListener;
import com.chdryra.android.reviewer.Social.Factories.FactoryAuthorisationUi;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPlatformAuthUi;
import com.chdryra.android.reviewer.Social.Implementation.DefaultOAuthUi;
import com.chdryra.android.reviewer.Social.Implementation.PublishingAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryShareScreenView;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.SocialReviewSharer;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.Social.Implementation.OAuthRequest;
import com.chdryra.android.reviewer.Social.Interfaces.PlatformAuthoriser;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPlatform;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 19/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ActivityShareReview extends ActivityReviewView implements
        PlatformAuthoriser, OAuthListener {
    private static final int SOCIAL = R.string.activity_title_share;
    private SocialPlatformAuthUi mAuthUi;
    private FactoryAuthorisationUi mAuthUiFactory;

    @Override
    protected ReviewView createReviewView() {
        ApplicationInstance app = ApplicationInstance.getInstance(this);

        mAuthUiFactory = new FactoryAuthorisationUi(new DialogSocialLogin(),
                new ActivitySocialLogin(), app.getUiLauncher());

        FactoryShareScreenView factory = new FactoryShareScreenView();
        return factory.buildView(getResources().getString(SOCIAL),
                app.getSocialPlatformList(),
                app.getReviewBuilderAdapter(),
                this,
                new SocialReviewSharerAndroid(ActivityFeed.class));
    }

    @Override
    public void seekAuthorisation(SocialPlatform<?> platform, AuthorisationListener listener) {
        mAuthUi = mAuthUiFactory.newAuthorisationUi(this, platform, listener);
        mAuthUi.launchAuthorisationUi();
    }

    @Override
    public void onAuthorisationCallback(OAuthRequest response) {
        ((DefaultOAuthUi) mAuthUi).onAuthorisationCallback(response);
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
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(PublishingAction.PUBLISHED, published.toString());
            intent.putStringArrayListExtra(PublishingAction.PLATFORMS, selectedPublishers);

            activity.startActivity(intent);
        }
    }
}
