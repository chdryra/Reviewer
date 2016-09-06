/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Factories;



import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.FbUserAccount;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.NullUserAccount;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Interfaces.FbSocialStructure;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticatedUser;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAccount;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 25/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryUserAccount {
    private FactoryFbReference mReferencer;

    public FactoryUserAccount(FactoryFbReference referencer) {
        mReferencer = referencer;
    }

    public UserAccount newAccount(AuthenticatedUser user,
                                  Firebase dataRoot,
                                  FbSocialStructure social) {
        if(user.getAuthorId() == null) {
            throw new IllegalArgumentException("User should be an author!");
        }
        return new FbUserAccount(user, mReferencer.newSocialProfile(user.getAuthorId(), dataRoot, social));
    }

    public UserAccount newNullAccount() {
        return new NullUserAccount();
    }

    public UserAccount newNullAccount(AuthenticatedUser user) {
        return new NullUserAccount(user);
    }
}
