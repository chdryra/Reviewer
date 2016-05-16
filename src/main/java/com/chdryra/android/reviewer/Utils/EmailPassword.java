/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Utils;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by: Rizwan Choudrey
 * On: 28/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class EmailPassword implements Parcelable{
    public static final Parcelable.Creator<EmailPassword> CREATOR = new Parcelable.Creator<EmailPassword>() {
        @Override
        public EmailPassword createFromParcel(Parcel in) {
            return new EmailPassword(in);
        }

        @Override
        public EmailPassword[] newArray(int size) {
            return new EmailPassword[size];
        }
    };

    private final EmailAddress mEmail;
    private final Password mPassword;

    public EmailPassword(EmailAddress email, Password password) {
        mEmail = email;
        mPassword = password;
    }

    public EmailPassword(Parcel in) {
        mEmail = in.readParcelable(EmailAddress.class.getClassLoader());
        mPassword = in.readParcelable(Password.class.getClassLoader());
    }

    public EmailAddress getEmail() {
        return mEmail;
    }

    public Password getPassword() {
        return mPassword;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mEmail, flags);
        dest.writeParcelable(mPassword, flags);
    }
}
