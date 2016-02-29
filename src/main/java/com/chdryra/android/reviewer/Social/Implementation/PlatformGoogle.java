/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Implementation;

import android.app.Activity;

import com.chdryra.android.reviewer.Social.Interfaces.AccessTokenGetter;
import com.chdryra.android.reviewer.Social.Interfaces.AuthorisationListener;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPlatformAuthUi;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;

/**
 * Created by: Rizwan Choudrey
 * On: 23/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class PlatformGoogle extends SocialPlatformBasic<String> {
    public static final String NAME = "google";

    public PlatformGoogle(PublisherGoogle publisher) {
        super(publisher);
    }

    @Override
    public SocialPlatformAuthUi getAuthUi(Activity activity, LaunchableUi authorisationUi,
                                          LaunchableUiLauncher launcher,
                                          AuthorisationListener listener) {
        return new SocialPlatformAuthUiDefault<>(activity, authorisationUi, launcher, this,
                listener,
                new AccessTokenGetter<String>() {
            @Override
            public String getAccessToken() {
                return PlatformGoogle.this.getAccessToken();
            }
        });
    }
}
