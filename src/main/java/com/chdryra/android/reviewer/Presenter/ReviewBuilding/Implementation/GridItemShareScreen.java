/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import android.support.annotation.NonNull;
import android.view.View;

import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.GridItemActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSocialPlatform;

import com.chdryra.android.reviewer.Social.Interfaces.PlatformAuthoriser;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPlatform;

/**
 * Created by: Rizwan Choudrey
 * On: 18/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GridItemShareScreen extends GridItemActionNone<GvSocialPlatform> {
    private static final String AUTHORISATION_NOT_RECEIVED = "authorisation not received";

    private final PlatformAuthoriser mAuthoriser;

    public GridItemShareScreen(PlatformAuthoriser authoriser) {
        mAuthoriser = authoriser;
    }

    @Override
    public void onGridItemClick(final GvSocialPlatform platform, final int position, final View v) {
        if (platform.isAuthorised()) {
            platform.press();
        } else {
            mAuthoriser.seekAuthorisation(platform.getPlatform(), callback(platform, position, v));
        }
    }

    @NonNull
    private PlatformAuthoriser.Callback callback(final GvSocialPlatform platform, final int position, final View v) {
        return new PlatformAuthoriser.Callback() {
            @Override
            public void onAuthorisationGiven(SocialPlatform<?> socialPlatform) {
                onGridItemLongClick(platform, position, v);
            }

            @Override
            public void onAuthorisationRefused(SocialPlatform<?> platform) {
                showToast(platform.getName() + ": " + AUTHORISATION_NOT_RECEIVED);
            }
        };
    }
}
