/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation;


import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.User;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthorProfile;
import com.chdryra.android.reviewer.Authentication.Implementation.UserAccount;

/**
 * Created by: Rizwan Choudrey
 * On: 23/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class NullUserAccount implements UserAccount {

    public NullUserAccount() {
    }

    @Override
    public User getAccountHolder() {
        return new User();
    }

    @Override
    public void getAuthorProfile(GetAuthorProfileCallback callback) {

    }

    @Override
    public void updateAuthorProfile(AuthorProfile newProfile, UpdateAuthorProfileCallback
            callback) {

    }

    @Override
    public void getSocialProfile(GetSocialProfileCallback callback) {

    }
}
