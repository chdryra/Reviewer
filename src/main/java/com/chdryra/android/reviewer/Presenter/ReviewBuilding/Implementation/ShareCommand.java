/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.SocialReviewSharer;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSocialPlatform;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSocialPlatformList;

import java.util.ArrayList;

public class ShareCommand {
    private final GvSocialPlatformList mPlatforms;
    private final SocialReviewSharer mSharer;

    public ShareCommand(GvSocialPlatformList platforms, SocialReviewSharer sharer) {
        mPlatforms = platforms;
        mSharer = sharer;
    }

    public void shareReview(ReviewId reviewId) {
        ArrayList<String> publishers = new ArrayList<>();
        for(GvSocialPlatform platform : mPlatforms) {
            if(platform.isChosen()) publishers.add(platform.getName());
        }

        mSharer.share(reviewId, publishers);
    }
}