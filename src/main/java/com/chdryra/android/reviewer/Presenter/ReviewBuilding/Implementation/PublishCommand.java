/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvSocialPlatform;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvSocialPlatformList;

import java.util.ArrayList;

public class PublishCommand {
    private final GvSocialPlatformList mPlatforms;
    private final PublishAction mAction;

    public PublishCommand(GvSocialPlatformList platforms, PublishAction action) {
        mPlatforms = platforms;
        mAction = action;
    }

    public void publishReview(Review toPublish) {
        ArrayList<String> publishers = new ArrayList<>();
        for(GvSocialPlatform platform : mPlatforms) {
            if(platform.isChosen()) publishers.add(platform.getName());
        }

        mAction.publish(toPublish, publishers);
    }
}