/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Social.Implementation;

import android.content.Intent;
import android.os.Bundle;

import com.chdryra.android.corelibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.startouch.Social.Interfaces.LoginUi;
import com.chdryra.android.startouch.Social.Interfaces.OAuthListener;
import com.chdryra.android.startouch.Social.Interfaces.OAuthRequester;
import com.chdryra.android.startouch.Social.Interfaces.PlatformAuthoriser;
import com.chdryra.android.startouch.Social.Interfaces.SocialPlatform;
import com.chdryra.android.startouch.Utils.ParcelablePacker;
import com.chdryra.android.startouch.View.LauncherModel.Implementation.UiLauncherArgs;
import com.chdryra.android.startouch.View.LauncherModel.Interfaces.LaunchableUi;
import com.chdryra.android.startouch.View.LauncherModel.Interfaces.UiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 15/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class DefaultOAuthUi<T> implements
        OAuthListener,
        OAuthRequester.RequestListener<T>,
        LoginUi {
    private static final int AUTHORISATION = RequestCodeGenerator.getCode("PlatformAuthorisation");

    private final LaunchableUi mAuthorisationUi;
    private final SocialPlatform<T> mPlatform;
    private final PlatformAuthoriser.Callback mListener;
    private final ParcelablePacker<OAuthRequest> mPacker;

    private UiLauncher mLauncher;

    public DefaultOAuthUi(LaunchableUi authorisationUi,
                          SocialPlatform<T> platform,
                          PlatformAuthoriser.Callback listener,
                          ParcelablePacker<OAuthRequest> packer) {
        mAuthorisationUi = authorisationUi;
        mPlatform = platform;
        mListener = listener;
        mPacker = packer;
    }

    @Override
    public void launchUi(UiLauncher launcher) {
        OAuthRequester<T> requester = mPlatform.getOAuthRequester();
        mLauncher = launcher;
        requester.generateAuthorisationRequest(this);
    }

    @Override
    public void onRequestGenerated(OAuthRequest request) {
        launchUi(request);
    }

    @Override
    public void onResponseParsed(T token) {
        if (token != null) {
            mPlatform.setAuthorisation(token);
            mListener.onAuthorisationGiven(mPlatform);
        } else {
            mListener.onAuthorisationRefused(mPlatform);
        }
    }

    @Override
    public void onAuthorisationCallback(OAuthRequest response) {
        OAuthRequester<T> requester = mPlatform.getOAuthRequester();
        requester.parseRequestResponse(response, this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        OAuthRequest response = null;
        if (requestCode == AUTHORISATION && data != null) {
            response = mPacker.unpack(ParcelablePacker.CurrentNewDatum.NEW, data);
        }

        if (response == null) {
            mListener.onAuthorisationRefused(mPlatform);
        } else {
            onAuthorisationCallback(response);
        }
    }

    private void launchUi(OAuthRequest request) {
        Bundle args = new Bundle();
        mPacker.packItem(ParcelablePacker.CurrentNewDatum.CURRENT, request, args);
        mLauncher.launch(mAuthorisationUi, new UiLauncherArgs(AUTHORISATION).setBundle(args));
        mLauncher = null;
    }
}
