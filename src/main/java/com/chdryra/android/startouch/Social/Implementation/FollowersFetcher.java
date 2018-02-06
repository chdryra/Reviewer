/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Social.Implementation;

import android.os.AsyncTask;

import com.chdryra.android.startouch.Social.Interfaces.SocialPlatform;

/**
 * Created by: Rizwan Choudrey
 * On: 12/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
class FollowersFetcher {
    private final FollowersGetter mGetter;

    public interface FollowersGetter {
        String getPlatformName();

        int getFollowers();
    }

    public FollowersFetcher(FollowersGetter getter) {
        mGetter = getter;
    }

    public String getName() {
        return mGetter.getPlatformName();
    }

    public void getFollowers(SocialPlatform.FollowersListener listener) {
        new FollowersTask(listener).execute();
    }

    private class FollowersTask extends AsyncTask<Void, Void, Integer> {
        private final SocialPlatform.FollowersListener mListener;

        public FollowersTask(SocialPlatform.FollowersListener listener) {
            mListener = listener;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            return mGetter.getFollowers();
        }

        @Override
        protected void onPostExecute(Integer followers) {
            mListener.onNumberFollowers(followers);
        }
    }
}
