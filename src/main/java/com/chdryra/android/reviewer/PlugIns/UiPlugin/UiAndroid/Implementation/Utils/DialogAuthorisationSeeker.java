/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Utils;

import android.app.Activity;
import android.content.Intent;

import com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Implementation.DialogAuthSharing;
import com.chdryra.android.reviewer.Social.Implementation.OAuthRequest;
import com.chdryra.android.reviewer.Social.Interfaces.OAuthRequester;
import com.chdryra.android.reviewer.Social.Interfaces.PlatformAuthoriser;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPlatform;
import com.chdryra.android.reviewer.Utils.DialogShower;
import com.chdryra.android.reviewer.Utils.RequestCodeGenerator;

/**
 * Created by: Rizwan Choudrey
 * On: 15/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class DialogAuthorisationSeeker<T> implements
        DialogAuthSharing.AuthorisationListener,
        OAuthRequester.RequestListener<T>, PlatformAuthorisationSeeker {
    private static final String AuthTag = "PlatformAuthorisation";
    private static final int AUTHORISATION = RequestCodeGenerator.getCode("PlatformAuthorisation");

    private Activity mActivity;
    private SocialPlatform<T> mPlatform;
    private PlatformAuthoriser.AuthorisationListener mListener;

    public DialogAuthorisationSeeker(Activity activity, SocialPlatform<T> platform,
                                     PlatformAuthoriser.AuthorisationListener listener) {
        mActivity = activity;
        mPlatform = platform;
        mListener = listener;
    }

    @Override
    public void seekAuthorisation() {
        OAuthRequester<T> requester = mPlatform.getAuthorisationRequester();
        requester.generateAuthorisationRequest(this);

    }

    @Override
    public void onRequestGenerated(OAuthRequest request) {
        launchDialog(request);
    }

    @Override
    public void onResponseParsed(T token) {
        if (token != null) {
            mPlatform.setAccessToken(token);
            mListener.onAuthorisationGiven(mPlatform);
        } else {
            mListener.onAuthorisationRefused(mPlatform);
        }
    }

    @Override
    public void onAuthorisationCallback(OAuthRequest response) {
        OAuthRequester<T> requester = mPlatform.getAuthorisationRequester();
        requester.parseRequestResponse(response, this);
    }

    private void launchDialog(OAuthRequest request) {
        DialogAuthSharing dialog = DialogAuthSharing.newDialog(request);
        DialogShower.show(dialog, mActivity, AUTHORISATION, AuthTag);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == AUTHORISATION) {
            if(requestCode == Activity.RESULT_OK) {
                mListener.onAuthorisationGiven(mPlatform);
            } else {
                mListener.onAuthorisationRefused(mPlatform);
            }
        }
    }
}