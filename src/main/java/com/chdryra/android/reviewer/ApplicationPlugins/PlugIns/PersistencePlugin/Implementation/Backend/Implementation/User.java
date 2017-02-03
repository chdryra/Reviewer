/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation;



import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by: Rizwan Choudrey
 * On: 16/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class User {
    private String mProviderName;
    private String mProviderUserId;
    private String mAuthorId;
    private Profile mProfile;
    private Profile mNewProfile;

    public User() {
    }

    public User(String providerName, String providerUserId) {
        mProviderName = providerName;
        mProviderUserId = providerUserId;
    }

    public User(String providerName, String providerUserId, String authorId) {
        mProviderName = providerName;
        mProviderUserId = providerUserId;
        mAuthorId = authorId;
    }

    public User(String providerName, String providerUserId, Profile profile) {
        mProviderName = providerName;
        mProviderUserId = providerUserId;
        mProfile = profile;
        mAuthorId = mProfile.getAuthor().getAuthorId();
    }

    public User(String providerName, String providerUserId, Profile oldProfile, @Nullable Profile newProfile) {
        mProviderName = providerName;
        mProviderUserId = providerUserId;
        mAuthorId = oldProfile.getAuthor().getAuthorId();
        mProfile = oldProfile;
        mNewProfile = newProfile != null ? newProfile : mProfile;
        if(!mNewProfile.getAuthor().getAuthorId().equals(mProfile.getAuthor().getAuthorId())) {
            throw new IllegalArgumentException("Profiles should refer to same authorId!");
        }
    }

    public String getProviderName() {
        return mProviderName;
    }

    public String getProviderUserId() {
        return mProviderUserId;
    }

    @Nullable
    public String getAuthorId() {
        return mAuthorId;
    }

    @Nullable
    public Profile getProfile() {
        return mProfile;
    }

    public User getCurrentUser() {
        return newUser(mProfile);
    }

    @NonNull
    private User newUser(Profile profile) {
        return new User(mProviderName, mProviderUserId, profile, null);
    }

    public User getUpdatedUser() {
        return newUser(mNewProfile);
    }
}
