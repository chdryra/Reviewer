/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation;



import android.graphics.Bitmap;
import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.Authentication.Factories.FactoryAuthorProfile;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticatedUser;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthorProfileSnapshot;

/**
 * Created by: Rizwan Choudrey
 * On: 09/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class UserProfileConverter {
    private final FactoryAuthorProfile mProfileFactory;

    public UserProfileConverter(FactoryAuthorProfile profileFactory) {
        mProfileFactory = profileFactory;
    }

    public User toUser(AuthenticatedUser user, AuthorProfileSnapshot profile) {
        return new User(user.getProvider(), user.getProvidersId(), new Profile(profile));
    }

    public AuthenticatedUser newAuthenticatedUser(String providerName, String providerUserId) {
        return new AuthenticatedUser(providerName, providerUserId);
    }

    public AuthenticatedUser newNullAuthenticatedUser() {
        return new AuthenticatedUser();
    }

    public AuthorProfileSnapshot newProfile(String name, @Nullable Bitmap photo) {
        return mProfileFactory.newProfile(name, photo);
    }
}
