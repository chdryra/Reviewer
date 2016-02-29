/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Implementation;

import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.TagsManager;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPlatform;
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
    private Collection<SocialPlatform<?>> mPlatforms;
    private ArrayList<PublishResults> mResults;
    private BatchPublisherListener mListener;

    public interface BatchPublisherListener {
        void onPublished(Collection<PublishResults> results);
    }

    public BatchSocialPublisher(Collection<SocialPlatform<?>> platforms) {
        mPlatforms = platforms;
    }

    public void publish(Review review, TagsManager tagsManager, BatchPublisherListener listener) {
        mListener = listener;
        mResults = new ArrayList<>();
        for(SocialPlatform<?> platform : mPlatforms) {
            platform.publish(review, tagsManager, this);
        }
    }

    @Override
    public void onPublished(PublishResults results) {
        mResults.add(results);
        if(mResults.size() == mPlatforms.size()) {
            mListener.onPublished(mResults);
            mListener = null;
        }
    }
}
