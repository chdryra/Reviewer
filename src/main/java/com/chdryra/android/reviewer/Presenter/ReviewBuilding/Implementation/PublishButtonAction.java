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
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvSocialPlatform;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvSocialPlatformList;

import java.util.ArrayList;

//TODO remove android activity and Intent dependency
public class PublishButtonAction {
    private final GvSocialPlatformList mPlatforms;
    private final Class<? extends Activity> mActivityOnPublish;

    public PublishButtonAction(GvSocialPlatformList platforms,
                               Class<? extends Activity> activityOnPublish) {
        mPlatforms = platforms;
        mActivityOnPublish = activityOnPublish;
    }

    public void onPublishButtonPressed(Activity activity) {
        Review review = publishReviewBuilder(activity);
        returnToFeedScreen(activity, review.getReviewId());
    }

    public void returnToFeedScreen(Activity activity, ReviewId published) {
        Intent intent = new Intent(activity, mActivityOnPublish);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(PublishingAction.PUBLISHED, published.toString());
        intent.putStringArrayListExtra(PublishingAction.PLATFORMS, getSelectedPublishers());

        activity.startActivity(intent);
    }

    public Review publishReviewBuilder(Activity activity) {
        return ApplicationInstance.getInstance(activity).publishReviewBuilder();
    }

    private ArrayList<String> getSelectedPublishers() {
        ArrayList<String> publishers = new ArrayList<>();
        for(GvSocialPlatform platform : mPlatforms) {
            if(platform.isChosen()) publishers.add(platform.getName());
        }

        return publishers;
    }
}