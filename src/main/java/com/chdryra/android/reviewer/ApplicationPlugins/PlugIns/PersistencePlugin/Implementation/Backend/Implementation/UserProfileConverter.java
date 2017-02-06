/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation;



import com.chdryra.android.reviewer.Authentication.Factories.FactoryAuthorProfileSnapshot;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticatedUser;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthorProfileSnapshot;

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

    public User toUser(AuthenticatedUser user, AuthorProfileSnapshot profile) {
        return new User(user.getProvider(), user.getProvidersId(), new Profile(profile));
    }

    public User toUser(AuthenticatedUser user, AuthorProfileSnapshot oldProfile, AuthorProfileSnapshot newProfile) {
        return new User(user.getProvider(), user.getProvidersId(), new Profile(oldProfile), new Profile(newProfile));
    }

    public AuthenticatedUser newAuthenticatedUser(String providerName, String providerUserId) {
        return new AuthenticatedUser(providerName, providerUserId);
    }

    public AuthenticatedUser newNullAuthenticatedUser() {
        return new AuthenticatedUser();
    }

    public AuthorProfileSnapshot newProfile(Profile profile) {
        return mProfileFactory.newProfile(profile.getAuthor().getName(),
                profile.getAuthor().getAuthorId(), profile.getDateJoined(),
                ImageData.asBitmap(profile.getPhoto()));
    }
}
