/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Authentication.Implementation;

/**
 * Created by: Rizwan Choudrey
 * On: 26/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class AuthenticationError {
    public enum Reason {
        PROVIDER_ERROR("Provider error"),
        INVALID_NAME("Name is invalid"),
        INVALID_EMAIL("Email doesn't make sense"),
        INVALID_PASSWORD("Password is invalid"),
        INVALID_CREDENTIALS("Credentials are invalid"),
        AUTHORISATION_REFUSED("Authorisation refused"),
        UNKNOWN_USER("Unknown user"),
        EMAIL_TAKEN("Email is already taken"),
        NAME_TAKEN("Name is already taken"),
        NETWORK_ERROR("Internet is having a rest"),
        NO_AUTHENTICATED_USER("No authenticated user available"),
        UNKNOWN_ERROR("Beats me...");


        private final String mMessage;

        Reason(String message) {
            mMessage = message;
        }

        public String getMessage() {
            return mMessage;
        }
    }

    private final String mProvider;
    private final Reason mReason;
    private final String mDetail;

    public AuthenticationError(String provider, Reason reason) {
        this(provider, reason, "");
    }

    public AuthenticationError(String provider, Reason reason, String detail) {
        mProvider = provider;
        mReason = reason;
        mDetail = detail;
    }

    public String getProvider() {
        return mProvider;
    }

    public String getMessage() {
        return "Error authenticating with " + mProvider + ": " + mReason.getMessage()
                + (mDetail != null && mDetail.length() > 0 ? "(" + mDetail + ")" : mDetail);
    }

    @Override
    public String toString() {
        return getMessage();
    }

    public boolean is(Reason reason) {
        return reason.equals(mReason);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthenticationError)) return false;

        AuthenticationError that = (AuthenticationError) o;

        return mReason == that.mReason;

    }

    @Override
    public int hashCode() {
        return mReason != null ? mReason.hashCode() : 0;
    }
}
