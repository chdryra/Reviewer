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
import com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Implementation.DialogAuthSharing;
import com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Utils.FactoryAuthorisationSeeker;
import com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Utils.PlatformAuthorisationSeeker;
import com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Utils.DialogAuthorisationSeeker;
import com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Utils.PublishingAction;
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
        PlatformAuthoriser, DialogAuthSharing.AuthorisationListener {
    private static final int SOCIAL = R.string.activity_title_share;
    private PlatformAuthorisationSeeker mSeeker;
    private FactoryAuthorisationSeeker mSeekerFactory;

    @Override
    protected ReviewView createReviewView() {
        ApplicationInstance app = ApplicationInstance.getInstance(this);

        mSeekerFactory = new FactoryAuthorisationSeeker();

        FactoryShareScreenView factory = new FactoryShareScreenView();
        return factory.buildView(getResources().getString(SOCIAL),
                app.getSocialPlatformList(),
                app.getReviewBuilderAdapter(),
                this,
                new SocialReviewSharerAndroid(ActivityFeed.class));
    }

    @Override
    public void seekAuthorisation(SocialPlatform<?> platform, AuthorisationListener listener) {
        mSeeker = mSeekerFactory.newAuthorisationSeeker(this, platform, listener);
        mSeeker.seekAuthorisation();
    }

    @Override
    public void onAuthorisationCallback(OAuthRequest response) {
        ((DialogAuthorisationSeeker)mSeeker).onAuthorisationCallback(response);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mSeeker.onActivityResult(requestCode, resultCode, data);
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
