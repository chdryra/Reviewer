/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation;


import com.chdryra.android.startouch.Authentication.Interfaces.SocialProfile;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;

import java.util.List;

/**
 * Created by: Rizwan Choudrey
 * On: 16/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ProfileSocial {
    public static final String FOLLOWING = "Following";
    public static final String FOLLOWERS = "Followers";

    private List<AuthorId> following;
    private List<AuthorId> followers;

    public ProfileSocial() {
    }

    public ProfileSocial(SocialProfile profile) {
        this.following = profile.getFollowing();
        this.followers = profile.getFollowers();
    }

    public List<AuthorId> getFollowing() {
        return following;
    }

    public List<AuthorId> getFollowers() {
        return followers;
    }
}
