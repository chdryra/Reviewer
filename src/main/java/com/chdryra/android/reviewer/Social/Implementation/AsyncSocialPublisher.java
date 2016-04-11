/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Implementation;

import android.os.AsyncTask;

import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPublisherListener;

/**
 * Created by: Rizwan Choudrey
 * On: 12/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class AsyncSocialPublisher {
    private SyncSocialPublisher mPublisher;

    public interface SyncSocialPublisher {
        String getPlatformName();

        PublishResults publish(Review review, TagsManager tagsManager);
    }

    public AsyncSocialPublisher(SyncSocialPublisher publisher) {
        mPublisher = publisher;
    }

    public String getName() {
        return mPublisher.getPlatformName();
    }

    public void publish(Review review, TagsManager tagsManager, SocialPublisherListener listener) {
        new PublisherTask(review, tagsManager, listener).execute();
    }

    private class PublisherTask extends AsyncTask<Void, Void, PublishResults> {
        private final Review mReview;
        private final TagsManager mTagsManager;
        private SocialPublisherListener mListener;

        public PublisherTask(Review review, TagsManager tagsManager,
                             SocialPublisherListener listener) {
            mReview = review;
            mTagsManager = tagsManager;
            mListener = listener;
        }

        @Override
        protected PublishResults doInBackground(Void... params) {
            return mPublisher.publish(mReview, mTagsManager);
        }

        @Override
        protected void onPostExecute(PublishResults publishResults) {
            mListener.onPublished(publishResults);
        }
    }
}
