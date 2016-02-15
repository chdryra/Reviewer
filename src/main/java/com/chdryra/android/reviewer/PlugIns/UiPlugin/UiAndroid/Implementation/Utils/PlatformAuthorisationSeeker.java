/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Utils;

import android.app.Activity;
import android.os.Bundle;

import com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Implementation.DialogAuthSharing;
import com.chdryra.android.reviewer.Social.Implementation.OAuthRequest;
import com.chdryra.android.reviewer.Social.Interfaces.PlatformAuthoriser;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPlatform;
import com.chdryra.android.reviewer.Utils.DialogShower;
import com.chdryra.android.reviewer.Utils.RequestCodeGenerator;

/**
 * Created by: Rizwan Choudrey
 * On: 15/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class PlatformAuthorisationSeeker<T> implements DialogAuthSharing.AuthorisationListener {
    private static final String AuthTag = "PlatformAuthorisation";
    private static final int AUTHORISATION = RequestCodeGenerator.getCode("PlatformAuthorisation");

    private Activity mActivity;
    private SocialPlatform<T> mPlatform;
    private PlatformAuthoriser.AuthorisationListener mListener;

    public PlatformAuthorisationSeeker(Activity activity, SocialPlatform<T> platform,
                                       PlatformAuthoriser.AuthorisationListener listener) {
        mActivity = activity;
        mPlatform = platform;
        mListener = listener;
    }

    public void seekAuthorisation() {
        DialogAuthSharing dialog = DialogAuthSharing.newDialog(mPlatform.generateAuthorisationRequest());
        DialogShower.show(dialog, mActivity, AUTHORISATION, AuthTag, new Bundle());
    }

    @Override
    public void onAuthorisationCallback(OAuthRequest response) {
        T token = mPlatform.parseRequestResponse(response);
        if (token != null) {
            mPlatform.setAccessToken(token);
            mListener.onAuthorisationGiven(mPlatform);
        } else {
            mListener.onAuthorisationRefused(mPlatform);
        }
    }
}
