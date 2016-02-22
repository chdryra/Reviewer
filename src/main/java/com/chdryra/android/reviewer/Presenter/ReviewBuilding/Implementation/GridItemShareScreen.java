/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import android.view.View;
import android.widget.Toast;

import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .GridItemActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvSocialPlatform;


import com.chdryra.android.reviewer.Social.Interfaces.PlatformAuthoriser;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPlatform;

/**
 * Created by: Rizwan Choudrey
 * On: 18/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GridItemShareScreen extends GridItemActionNone<GvSocialPlatform>
        implements PlatformAuthoriser.AuthorisationListener {
    private static final String AUTHORISATION_RECEIVED = "authorisation received";
    private static final String AUTHORISATION_NOT_RECEIVED = "authorisation not received";

    private PlatformAuthoriser mAuthoriser;
    private GvSocialPlatform mPlatformSeekingAuthorisation;
    private View mViewForAuthorisation;

    public GridItemShareScreen(PlatformAuthoriser authoriser) {
        mAuthoriser = authoriser;
    }

    @Override
    public void onGridItemClick(GvSocialPlatform platform, int position, View v) {
        if (platform.isAuthorised()) {
            pressPlatform(platform, v);
        } else {
            mPlatformSeekingAuthorisation = platform;
            mViewForAuthorisation = v;
            mAuthoriser.seekAuthorisation(platform.getPlatform(), this);
        }
    }

    @Override
    public void onAuthorisationGiven(SocialPlatform<?> platform) {
        pressPlatform(mPlatformSeekingAuthorisation, mViewForAuthorisation);
        getReviewView().onGridDataChanged();
        makeToast(platform, AUTHORISATION_RECEIVED);
        mPlatformSeekingAuthorisation = null;
        mViewForAuthorisation = null;
    }

    @Override
    public void onAuthorisationRefused(SocialPlatform<?> platform) {
        makeToast(platform, AUTHORISATION_NOT_RECEIVED);
    }

    private void pressPlatform(GvSocialPlatform platform, View v) {
        platform.press();
        v.setActivated(platform.isChosen());
    }

    private void makeToast(SocialPlatform<?> platform, String message) {
        Toast.makeText(getActivity(), platform.getName() + ": " + message,
                Toast.LENGTH_SHORT).show();
    }
}
