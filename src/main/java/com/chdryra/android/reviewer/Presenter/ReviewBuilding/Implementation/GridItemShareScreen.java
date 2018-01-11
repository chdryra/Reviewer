/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import android.view.View;

import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.GridItemActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSocialPlatform;
import com.chdryra.android.reviewer.Social.Interfaces.AuthorisationListener;
import com.chdryra.android.reviewer.Social.Interfaces.PlatformAuthoriser;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPlatform;

/**
 * Created by: Rizwan Choudrey
 * On: 18/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GridItemShareScreen extends GridItemActionNone<GvSocialPlatform>
        implements AuthorisationListener {
    private static final String AUTHORISATION_RECEIVED = "authorisation received";
    private static final String AUTHORISATION_NOT_RECEIVED = "authorisation not received";

    private final PlatformAuthoriser mAuthoriser;
    private View mViewForAuthorisation;

    public GridItemShareScreen(PlatformAuthoriser authoriser) {
        mAuthoriser = authoriser;
    }

    @Override
    public void onGridItemClick(GvSocialPlatform platform, int position, View v) {
        if (platform.isAuthorised()) {
            platform.press();
            v.setActivated(platform.isChosen());
        } else {
            mViewForAuthorisation = v;
            mAuthoriser.seekAuthorisation(platform.getPlatform(), this);
        }
    }

    @Override
    public void onAuthorisationGiven(SocialPlatform<?> platform) {
        makeToast(platform, AUTHORISATION_RECEIVED);
        mViewForAuthorisation.setActivated(true);
        mViewForAuthorisation = null;
    }

    @Override
    public void onAuthorisationRefused(SocialPlatform<?> platform) {
        makeToast(platform, AUTHORISATION_NOT_RECEIVED);
    }

    private void makeToast(SocialPlatform<?> platform, String message) {
        showToast(platform.getName() + ": " + message);
    }
}
