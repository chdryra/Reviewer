/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.View.LauncherModel.Implementation;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticatedUser;
import com.chdryra.android.reviewer.Utils.EmailAddress;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ProfileArgs implements Parcelable {
    public static final Creator<ProfileArgs> CREATOR = new Creator<ProfileArgs>() {
        @Override
        public ProfileArgs createFromParcel(Parcel in) {
            return new ProfileArgs(in);
        }

        @Override
        public ProfileArgs[] newArray(int size) {
            return new ProfileArgs[size];
        }
    };

    private EmailAddress mEmail;
    private AuthenticatedUser mUser;

    public ProfileArgs() {

    }

    public ProfileArgs(AuthenticatedUser user) {
        mEmail = null;
        mUser = user;
    }

    public ProfileArgs(EmailAddress emailAddress){
        mEmail = emailAddress;
        mUser = null;
    }

    private ProfileArgs(Parcel in) {
        mEmail = in.readParcelable(EmailAddress.class.getClassLoader());
        mUser = in.readParcelable(AuthenticatedUser.class.getClassLoader());
    }

    @Nullable
    public AuthenticatedUser getUser() {
        return mUser;
    }

    @Nullable
    public EmailAddress getEmail() {
        return mEmail;
    }

    public boolean isEmailPassword() {
        return mUser == null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mEmail, flags);
        dest.writeParcelable(mUser, flags);
    }
}
