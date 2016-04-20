/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Factories;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.Social.Implementation.GoogleLoginResultHandler;
import com.chdryra.android.reviewer.Authentication.LoginFailure;
import com.chdryra.android.reviewer.Authentication.LoginSuccess;
import com.chdryra.android.reviewer.Social.Implementation.PlatformGoogle;
import com.chdryra.android.reviewer.Social.Implementation.SocialPlatformList;
import com.chdryra.android.reviewer.Authentication.LoginResultHandler;

/**
 * Created by: Rizwan Choudrey
 * On: 02/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryLoginResultHandler {
    private SocialPlatformList mPlatforms;

    public FactoryLoginResultHandler(SocialPlatformList platforms) {
        mPlatforms = platforms;
    }

    public LoginResultHandler newSocialHandler(String platform) {
        if(platform.equals(PlatformGoogle.NAME)) {
            PlatformGoogle google = (PlatformGoogle)mPlatforms.getPlatform(platform);
            return new GoogleLoginResultHandler(google);
        } else {
            return newDoNothingLoginResultListener();
        }
    }

    @NonNull
    private LoginResultHandler newDoNothingLoginResultListener() {
        return new LoginResultHandler() {
            @Override
            public void onSuccess(LoginSuccess<?> loginSuccess) {

            }

            @Override
            public void onFailure(LoginFailure<?> loginFailure) {

            }
        };
    }
}
