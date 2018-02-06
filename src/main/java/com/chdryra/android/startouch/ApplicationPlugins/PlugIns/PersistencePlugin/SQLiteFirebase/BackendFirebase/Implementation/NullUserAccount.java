/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation;


import com.chdryra.android.startouch.Authentication.Implementation.AuthenticatedUser;
import com.chdryra.android.startouch.Authentication.Implementation.NullAuthorProfile;
import com.chdryra.android.startouch.Authentication.Implementation.NullSocialProfile;
import com.chdryra.android.startouch.Authentication.Interfaces.ProfileReference;
import com.chdryra.android.startouch.Authentication.Interfaces.ProfileSocial;
import com.chdryra.android.startouch.Authentication.Interfaces.UserAccount;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.AuthorIdParcelable;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;

/**
 * Created by: Rizwan Choudrey
 * On: 23/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class NullUserAccount implements UserAccount {
    private final AuthenticatedUser mUser;
    public NullUserAccount() {
        mUser = new AuthenticatedUser();
    }

    public NullUserAccount(AuthenticatedUser user) {
        mUser = user;
    }

    @Override
    public AuthenticatedUser getAccountHolder() {
        return mUser;
    }

    @Override
    public AuthorId getAuthorId() {
        return new AuthorIdParcelable();
    }

    @Override
    public ProfileSocial getSocialProfile() {
        return new NullSocialProfile();
    }

    @Override
    public ProfileReference getAuthorProfile() {
        return new NullAuthorProfile();
    }
}
