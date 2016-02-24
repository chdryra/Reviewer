/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Implementation;

import android.content.Context;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.FacebookSdk;

import java.util.Set;

/**
 * Created by: Rizwan Choudrey
 * On: 23/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class PlatformFacebook extends SocialPlatformImpl<AccessToken>{
    public static final String NAME = "facebook";
    public static final String PUBLISH_PERMISSION = "publish_actions";

    private AccessTokenTracker mTracker;

    public PlatformFacebook(Context context, PublisherFacebook publisher) {
        super(publisher);

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

    private void setAccessToken() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        Set<String> permissions = accessToken.getPermissions();
        if(permissions.contains(PUBLISH_PERMISSION)) {
            setAccessToken(accessToken);
        } else {
            setAccessToken(null);
        }
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
