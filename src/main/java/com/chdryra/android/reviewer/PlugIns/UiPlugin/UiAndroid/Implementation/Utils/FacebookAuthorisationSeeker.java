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

import com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Activities.ActivityFacebookLogin;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ActivityResultListener;
import com.chdryra.android.reviewer.Social.Interfaces.PlatformAuthoriser;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPlatform;
import com.chdryra.android.reviewer.Utils.RequestCodeGenerator;
import com.facebook.AccessToken;

/**
 * Created by: Rizwan Choudrey
 * On: 15/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FacebookAuthorisationSeeker implements PlatformAuthorisationSeeker,
        ActivityResultListener{
    private static final int AUTHORISATION = RequestCodeGenerator.getCode("FacebookAuthorisation");

    private Activity mActivity;
    private SocialPlatform<AccessToken> mPlatform;
    private PlatformAuthoriser.AuthorisationListener mListener;

    public FacebookAuthorisationSeeker(Activity activity, SocialPlatform<AccessToken> platform,
                                       PlatformAuthoriser.AuthorisationListener listener) {
        mActivity = activity;
        mPlatform = platform;
        mListener = listener;
    }

    @Override
    public void seekAuthorisation() {
        Intent intent = new Intent(mActivity, ActivityFacebookLogin.class);
        mActivity.startActivityForResult(intent, AUTHORISATION);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == AUTHORISATION) {
            if(requestCode == Activity.RESULT_OK) {
                mPlatform.setAccessToken(AccessToken.getCurrentAccessToken());
                mListener.onAuthorisationGiven(mPlatform);
            } else if(requestCode == Activity.RESULT_FIRST_USER){
                mListener.onAuthorisationRefused(mPlatform);
            }
        }
    }
}
