/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Factories;


import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Implementation.NullUserAccount;
import com.chdryra.android.startouch.Authentication.Implementation.AuthenticatedUser;
import com.chdryra.android.startouch.Authentication.Implementation.UserAccountImpl;
import com.chdryra.android.startouch.Authentication.Interfaces.AuthorProfileRef;
import com.chdryra.android.startouch.Authentication.Interfaces.SocialProfileRef;
import com.chdryra.android.startouch.Authentication.Interfaces.UserAccount;

/**
 * Created by: Rizwan Choudrey
 * On: 25/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryUserAccount {
    public UserAccount newAccount(AuthenticatedUser user,
                                  AuthorProfileRef authorProfile,
                                  SocialProfileRef socialProfile) {
        if (user.getAuthorId() == null) {
            throw new IllegalArgumentException("User should be an author!");
        }

        return new UserAccountImpl(user, authorProfile, socialProfile);
    }

    public UserAccount newNullAccount() {
        return new NullUserAccount();
    }

    public UserAccount newNullAccount(AuthenticatedUser user) {
        return new NullUserAccount(user);
    }
}
