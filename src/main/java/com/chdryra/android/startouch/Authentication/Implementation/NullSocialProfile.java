/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Authentication.Implementation;

import com.chdryra.android.startouch.Authentication.Interfaces.ProfileSocial;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumAuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.startouch.DataDefinitions.References.Implementation.NullDataReference;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.RefAuthorList;

import java.util.List;

/**
 * Created by: Rizwan Choudrey
 * On: 25/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class NullSocialProfile implements ProfileSocial {
    private final static AuthorId AUTHOR_ID = new DatumAuthorId();
    private final RefAuthorList FOLLOWING = new NullRefAuthorList();
    private final RefAuthorList FOLLOWERS = new NullRefAuthorList();

    @Override
    public AuthorId getAuthorId() {
        return AUTHOR_ID;
    }

    @Override
    public RefAuthorList getFollowing() {
        return FOLLOWING;
    }

    @Override
    public RefAuthorList getFollowers() {
        return FOLLOWERS;
    }

    @Override
    public void followUnfollow(AuthorId authorId, FollowUnfollow type, FollowCallback callback) {

    }

    private static class NullRefAuthorList extends
            NullDataReference.NullList<AuthorId, List<AuthorId>>
    implements RefAuthorList{
    }
}
