/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation;


import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;

/**
 * Created by: Rizwan Choudrey
 * On: 06/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class Follow {
    private AuthorId mFollower;
    private AuthorId mFollowing;

    public Follow(AuthorId follower, AuthorId following) {
        mFollower = follower;
        mFollowing = following;
    }

    public AuthorId getFollower() {
        return mFollower;
    }

    public AuthorId getFollowing() {
        return mFollowing;
    }
}
