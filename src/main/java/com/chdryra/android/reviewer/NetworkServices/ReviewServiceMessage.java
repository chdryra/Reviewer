/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.NetworkServices;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by: Rizwan Choudrey
 * On: 30/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewServiceMessage implements Parcelable {
    public static final Creator<ReviewServiceMessage> CREATOR
            = new Creator<ReviewServiceMessage>() {
        @Override
        public ReviewServiceMessage createFromParcel(Parcel in) {
            return new ReviewServiceMessage(in);
        }

        @Override
        public ReviewServiceMessage[] newArray(int size) {
            return new ReviewServiceMessage[size];
        }
    };

    private String mMessage;
    private boolean mIsError = false;

    protected ReviewServiceMessage() {

    }

    protected ReviewServiceMessage(String message, boolean isError) {
        mMessage = message;
        mIsError = isError;
    }

    public ReviewServiceMessage(Parcel in) {
        mMessage = in.readString();
        mIsError = in.readByte() != 0;
    }

    public String getMessage() {
        return mMessage;
    }

    public boolean isError() {
        return mIsError;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mMessage);
        parcel.writeByte((byte) (isError() ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
