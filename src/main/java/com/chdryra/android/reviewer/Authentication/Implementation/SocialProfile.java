/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;

import java.util.List;

/**
 * Created by: Rizwan Choudrey
 * On: 25/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class SocialProfile {
    private final List<AuthorId> mFollowing;
    private final List<AuthorId> mFollowers;

    public SocialProfile(List<AuthorId> following, List<AuthorId> followers) {
        mFollowing = following;
        mFollowers = followers;
    }

    public List<AuthorId> getFollowing() {
        return mFollowing;
    }

    public List<AuthorId> getFollowers() {
        return mFollowers;
    }
}
