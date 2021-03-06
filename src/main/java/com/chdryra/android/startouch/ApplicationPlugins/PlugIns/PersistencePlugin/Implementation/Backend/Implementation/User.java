/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation;


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
    private ProfileAuthor mProfile;
    private ProfileAuthor mNewProfile;

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

    public User(String providerName, String providerUserId, ProfileAuthor profile) {
        this(providerName, providerUserId, profile, null);
    }

    public User(String providerName, String providerUserId, ProfileAuthor oldProfile, @Nullable
            ProfileAuthor newProfile) {
        mProviderName = providerName;
        mProviderUserId = providerUserId;
        mAuthorId = oldProfile.getAuthor().getAuthorId();
        mProfile = oldProfile;
        mNewProfile = newProfile != null ? newProfile : mProfile;
        if (!mNewProfile.getAuthor().getAuthorId().equals(mProfile.getAuthor().getAuthorId())) {
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
    public ProfileAuthor getProfile() {
        return mProfile;
    }

    public User getCurrentUser() {
        return newUser(mProfile);
    }

    public User getUpdatedUser() {
        return newUser(mNewProfile);
    }

    @NonNull
    private User newUser(ProfileAuthor profile) {
        return new User(mProviderName, mProviderUserId, profile, null);
    }
}
