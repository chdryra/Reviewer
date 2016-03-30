/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.NetworkServices.Social.Implementation;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by: Rizwan Choudrey
 * On: 12/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class PublishResults implements Parcelable{
    public static final Creator<PublishResults> CREATOR = new Creator<PublishResults>() {
        @Override
        public PublishResults createFromParcel(Parcel in) {
            return new PublishResults(in);
        }

        @Override
        public PublishResults[] newArray(int size) {
            return new PublishResults[size];
        }
    };

    private String mPublisherName;
    private int mFollowers;
    private String mErrorIfFail;
    private boolean mSuccess;

    public PublishResults(String publisherName, int followers) {
        mPublisherName = publisherName;
        mFollowers = followers;
        mSuccess = true;
        mErrorIfFail = "";
    }

    public PublishResults(String publisherName, String errorIfFail) {
        mPublisherName = publisherName;
        mFollowers = 0;
        mErrorIfFail = errorIfFail;
        mSuccess = false;
    }

    public PublishResults(Parcel in) {
        mPublisherName = in.readString();
        mFollowers = in.readInt();
        mErrorIfFail = in.readString();
        mSuccess = in.readByte() != 0;
    }

    public String getPublisherName() {
        return mPublisherName;
    }

    public int getFollowers() {
        return mFollowers;
    }

    public String getErrorIfFail() {
        return mErrorIfFail;
    }

    public boolean wasSuccessful() {
        return mSuccess;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mPublisherName);
        parcel.writeInt(mFollowers);
        parcel.writeString(mErrorIfFail);
        parcel.writeByte((byte) (wasSuccessful() ? 1 : 0));
    }
}
