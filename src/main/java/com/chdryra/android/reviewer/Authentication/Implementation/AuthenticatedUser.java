/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Implementation;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by: Rizwan Choudrey
 * On: 16/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class AuthenticatedUser implements Parcelable{
    public static final Creator<AuthenticatedUser> CREATOR = new Creator<AuthenticatedUser>() {
        @Override
        public AuthenticatedUser createFromParcel(Parcel in) {
            return new AuthenticatedUser(in);
        }

        @Override
        public AuthenticatedUser[] newArray(int size) {
            return new AuthenticatedUser[size];
        }
    };
    
    private String mProvider;
    private String mUserId;

    public AuthenticatedUser() {
    }

    public AuthenticatedUser(String provider, String userId) {
        mProvider = provider;
        mUserId = userId;
    }

    public AuthenticatedUser(Parcel in) {
        mProvider = in.readString();
        mUserId = in.readString();
    }

    public String getProvider() {
        return mProvider;
    }

    public String getUserId() {
        return mUserId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mProvider);
        dest.writeString(mUserId);
    }
}