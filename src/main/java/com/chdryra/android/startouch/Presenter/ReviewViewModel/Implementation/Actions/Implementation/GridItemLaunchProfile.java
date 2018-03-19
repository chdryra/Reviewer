/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions
        .Implementation;


import android.view.View;

import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.LaunchProfileCommand;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvAuthorName;

/**
 * Created by: Rizwan Choudrey
 * On: 18/02/2018
 * Email: rizwan.choudrey@gmail.com
 */

public class GridItemLaunchProfile extends GridItemActionNone<GvAuthorName> {
    private final LaunchProfileCommand mLaunchProfile;

    public GridItemLaunchProfile(LaunchProfileCommand launchProfile) {
        mLaunchProfile = launchProfile;
    }

    @Override
    public void onGridItemClick(GvAuthorName item, int position, View v) {
        mLaunchProfile.execute(item.getAuthorId());
    }
}
