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

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 12/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class BatchSocialPublisher implements SocialPublisherListener{
    private Collection<SocialPublisher> mPublishers;
    private ArrayList<PublishResults> mResults;
    private BatchPublisherListener mListener;

    public interface BatchPublisherListener {
        void onPublished(Collection<PublishResults> results);
    }

    public BatchSocialPublisher(Collection<SocialPublisher> publishers) {
        mPublishers = publishers;
    }

    public void publish(Review review, TagsManager tagsManager, Activity activity,
                        BatchPublisherListener listener) {
        mListener = listener;
        mResults = new ArrayList<>();
        for(SocialPublisher publisher : mPublishers) {
            doAsyncPublish(publisher, review, tagsManager, activity);
        }
    }

    private void doAsyncPublish(SocialPublisher publisher, Review review, TagsManager tagsManager,
                                Activity activity) {
        AsyncSocialPublisher asyncPublisher = new AsyncSocialPublisher(publisher);
        asyncPublisher.publish(review, tagsManager, activity, this);
    }

    @Override
    public void onPublished(PublishResults results) {
        mResults.add(results);
        if(mResults.size() == mPublishers.size()) {
            mListener.onPublished(mResults);
            mListener = null;
        }
    }
}
