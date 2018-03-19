/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Social.Implementation;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by: Rizwan Choudrey
 * On: 15/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class OAuthRequest implements Parcelable {
    public static final Creator<OAuthRequest> CREATOR = new Creator<OAuthRequest>() {
        @Override
        public OAuthRequest createFromParcel(Parcel in) {
            return new OAuthRequest(in);
        }

        @Override
        public OAuthRequest[] newArray(int size) {
            return new OAuthRequest[size];
        }
    };

    private final String mName;
    private final String mAuthorisationUrl;
    private final String mCallbackUrl;
    private String mCallbackResult;

    public OAuthRequest(String name, String authorisationUrl, String callbackUrl) {
        mName = name;
        mAuthorisationUrl = authorisationUrl;
        mCallbackUrl = callbackUrl;
        mCallbackResult = "";
    }

    private OAuthRequest(Parcel in) {
        mName = (String) in.readSerializable();
        mAuthorisationUrl = (String) in.readSerializable();
        mCallbackUrl = (String) in.readSerializable();
        mCallbackResult = (String) in.readSerializable();
    }

    public String getName() {
        return mName;
    }

    public String getAuthorisationUrl() {
        return mAuthorisationUrl;
    }

    public String getCallbackUrl() {
        return mCallbackUrl;
    }

    public String getCallbackResult() {
        return mCallbackResult;
    }

    public void setCallbackResult(String callbackResult) {
        mCallbackResult = callbackResult;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(mName);
        dest.writeSerializable(mAuthorisationUrl);
        dest.writeSerializable(mCallbackUrl);
        dest.writeSerializable(mCallbackResult);
    }
}
