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
 * On: 30/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class CallbackMessage implements Parcelable {
    public static final Creator<CallbackMessage> CREATOR
            = new Creator<CallbackMessage>() {
        @Override
        public CallbackMessage createFromParcel(Parcel in) {
            return new CallbackMessage(in);
        }

        @Override
        public CallbackMessage[] newArray(int size) {
            return new CallbackMessage[size];
        }
    };

    private String mMessage;
    private boolean mIsError = false;

    public static CallbackMessage ok(String message) {

        return new CallbackMessage(message, false);
    }

    public static CallbackMessage error(String message) {
        return new CallbackMessage(message, true);
    }

    private CallbackMessage(String message, boolean isError) {
        mMessage = message;
        mIsError = isError;
    }

    public CallbackMessage(Parcel in) {
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
