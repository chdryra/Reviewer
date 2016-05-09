/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendDb.Implementation;



import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticatedUser;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthorProfile;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumDate;

/**
 * Created by: Rizwan Choudrey
 * On: 09/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class UserProfileTranslator {
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
        DatumAuthor da = new DatumAuthor(author.getName(), new DatumAuthorId(author.getAuthorId()));
        return new AuthorProfile(da, new DatumDate(profile.getDateJoined()));
    }

    public AuthorProfile newNullAuthorProfile() {
        return new AuthorProfile();
    }
}
