/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendDb.Implementation;


/**
 * Created by: Rizwan Choudrey
 * On: 06/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class BackendError {
    public enum Reason {
        OPERATION_FAILED("Operation failed"),
        PERMISSION_DENIED("Permission denied"),
        LIMITS_EXCEEDED("Limits exceeded"),
        NETWORK_ERROR("Network error"),
        WRITE_CANCELED("Write cancelled"),
        UNKNOWN_ERROR("Unknown error");

        private String mMessage;

        Reason(String message) {
            mMessage = message;
        }

        public String getMessage() {
            return mMessage;
        }
    }

    private String mProvider;
    private Reason mReason;
    private String mDetail;

    public BackendError(String provider, Reason reason) {
        this(provider, reason, "");
    }

    public BackendError(String provider, Reason reason, String detail) {
        mProvider = provider;
        mReason = reason;
        mDetail = detail;
    }

    public String getProvider() {
        return mProvider;
    }

    public String getMessage() {
        return "Error with " + mProvider + ": " + mReason.getMessage()
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
        if (!(o instanceof BackendError)) return false;

        BackendError that = (BackendError) o;

        return mReason == that.mReason;

    }

    @Override
    public int hashCode() {
        return mReason != null ? mReason.hashCode() : 0;
    }
}
