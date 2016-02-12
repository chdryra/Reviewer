/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Implementation;

import android.app.Activity;

import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.TagsManager;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPlatformPublisher;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 12/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class SocialPlatformsPublisher {
    private ArrayList<SocialPlatformPublisher> mPublishers;

    public SocialPlatformsPublisher() {
        mPublishers = new ArrayList<>();
    }

    public void registerPublisher(SocialPlatformPublisher publisher) {
        mPublishers.add(publisher);
    }

    public void unregisterPublisher(SocialPlatformPublisher publisher) {
        mPublishers.remove(publisher);
    }

    public void publish(Review review, TagsManager tagsManager, Activity activity) {
        for(SocialPlatformPublisher publisher : mPublishers) {
            publisher.publish(review, tagsManager, activity);
        }
    }
}
