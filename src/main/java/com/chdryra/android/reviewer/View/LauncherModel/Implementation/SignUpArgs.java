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

    public static final String EMAIL_PASSWORD = "EmailPassword";
    private final String mProvider;
    private EmailAddress mEmail;
    private AuthenticatedUser mUser;

    public SignUpArgs() {
        mProvider = EMAIL_PASSWORD;
    }

    public SignUpArgs(AuthenticatedUser user) {
        mProvider = user.getProvider();
        mEmail = null;
        mUser = user;
    }

    public SignUpArgs(EmailAddress emailAddress){
        mProvider = EMAIL_PASSWORD;
        mEmail = emailAddress;
    }

    public SignUpArgs(Parcel in) {
        mProvider = in.readString();
        String emailAddress = in.readString();
        if(emailAddress != null) mEmail = new EmailAddress(emailAddress);
        mUser = in.readParcelable(AuthenticatedUser.class.getClassLoader());
    }

    public String getProvider() {
        return mProvider;
    }

    @Nullable
    public EmailAddress getEmail() {
        return mEmail;
    }

    public boolean isEmailPassword() {
        return mProvider.equals(EMAIL_PASSWORD);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mProvider);
        if(mEmail != null) dest.writeString(mEmail.toString());
        if(mUser != null) dest.writeParcelable(mUser, flags);
    }
}
