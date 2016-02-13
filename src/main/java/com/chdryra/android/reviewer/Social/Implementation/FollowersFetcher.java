/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Implementation;

import android.content.Context;
import android.os.AsyncTask;

import com.chdryra.android.reviewer.Social.Interfaces.FollowersListener;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPlatform;

/**
 * Created by: Rizwan Choudrey
 * On: 12/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FollowersFetcher {
    private SocialPlatform mPlatform;

    public FollowersFetcher(SocialPlatform platform) {
        mPlatform = platform;
    }

    public String getName() {
        return mPlatform.getName();
    }

    public void getFollowers(Context context, FollowersListener listener) {
        new FollowersTask(context, listener).execute();
    }

    private class FollowersTask extends AsyncTask<Void, Void, Integer> {
        private Context mContext;
        private FollowersListener mListener;

        public FollowersTask(Context context, FollowersListener listener ) {
            mContext = context;
            mListener = listener;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            return mPlatform.getPublisher().getFollowers(mContext);
        }

        @Override
        protected void onPostExecute(Integer followers) {
            mListener.onNumberFollowers(followers);
        }
    }
}
