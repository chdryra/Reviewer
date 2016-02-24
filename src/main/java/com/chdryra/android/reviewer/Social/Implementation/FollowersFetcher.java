/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Implementation;

import android.os.AsyncTask;

import com.chdryra.android.reviewer.Social.Interfaces.FollowersListener;

/**
 * Created by: Rizwan Choudrey
 * On: 12/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FollowersFetcher {
    private FollowersGetter mGetter;

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

    public void getFollowers(FollowersListener listener) {
        new FollowersTask(listener).execute();
    }

    private class FollowersTask extends AsyncTask<Void, Void, Integer> {
        private FollowersListener mListener;

        public FollowersTask(FollowersListener listener ) {
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
