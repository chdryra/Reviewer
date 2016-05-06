/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendDb.Implementation;



import android.support.annotation.Nullable;

/**
 * Created by: Rizwan Choudrey
 * On: 16/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class User {
    private String mProviderUserId;
    private String mAuthorId;
    private AuthorProfile mProfile;

    public User() {
    }

    public User(String providerUserId, String authorId) {
        mProviderUserId = providerUserId;
        mAuthorId = authorId;
    }

    public User(String providerUserId, AuthorProfile profile) {
        mProviderUserId = providerUserId;
        mAuthorId = profile.getAuthor().getAuthorId();
        mProfile = profile;
    }

    public String getProviderUserId() {
        return mProviderUserId;
    }

    public String getAuthorId() {
        return mAuthorId;
    }

    public @Nullable
    AuthorProfile getProfile() {
        return mProfile;
    }
}
