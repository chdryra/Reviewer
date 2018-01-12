/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces;

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface DataSocialPlatform {
    String TYPE_NAME = "share";
    String DATA_NAME = "share";

    interface FollowersListener {
        void onNumberFollowers(int followers);
    }

    String getName();

    void getFollowers(FollowersListener listener);
}
