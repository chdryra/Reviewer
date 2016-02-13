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
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.SocialReviewSharer;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.Social.Implementation.SocialPlatformList;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 19/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ActivityShareReview extends ActivityReviewView {
    @Override
    protected ReviewView createReviewView() {
        ApplicationInstance app = ApplicationInstance.getInstance(this);

        String title = getResources().getString(R.string.button_social);
        SocialPlatformList socialPlatforms = app.getSocialPlatformList();

        ReviewBuilderAdapter reviewInProgress = app.getReviewBuilderAdapter();
        if(reviewInProgress == null) throw new RuntimeException("Builder is null!");

        FactoryShareScreenView factory = new FactoryShareScreenView();

        return factory.buildView(title, socialPlatforms, reviewInProgress,
                new SocialReviewSharerAndroid(ActivityFeed.class));
    }

    private class SocialReviewSharerAndroid implements SocialReviewSharer {
        private final Class<? extends Activity> mActivityOnPublish;

        private SocialReviewSharerAndroid(Class<? extends Activity> activityToPublish) {
            mActivityOnPublish = activityToPublish;
        }

        @Override
        public void share(ReviewId published,
                          ArrayList<String> selectedPublishers) {
            Activity activity = ActivityShareReview.this;
            Intent intent = new Intent(activity, mActivityOnPublish);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(PublishingAction.PUBLISHED, published.toString());
            intent.putStringArrayListExtra(PublishingAction.PLATFORMS, selectedPublishers);

            activity.startActivity(intent);
        }
    }
}
