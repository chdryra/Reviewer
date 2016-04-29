/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase.Implementation;


/**
 * Created by: Rizwan Choudrey
 * On: 16/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class User {
    private String mFbUserId;
    private UserProfile mProfile;

    public User(String fbUserId, UserProfile profile) {
        mFbUserId = fbUserId;
        mProfile = profile;
    }

    public String getFbUserId() {
        return mFbUserId;
    }

    public String getAuthorId() {
        return mProfile.getAuthor().getAuthorId();
    }

    public UserProfile getProfile() {
        return mProfile;
    }
}
