/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Social.Implementation;

import android.content.Context;

import com.chdryra.android.startouch.Social.Interfaces.AuthorisationTokenGetter;
import com.chdryra.android.startouch.Social.Interfaces.LoginUi;
import com.chdryra.android.startouch.Social.Interfaces.PlatformAuthoriser;
import com.chdryra.android.startouch.View.LauncherModel.Interfaces.LaunchableUi;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;

import org.json.JSONArray;

import java.util.Set;

/**
 * Created by: Rizwan Choudrey
 * On: 23/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class PlatformFacebook extends SocialPlatformBasic<AccessToken> {
    public static final String NAME = "facebook";
    public static final String REQUIRED_PERMISSION = "publish_actions";

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
        if(accessToken != null) {
            Set<String> permissions = accessToken.getPermissions();
            if (permissions.contains(REQUIRED_PERMISSION)) {
                setAuthorisation(accessToken);
            } else {
                setAuthorisation(null);
            }
        } else {
            setAuthorisation(null);
        }
    }

    private void setAccessTracker() {
        AccessTokenTracker tracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken
                    currentAccessToken) {
                setAccessToken();
            }
        };
    }

    @Override
    public void getFollowers(final FollowersListener listener) {
        GraphRequest request = GraphRequest.newMyFriendsRequest(
                getAccessToken(), new GraphRequest.GraphJSONArrayCallback() {
                    @Override
                    public void onCompleted(JSONArray objects, GraphResponse response) {
                        listener.onNumberFollowers(objects.length());
                    }
                }
        );

        request.executeAsync();
    }

    @Override
    public LoginUi getLoginUi(LaunchableUi loginLaunchable, PlatformAuthoriser.Callback listener) {
        return new LoginUiDefault<>(loginLaunchable, this,
                listener, new AuthorisationTokenGetter<AccessToken>() {
            @Override
            public AccessToken getAuthorisationToken() {
                return AccessToken.getCurrentAccessToken();
            }
        });
    }

    @Override
    public void logout() {
        LoginManager.getInstance().logOut();
        setAuthorisation(null);
    }
}
