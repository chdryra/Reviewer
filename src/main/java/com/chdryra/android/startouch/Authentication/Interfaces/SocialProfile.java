/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Authentication.Interfaces;

import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;

import java.util.List;

/**
 * Created by: Rizwan Choudrey
 * On: 11/02/2018
 * Email: rizwan.choudrey@gmail.com
 */

public interface SocialProfile {
    AuthorId getAuthorId();

    List<AuthorId> getFollowing();

    List<AuthorId> getFollowers();
}
