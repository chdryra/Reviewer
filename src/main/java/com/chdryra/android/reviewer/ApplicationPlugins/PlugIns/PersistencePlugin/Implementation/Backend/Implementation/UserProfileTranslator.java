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
public class UserProfileTranslator {
    private FactoryAuthorProfile mProfileFactory;

    public UserProfileTranslator(FactoryAuthorProfile profileFactory) {
        mProfileFactory = profileFactory;
    }

    public FactoryAuthorProfile getProfileFactory() {
        return mProfileFactory;
    }

    public User newUser(String providerName, String providerUserId) {
        return new User(providerName, providerUserId);
    }

    public User newUser(String providerName, String providerUserId, String authorId) {
        return new User(providerName, providerUserId, authorId);
    }

    public User toUser(AuthenticatedUser user) {
        return newUser(user.getProvider(), user.getUserId());
    }

    public User toUser(AuthenticatedUser user, AuthorProfile profile) {
        return new User(user.getProvider(), user.getUserId(), new Profile(profile));
    }

    public AuthenticatedUser toAuthenticatedUser(User user) {
        return new AuthenticatedUser(user.getProviderName(), user.getProviderUserId());
    }

    public AuthenticatedUser newNullAuthenticatedUser() {
        return new AuthenticatedUser();
    }

    public Profile toProfile(AuthorProfile profile) {
        return new Profile(profile);
    }

    public AuthorProfile toAuthorProfile(Profile profile) {
        Author author = profile.getAuthor();
        return mProfileFactory.newProfile(author.getName(), author.getAuthorId(), profile.getDateJoined());
    }

    public AuthorProfile newProfile(String name) {
        return mProfileFactory.newProfile(name);
    }

    public AuthorProfile newNullAuthorProfile() {
        return mProfileFactory.newNullAuthorProfile();
    }
}
