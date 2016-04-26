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
import com.chdryra.android.reviewer.Authentication.Interfaces.UserCreaterCallback;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserCreaterEmailPassword;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumUserId;
import com.chdryra.android.reviewer.Utils.EmailAddress;
import com.chdryra.android.reviewer.Utils.Password;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 24/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class UserCreaterFirebase implements UserCreaterEmailPassword {
    private static final String ROOT = FirebaseBackend.ROOT;

    @Override
    public void createUser(EmailAddress email, Password password, final UserCreaterCallback callback) {
        Firebase ref = new Firebase(ROOT);
        ref.createUser(email.toString(), password.toString(),
                new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                callback.onSuccess(new DatumUserId(result.get("uid").toString()));
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                callback.onFailure(CallbackMessage.error(firebaseError.getMessage() + ": " + firebaseError.getDetails()));
            }
        });

    }
}
