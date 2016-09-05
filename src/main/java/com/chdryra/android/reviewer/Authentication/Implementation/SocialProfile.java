/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.NamedAuthor;

import java.util.List;

/**
 * Created by: Rizwan Choudrey
 * On: 25/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class SocialProfile {
    private final List<NamedAuthor> mFollowing;
    private final List<NamedAuthor> mFollowers;

    public SocialProfile(List<NamedAuthor> following, List<NamedAuthor> followers) {
        mFollowing = following;
        mFollowers = followers;
    }

    public List<NamedAuthor> getFollowing() {
        return mFollowing;
    }

    public List<NamedAuthor> getFollowers() {
        return mFollowers;
    }
}
