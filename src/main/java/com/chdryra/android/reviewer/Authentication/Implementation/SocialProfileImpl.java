/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Implementation;

import com.chdryra.android.reviewer.Authentication.Interfaces.SocialProfile;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefAuthorList;

/**
 * Created by: Rizwan Choudrey
 * On: 25/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class SocialProfileImpl implements SocialProfile {
    private AuthorId mAuthorId;
    private final RefAuthorList mFollowing;

    public SocialProfileImpl(AuthorId authorId, RefAuthorList following) {
        mAuthorId = authorId;
        mFollowing = following;
    }

    @Override
    public AuthorId getAuthorId() {
        return mAuthorId;
    }

    @Override
    public RefAuthorList getFollowing() {
        return mFollowing;
    }
}
