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
 * On: 24/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class Password implements Parcelable{
    public static final Parcelable.Creator<Password> CREATOR = new Parcelable.Creator<Password>() {
        @Override
        public Password createFromParcel(Parcel in) {
            return new Password(in);
        }

        @Override
        public Password[] newArray(int size) {
            return new Password[size];
        }
    };

    private final String mPassword;

    public Password(String password) {
        mPassword = password;
    }

    public Password(Parcel in) {
        mPassword = in.readString();
    }

    @Override
    public String toString() {
        return mPassword;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mPassword);
    }
}
