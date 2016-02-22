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

import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.GridItemActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSocialPlatform;


import com.chdryra.android.reviewer.Social.Interfaces.PlatformAuthoriser;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPlatform;

/**
 * Created by: Rizwan Choudrey
 * On: 18/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GridItemShareScreen extends GridItemActionNone<GvSocialPlatform>
        implements PlatformAuthoriser.AuthorisationListener{
    private PlatformAuthoriser mAuthoriser;
    private GvSocialPlatform mPlatformSeekingAuthorisation;
    private View mViewForAuthorisation;

    public GridItemShareScreen(PlatformAuthoriser authoriser) {
        mAuthoriser = authoriser;
    }

    @Override
    public void onGridItemClick(GvSocialPlatform platform, int position, View v) {
        if(platform.isAuthorised()) {
            pressPlatform(platform, v);
        } else if(platform.getName().equals("tumblr") || platform.getName().equals("twitter")) {
            mPlatformSeekingAuthorisation = platform;
            mViewForAuthorisation = v;
            mAuthoriser.seekAuthorisation(platform.getPlatform(), this);
        } else {
            Toast.makeText(getActivity(), platform.getName() + " not currently authorised",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void pressPlatform(GvSocialPlatform platform, View v) {
        platform.press();
        v.setActivated(platform.isChosen());
    }

    @Override
    public void onAuthorisationGiven(SocialPlatform<?> platform) {
        pressPlatform(mPlatformSeekingAuthorisation, mViewForAuthorisation);
        getReviewView().onGridDataChanged();
        Toast.makeText(getActivity(), platform.getName() + ": authorisation received",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAuthorisationRefused(SocialPlatform<?> platform) {
        Toast.makeText(getActivity(), platform.getName() + ": authorisation not received",
                Toast.LENGTH_SHORT).show();
    }
}
