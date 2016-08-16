/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Interfaces;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticatedUser;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticationError;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthorProfile;
import com.chdryra.android.reviewer.Authentication.Implementation.SocialProfile;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.AuthorId;

/**
 * Created by: Rizwan Choudrey
 * On: 25/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface UserAccount {
    interface UpdateAuthorProfileCallback {
        void onAuthorProfileUpdated(AuthorProfile profile, @Nullable AuthenticationError error);
    }

    interface GetAuthorProfileCallback {
        void onAuthorProfile(AuthorProfile profile, @Nullable AuthenticationError error);
    }

    interface GetSocialProfileCallback {
        void onSocialProfile(SocialProfile profile, @Nullable AuthenticationError error);
    }

    AuthenticatedUser getAccountHolder();

    AuthorId getAuthorId();

    void getAuthorProfile(GetAuthorProfileCallback callback);

    void updateAuthorProfile(AuthorProfile newProfile, UpdateAuthorProfileCallback callback);

    void getSocialProfile(GetSocialProfileCallback callback);
}
