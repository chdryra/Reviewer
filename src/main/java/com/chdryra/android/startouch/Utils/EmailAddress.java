/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Utils;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by: Rizwan Choudrey
 * On: 24/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class EmailAddress implements Parcelable{
    public static final Creator<EmailAddress> CREATOR = new Creator<EmailAddress>() {
        @Override
        public EmailAddress createFromParcel(Parcel in) {
            return new EmailAddress(in);
        }

        @Override
        public EmailAddress[] newArray(int size) {
            return new EmailAddress[size];
        }
    };
    
    private String mEmail;

    public EmailAddress(String email) throws EmailAddressException{
        if(!EmailValidator.isValid(email)) throw new EmailAddressException(email);
        mEmail = email;
    }

    public EmailAddress(Parcel in) {
        mEmail = in.readString();
    }
    
    @Override
    public String toString() {
        return mEmail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mEmail);
    }
}
