/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendDb.BackendFirebase.Implementation;



import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendDb.Implementation
        .BackendError;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticationError;
import com.firebase.client.FirebaseError;

/**
 * Created by: Rizwan Choudrey
 * On: 24/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FirebaseBackend         {
    public static final String ROOT = "https://teeqr.firebaseio.com/";
    private static final String NAME = "Firebase";
    
    public static AuthenticationError authenticationError(FirebaseError error) {
        if (error.getCode() == FirebaseError.EMAIL_TAKEN) {
            return new AuthenticationError(NAME, AuthenticationError.Reason.EMAIL_TAKEN);
        } else if (error.getCode() == FirebaseError.INVALID_EMAIL) {
            return new AuthenticationError(NAME, AuthenticationError.Reason.INVALID_EMAIL);
        } else if (error.getCode() == FirebaseError.INVALID_PASSWORD) {
            return new AuthenticationError(NAME, AuthenticationError.Reason.INVALID_PASSWORD);
        } else if (error.getCode() == FirebaseError.USER_DOES_NOT_EXIST) {
            return new AuthenticationError(NAME, AuthenticationError.Reason.UNKNOWN_USER);
        } else if (error.getCode() == FirebaseError.INVALID_CREDENTIALS) {
            return new AuthenticationError(NAME, AuthenticationError.Reason.INVALID_CREDENTIALS);
        } else if (error.getCode() == FirebaseError.DISCONNECTED) {
            return new AuthenticationError(NAME, AuthenticationError.Reason.NETWORK_ERROR);
        } else if (error.getCode() == FirebaseError.MAX_RETRIES) {
            return new AuthenticationError(NAME, AuthenticationError.Reason.NETWORK_ERROR);
        } else if (error.getCode() == FirebaseError.NETWORK_ERROR) {
            return new AuthenticationError(NAME, AuthenticationError.Reason.NETWORK_ERROR);
        } else if (error.getCode() == FirebaseError.UNAVAILABLE) {
            return new AuthenticationError(NAME, AuthenticationError.Reason.NETWORK_ERROR);
        } else {
            return new AuthenticationError(NAME, AuthenticationError.Reason.PROVIDER_ERROR, error
                    .getDetails());
        }
    }

    public static BackendError backendError(FirebaseError error) {
        if (error.getCode() == FirebaseError.OPERATION_FAILED) {
            return new BackendError(NAME, BackendError.Reason.OPERATION_FAILED);
        } else if (error.getCode() == FirebaseError.PERMISSION_DENIED) {
            return new BackendError(NAME, BackendError.Reason.PERMISSION_DENIED);
        } else if (error.getCode() == FirebaseError.DISCONNECTED) {
            return new BackendError(NAME, BackendError.Reason.NETWORK_ERROR);
        } else if (error.getCode() == FirebaseError.MAX_RETRIES) {
            return new BackendError(NAME, BackendError.Reason.NETWORK_ERROR);
        } else if (error.getCode() == FirebaseError.UNAVAILABLE) {
            return new BackendError(NAME, BackendError.Reason.NETWORK_ERROR);
        } else if (error.getCode() == FirebaseError.LIMITS_EXCEEDED) {
            return new BackendError(NAME, BackendError.Reason.LIMITS_EXCEEDED);
        } else if (error.getCode() == FirebaseError.NETWORK_ERROR) {
            return new BackendError(NAME, BackendError.Reason.NETWORK_ERROR);
        } else {
            return new BackendError(NAME, BackendError.Reason.UNKNOWN_ERROR, error
                    .getDetails());
        }
    }
}
