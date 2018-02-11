/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Authentication.Interfaces;

import com.chdryra.android.startouch.Authentication.Implementation.AuthenticatedUser;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.HasAuthorId;

/**
 * Created by: Rizwan Choudrey
 * On: 25/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface UserAccount extends HasAuthorId{
    AuthenticatedUser getAccountHolder();

    SocialProfileRef getSocialProfile();

    AuthorProfileRef getAuthorProfile();
}
