/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Authentication.Implementation;


import com.chdryra.android.startouch.Authentication.Interfaces.AuthorProfileRef;
import com.chdryra.android.startouch.Authentication.Interfaces.SocialProfileRef;
import com.chdryra.android.startouch.Authentication.Interfaces.UserAccount;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;

/**
 * Created by: Rizwan Choudrey
 * On: 23/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class UserAccountImpl implements UserAccount {
    private final AuthorId mAuthorId;
    private final AuthenticatedUser mAccountHolder;
    private final AuthorProfileRef mAuthorProfile;
    private final SocialProfileRef mSocialProfile;

    public UserAccountImpl(AuthenticatedUser accountHolder,
                           AuthorProfileRef authorProfile,
                           SocialProfileRef socialProfile) {
        mAccountHolder = accountHolder;
        if (mAccountHolder.getAuthorId() == null) {
            throw new IllegalArgumentException("User should be an author!");
        }
        mAuthorId = mAccountHolder.getAuthorId();
        mAuthorProfile = authorProfile;
        mSocialProfile = socialProfile;

        String id = mAuthorId.toString();
        if (!id.equals(mAuthorProfile.getAuthorId().toString()) ||
                !id.equals(mSocialProfile.getAuthorId().toString())) {
            throw new IllegalArgumentException("AuthorIds should match!");
        }
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
    public AuthorProfileRef getAuthorProfile() {
        return mAuthorProfile;
    }

    @Override
    public SocialProfileRef getSocialProfile() {
        return mSocialProfile;
    }
}
