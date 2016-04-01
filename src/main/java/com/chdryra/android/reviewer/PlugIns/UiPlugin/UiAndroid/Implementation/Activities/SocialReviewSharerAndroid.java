/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Implementation.RepositoryMessage;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces.RepositoryMutableCallback;
import com.chdryra.android.reviewer.NetworkServices.Social.Implementation.PublishingAction;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.SocialReviewSharer;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 01/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
class SocialReviewSharerAndroid implements SocialReviewSharer, RepositoryMutableCallback {
    private final Context mContext;
    private final Class<? extends Activity> mActivityToPublish;
    private ArrayList<String> mSelectedPublishers;

    SocialReviewSharerAndroid(Context context, Class<? extends Activity> activityToPublish) {
        mContext = context;
        mActivityToPublish = activityToPublish;
    }

    @Override
    public void share(Review toPublish, ArrayList<String> selectedPublishers) {
        mSelectedPublishers = selectedPublishers;
        ApplicationInstance app = ApplicationInstance.getInstance(mContext);
        app.getLocalRepository().addReview(toPublish, this);
    }

    @Override
    public void onAdded(Review review, RepositoryMessage result) {
        Intent intent = new Intent(mContext, mActivityToPublish);
        intent.putExtra(PublishingAction.PUBLISHED, review.getReviewId().toString());
        intent.putStringArrayListExtra(PublishingAction.PLATFORMS, mSelectedPublishers);
        if (result.isError()) intent.putExtra(PublishingAction.RepoError, result.getMessage());
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);

        mContext.startActivity(intent);
    }

    @Override
    public void onRemoved(ReviewId reviewId, RepositoryMessage result) {

    }
}
