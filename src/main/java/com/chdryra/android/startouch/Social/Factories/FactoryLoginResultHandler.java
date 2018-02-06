/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Social.Factories;

import com.chdryra.android.mygenerallibrary.AsyncUtils.BinaryResultCallback;
import com.chdryra.android.startouch.Social.Implementation.PlatformGoogle;
import com.chdryra.android.startouch.Social.Implementation.SocialPlatformList;

/**
 * Created by: Rizwan Choudrey
 * On: 02/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryLoginResultHandler {
    private final SocialPlatformList mPlatforms;

    public FactoryLoginResultHandler(SocialPlatformList platforms) {
        mPlatforms = platforms;
    }

    public BinaryResultCallback<?,?> newSocialLoginHandler(String platform) {
        if(platform.equals(PlatformGoogle.NAME)) {
            PlatformGoogle google = (PlatformGoogle) mPlatforms.getPlatform(PlatformGoogle.NAME);
            return google.newSignInResultHandler();
        } else {
            return new BinaryResultCallback() {
                @Override
                public void onSuccess(Object result) {

                }

                @Override
                public void onFailure(Object result) {

                }
            };
        }
    }
}
