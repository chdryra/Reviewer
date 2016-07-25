/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;

import java.util.List;

/**
 * Created by: Rizwan Choudrey
 * On: 25/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class SocialProfile {
    private List<DataAuthor> mFollowing;
    private List<DataAuthor> mFollowers;

    public SocialProfile(List<DataAuthor> following, List<DataAuthor> followers) {
        mFollowing = following;
        mFollowers = followers;
    }

    public List<DataAuthor> getFollowing() {
        return mFollowing;
    }

    public List<DataAuthor> getFollowers() {
        return mFollowers;
    }
}
