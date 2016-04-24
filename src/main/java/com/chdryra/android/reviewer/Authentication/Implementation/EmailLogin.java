/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Implementation;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase
        .Implementation.FirebaseBackend;
import com.chdryra.android.reviewer.Authentication.Interfaces.AuthenticationProvider;
import com.chdryra.android.reviewer.Authentication.Interfaces.EmailLoginCallback;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumUserId;
import com.chdryra.android.reviewer.Utils.EmailAddress;
import com.chdryra.android.reviewer.Utils.Password;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

/**
 * Created by: Rizwan Choudrey
 * On: 21/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class EmailLogin implements AuthenticationProvider<EmailLoginCallback> {
    private static final String NAME = "EmailPassword";
    private EmailAddress mEmail;
    private Password mPassword;

    public EmailLogin(EmailAddress email, Password password) {
        mEmail = email;
        mPassword = password;
    }

    @Override
    public void requestAuthentication(final EmailLoginCallback callback) {
        Firebase ref = new Firebase(FirebaseBackend.ROOT);
        ref.authWithPassword(mEmail.getEmail(), mPassword.getPassword(), new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                callback.onSuccess(new DatumUserId(authData.getUid()));
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                callback.onFailure(CallbackMessage.error(firebaseError.getMessage() + ": " + firebaseError.getDetails()));
            }
        });
    }

    @Override
    public String getName() {
        return NAME;
    }
}
