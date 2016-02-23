/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Implementation;

import android.content.Context;

import com.chdryra.android.reviewer.Social.Interfaces.SocialPlatform;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.FacebookSdk;

/**
 * Created by: Rizwan Choudrey
 * On: 23/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class PlatformFacebook {
    private static PlatformFacebook sFacebook;
    private SocialPlatform<AccessToken> mPlatform;
    private AccessTokenTracker mTracker;

    public PlatformFacebook(Context context) {
        PublisherFacebook publisher = new PublisherFacebook(new ReviewSummariser(),
                new ReviewFormatterDefault());
        mPlatform = new SocialPlatformImpl<>(context, publisher, null);

        if (!FacebookSdk.isInitialized()) {
            initialiseFacebook(context);
        } else {
            setAccessToken();
        }

        setAccessTracker();
    }

    private void initialiseFacebook(Context context) {
        FacebookSdk.sdkInitialize(context, new FacebookSdk.InitializeCallback() {
            @Override
            public void onInitialized() {
                setAccessToken();
            }
        });
    }

    public static SocialPlatform<AccessToken> getInstance(Context context) {
        if (sFacebook == null) sFacebook = new PlatformFacebook(context);
        return sFacebook.mPlatform;
    }

    private void setAccessToken() {
        mPlatform.setAccessToken(AccessToken.getCurrentAccessToken());
    }

    private void setAccessTracker() {
        mTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken
                    currentAccessToken) {
                setAccessToken();
            }
        };
    }
}
