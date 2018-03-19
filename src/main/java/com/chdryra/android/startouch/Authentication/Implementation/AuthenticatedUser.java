/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Authentication.Implementation;


import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.AuthorIdParcelable;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;

/**
 * Created by: Rizwan Choudrey
 * On: 16/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class AuthenticatedUser implements Parcelable {
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
    private String mProvidersId;
    private String mAuthorId;

    public AuthenticatedUser() {
    }

    public AuthenticatedUser(String provider, String providersId) {
        this(provider, providersId, null);
    }

    public AuthenticatedUser(String provider, String providersId, @Nullable String authorId) {
        mProvider = provider;
        mProvidersId = providersId;
        mAuthorId = authorId;
    }

    public AuthenticatedUser(Parcel in) {
        mProvider = in.readString();
        mProvidersId = in.readString();
        mAuthorId = in.readString();
    }

    public String getProvider() {
        return mProvider;
    }

    public String getProvidersId() {
        return mProvidersId;
    }

    @Nullable
    public AuthorId getAuthorId() {
        return mAuthorId != null ? new AuthorIdParcelable(mAuthorId) : null;
    }

    public void setAuthorId(String authorId) {
        mAuthorId = authorId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mProvider);
        dest.writeString(mProvidersId);
        dest.writeString(mAuthorId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthenticatedUser)) return false;

        AuthenticatedUser that = (AuthenticatedUser) o;

        if (!mProvider.equals(that.mProvider)) return false;
        if (!mProvidersId.equals(that.mProvidersId)) return false;
        return mAuthorId != null ? mAuthorId.equals(that.mAuthorId) : that.mAuthorId == null;

    }

    @Override
    public int hashCode() {
        int result = mProvider.hashCode();
        result = 31 * result + mProvidersId.hashCode();
        result = 31 * result + (mAuthorId != null ? mAuthorId.hashCode() : 0);
        return result;
    }
}
