/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Implementation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.ParcelablePacker;
import com.chdryra.android.reviewer.Social.Interfaces.OAuthListener;
import com.chdryra.android.reviewer.Social.Interfaces.OAuthRequester;
import com.chdryra.android.reviewer.Social.Interfaces.PlatformAuthorisationSeeker;
import com.chdryra.android.reviewer.Social.Interfaces.PlatformAuthoriser;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPlatform;
import com.chdryra.android.reviewer.Utils.RequestCodeGenerator;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;

/**
 * Created by: Rizwan Choudrey
 * On: 15/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class DefaultOAuthSeeker<T> implements
        OAuthListener,
        OAuthRequester.RequestListener<T>,
        PlatformAuthorisationSeeker {
    private static final int AUTHORISATION = RequestCodeGenerator.getCode("PlatformAuthorisation");

    private Activity mActivity;
    private LaunchableUi mAuthorisationUi;
    private LaunchableUiLauncher mLauncher;
    private SocialPlatform<T> mPlatform;
    private PlatformAuthoriser.AuthorisationListener mListener;
    private ParcelablePacker<OAuthRequest>mPacker;

    public DefaultOAuthSeeker(Activity activity,
                              LaunchableUi authorisationUi,
                              LaunchableUiLauncher launcher,
                              SocialPlatform<T> platform,
                              PlatformAuthoriser.AuthorisationListener listener,
                              ParcelablePacker<OAuthRequest> packer) {
        mActivity = activity;
        mAuthorisationUi = authorisationUi;
        mLauncher = launcher;
        mPlatform = platform;
        mListener = listener;
        mPacker = packer;
    }

    @Override
    public void seekAuthorisation() {
        OAuthRequester<T> requester = mPlatform.getOAuthRequester();
        requester.generateAuthorisationRequest(this);
    }

    @Override
    public void onRequestGenerated(OAuthRequest request) {
        launchUi(request);
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
        OAuthRequester<T> requester = mPlatform.getOAuthRequester();
        requester.parseRequestResponse(response, this);
    }

    private void launchUi(OAuthRequest request) {
        Bundle args = new Bundle();
        mPacker.packItem(ParcelablePacker.CurrentNewDatum.CURRENT, request, args);
        mLauncher.launch(mAuthorisationUi, mActivity, AUTHORISATION, args);
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
