
/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Authentication.Implementation;


import com.chdryra.android.startouch.Authentication.Interfaces.SocialProfile;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumAuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by: Rizwan Choudrey
 * On: 16/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class SocialProfileDefault implements SocialProfile {
    private AuthorId mAuthorId;
    private List<AuthorId> mFollowing;
    private List<AuthorId> mFollowers;

    public SocialProfileDefault() {
        this(new DatumAuthorId(), new ArrayList<AuthorId>(), new ArrayList<AuthorId>());
    }

    public SocialProfileDefault(AuthorId authorId, List<AuthorId> following, List<AuthorId>
            followers) {
        mAuthorId = authorId;
        mFollowing = following;
        mFollowers = followers;
    }

    @Override
    public AuthorId getAuthorId() {
        return mAuthorId;
    }

    @Override
    public List<AuthorId> getFollowing() {
        return mFollowing;
    }

    @Override
    public List<AuthorId> getFollowers() {
        return mFollowers;
    }
}
