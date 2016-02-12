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
import com.chdryra.android.reviewer.Social.Interfaces.SocialPublisher;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPublisherListener;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 12/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class SocialPlatformsPublisher implements SocialPublisherListener{
    private Map<String, SocialPublisher> mPublishers;

    public SocialPlatformsPublisher(Collection<SocialPublisher> publishers) {
        mPublishers = new HashMap<>();
        for(SocialPublisher publisher : publishers) {
            mPublishers.put(publisher.getName(), publisher);
        }
    }

    public void publish(Review review, TagsManager tagsManager, Iterable<String> platforms,
                        Activity activity, SocialPublisherListener listener) {
        for(String platform : platforms) {
            SocialPublisher publisher = mPublishers.get(platform);
            if(publisher != null) doAsyncPublish(publisher, review, tagsManager, activity);
        }
    }

    private void doAsyncPublish(SocialPublisher publisher, Review review, TagsManager tagsManager,
                                Activity activity) {
        AsyncSocialPublisher asyncPublisher = new AsyncSocialPublisher(publisher);
        asyncPublisher.publish(review, tagsManager, activity, this);
    }

    @Override
    public void onPublished(PublishResults results) {

    }
}
