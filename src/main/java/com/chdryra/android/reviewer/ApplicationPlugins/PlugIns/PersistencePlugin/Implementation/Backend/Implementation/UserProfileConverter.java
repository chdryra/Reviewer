/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation;



import com.chdryra.android.reviewer.Authentication.Factories.FactoryAuthorProfile;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticatedUser;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthorProfile;

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

    private User newUser(String providerName, String providerUserId) {
        return new User(providerName, providerUserId);
    }

    public User toUser(AuthenticatedUser user) {
        return newUser(user.getProvider(), user.getProvidersId());
    }

    public User toUser(AuthenticatedUser user, AuthorProfile profile) {
        return new User(user.getProvider(), user.getProvidersId(), new Profile(profile));
    }

    public User toUser(AuthenticatedUser user, String authorId) {
        return new User(user.getProvider(), user.getProvidersId(), authorId);
    }

    public AuthenticatedUser toAuthenticatedUser(User user) {
        return new AuthenticatedUser(user.getProviderName(), user.getProviderUserId());
    }

    public AuthenticatedUser newAuthenticatedUser(String providerName, String providerUserId) {
        return new AuthenticatedUser(providerName, providerUserId);
    }

    public AuthenticatedUser newNullAuthenticatedUser() {
        return new AuthenticatedUser();
    }

    public AuthorProfile toAuthorProfile(Profile profile) {
        Author author = profile.getAuthor();
        return mProfileFactory.newProfile(author.getName(), author.getAuthorId(), profile.getDateJoined());
    }

    public AuthorProfile newProfile(String name) {
        return mProfileFactory.newProfile(name);
    }

    public AuthorProfile newNullProfile() {
        return mProfileFactory.newNullAuthorProfile();
    }
}
