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
import com.chdryra.android.reviewer.Social.Interfaces.AsyncSocialPlatform;
import com.chdryra.android.reviewer.Social.Interfaces.FollowersListener;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPlatform;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPublisherListener;

/**
 * Created by: Rizwan Choudrey
 * On: 12/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class AsyncSocialPlatformImpl implements AsyncSocialPlatform {
    private SocialPlatform mPlatform;

    public AsyncSocialPlatformImpl(SocialPlatform platform) {
        mPlatform = platform;
    }

    @Override
    public String getName() {
        return mPlatform.getName();
    }

    @Override
    public void publish(Review review, TagsManager tagsManager, Activity activity,
                                  SocialPublisherListener listener) {
        new PublisherTask(review, tagsManager, activity, listener).execute();
    }

    @Override
    public void getFollowers(FollowersListener listener) {
        new FollowersTask(listener).execute();
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
            return mPlatform.getPublisher().publish(mReview, mTagsManager, mActivity);
        }

        @Override
        protected void onPostExecute(PublishResults publishResults) {
            mListener.onPublished(publishResults);
        }
    }

    private class FollowersTask extends AsyncTask<Void, Void, Integer> {
        private FollowersListener mListener;

        public FollowersTask(FollowersListener listener ) {
            mListener = listener;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            return mPlatform.getPublisher().getFollowers();
        }

        @Override
        protected void onPostExecute(Integer followers) {
            mListener.onNumberFollowers(followers);
        }
    }
}
