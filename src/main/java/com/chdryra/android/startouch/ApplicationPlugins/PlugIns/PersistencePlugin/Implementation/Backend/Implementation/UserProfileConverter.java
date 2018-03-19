/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation;


import com.chdryra.android.startouch.Authentication.Factories.FactoryAuthorProfileSnapshot;
import com.chdryra.android.startouch.Authentication.Implementation.AuthenticatedUser;
import com.chdryra.android.startouch.Authentication.Interfaces.AuthorProfile;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.AuthorIdParcelable;

/**
 * Created by: Rizwan Choudrey
 * On: 09/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class UserProfileConverter {
    private final FactoryAuthorProfileSnapshot mProfileFactory;

    public UserProfileConverter(FactoryAuthorProfileSnapshot profileFactory) {
        mProfileFactory = profileFactory;
    }

    public User toUser(AuthenticatedUser user, AuthorProfile profile) {
        return new User(user.getProvider(), user.getProvidersId(), new ProfileAuthor(profile));
    }

    public User toUser(AuthenticatedUser user, AuthorProfile oldProfile, AuthorProfile newProfile) {
        return new User(user.getProvider(), user.getProvidersId(), new ProfileAuthor(oldProfile),
                new ProfileAuthor(newProfile));
    }

    public AuthenticatedUser newAuthenticatedUser(String providerName, String providerUserId) {
        return new AuthenticatedUser(providerName, providerUserId);
    }

    public AuthenticatedUser newNullAuthenticatedUser() {
        return new AuthenticatedUser();
    }

    public AuthorProfile newProfile(ProfileAuthor profile) {
        return mProfileFactory.newProfile(profile.getAuthor().getName(),
                new AuthorIdParcelable(profile.getAuthor().getAuthorId()), profile.getDateJoined(),
                ImageData.asBitmap(profile.getPhoto()));
    }
}
