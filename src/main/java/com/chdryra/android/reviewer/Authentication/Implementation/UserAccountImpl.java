/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Implementation;


import com.chdryra.android.reviewer.Authentication.Interfaces.SocialProfile;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAccount;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;

/**
 * Created by: Rizwan Choudrey
 * On: 23/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class UserAccountImpl implements UserAccount {
    private final AuthorId mAuthorId;
    private final AuthenticatedUser mAccountHolder;
    private final SocialProfile mSocialProfile;

    public UserAccountImpl(AuthenticatedUser accountHolder, SocialProfile profile) {
        mAccountHolder = accountHolder;
        if(mAccountHolder.getAuthorId() == null) {
            throw new IllegalArgumentException("User should be an author!");
        }
        mAuthorId = mAccountHolder.getAuthorId();
        mSocialProfile = profile;
    }

    @Override
    public AuthenticatedUser getAccountHolder() {
        return mAccountHolder;
    }

    @Override
    public AuthorId getAuthorId() {
        return mAuthorId;
    }

    @Override
    public SocialProfile getSocialProfile() {
        return mSocialProfile;
    }
}
