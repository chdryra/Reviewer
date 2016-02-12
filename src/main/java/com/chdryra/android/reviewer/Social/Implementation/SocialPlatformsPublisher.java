/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Implementation;

import android.app.Activity;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSocialPlatform;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.TagsManager;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPublisher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 12/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class SocialPlatformsPublisher {
    private Map<String, SocialPublisher> mPublishers;
    private ArrayList<String> mRegistered;

    public SocialPlatformsPublisher(Collection<SocialPublisher> publishers) {
        mPublishers = new HashMap<>();
        for(SocialPublisher publisher : publishers) {
            mPublishers.put(publisher.getName(), publisher);
        }
    }

    public void registerPublisher(DataSocialPlatform platform) {
        String name = platform.getName();
        if(mPublishers.containsKey(name)) mRegistered.add(name);
    }

    public void unregisterPublisher(DataSocialPlatform platform) {
        mRegistered.remove(platform.getName());
    }

    public void publish(Review review, TagsManager tagsManager, Activity activity) {
        for(String publisher : mRegistered) {
            mPublishers.get(publisher).publish(review, tagsManager, activity);
        }
    }
}
