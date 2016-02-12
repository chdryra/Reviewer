/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Implementation;

import android.app.Activity;
import android.os.AsyncTask;

import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.TagsManager;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPublisher;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPublisherListener;

/**
 * Created by: Rizwan Choudrey
 * On: 12/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class AsyncSocialPublisher {
    private SocialPublisher mPublisher;

    public AsyncSocialPublisher(SocialPublisher publisher) {
        mPublisher = publisher;
    }

    public String getName() {
        return mPublisher.getName();
    }

    public void publish(Review review, TagsManager tagsManager, Activity activity,
                                  SocialPublisherListener listener) {
        new PublisherTask(review, tagsManager, activity, listener).execute();
    }

    private class PublisherTask extends AsyncTask<Void, Void, PublishResults> {
        private final Review mReview;
        private final TagsManager mTagsManager;
        private Activity mActivity;
        private SocialPublisherListener mListener;

        public PublisherTask(Review review, TagsManager tagsManager, Activity activity,
                             SocialPublisherListener listener) {
            mReview = review;
            mTagsManager = tagsManager;
            mActivity = activity;
            mListener = listener;
        }

        @Override
        protected PublishResults doInBackground(Void... params) {
            return mPublisher.publish(mReview, mTagsManager, mActivity);
        }

        @Override
        protected void onPostExecute(PublishResults publishResults) {
            mListener.onPublished(publishResults);
        }
    }
}
