/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Authentication.Interfaces;

import android.content.Intent;

import com.chdryra.android.startouch.Authentication.Implementation.AuthenticationError;
import com.chdryra.android.startouch.Authentication.Implementation.Credentials;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ActivityResultListener;

/**
 * Created by: Rizwan Choudrey
 * On: 27/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface CredentialsProvider<Cred> extends ActivityResultListener {
    interface Callback<Cred> {
        void onCredentialsObtained(Credentials<Cred> credentials);

        void onCredentialsFailure(AuthenticationError error);
    }

    void requestCredentials(Callback<Cred> callback);

    @Override
    void onActivityResult(int requestCode, int resultCode, Intent data);
}
