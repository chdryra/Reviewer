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

import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;

//TODO remove android activity and Intent dependency
public class PublishButtonAction {
    private final Class<? extends Activity> mActivityOnPublish;

    public PublishButtonAction(Class<? extends Activity> activityOnPublish) {
        mActivityOnPublish = activityOnPublish;
    }

    public void onPublishButtonPressed(Activity activity) {
        Review review = publishReviewBuilder(activity);
        returnToFeedScreen(activity);
    }

    public void returnToFeedScreen(Activity activity) {
        Intent intent = new Intent(activity, mActivityOnPublish);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    public Review publishReviewBuilder(Activity activity) {
        return ApplicationInstance.getInstance(activity).publishReviewBuilder();
    }
}