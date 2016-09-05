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
public class SignUpArgs implements Parcelable {
    public static final Creator<SignUpArgs> CREATOR = new Creator<SignUpArgs>() {
        @Override
        public SignUpArgs createFromParcel(Parcel in) {
            return new SignUpArgs(in);
        }

        @Override
        public SignUpArgs[] newArray(int size) {
            return new SignUpArgs[size];
        }
    };

    private EmailAddress mEmail;
    private AuthenticatedUser mUser;

    public SignUpArgs() {

    }

    public SignUpArgs(AuthenticatedUser user) {
        mEmail = null;
        mUser = user;
    }

    public SignUpArgs(EmailAddress emailAddress){
        mEmail = emailAddress;
        mUser = null;
    }

    private SignUpArgs(Parcel in) {
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
