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
import com.chdryra.android.reviewer.Social.Interfaces.SocialPublisher;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPublisherListener;

/**
 * Created by: Rizwan Choudrey
 * On: 12/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
class AsyncSocialPublisher {
    private final SocialPublisher mPublisher;

    public AsyncSocialPublisher(SocialPublisher publisher) {
        mPublisher = publisher;
    }

    public String getName() {
        return mPublisher.getPlatformName();
    }

    public void publish(Review review, SocialPublisherListener listener) {
        new PublisherTask(review, listener).execute();
    }

    private class PublisherTask extends AsyncTask<Void, Void, PublishResults> {
        private final Review mReview;
        private final SocialPublisherListener mListener;

        public PublisherTask(Review review, SocialPublisherListener listener) {
            mReview = review;
            mListener = listener;
        }

        @Override
        protected PublishResults doInBackground(Void... params) {
            return mPublisher.publish(mReview);
        }

        @Override
        protected void onPostExecute(PublishResults publishResults) {
            mListener.onPublished(publishResults);
        }
    }
}
