/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Implementation;

import android.view.MenuItem;

import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands.Implementation.LaunchProfileCommand;

/**
 * Created by: Rizwan Choudrey
 * On: 10/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class MaiProfileEdit<T extends GvData> extends MenuActionItemBasic<T>{
    private final LaunchProfileCommand mProfile;

    public MaiProfileEdit(LaunchProfileCommand profile) {
        mProfile = profile;
    }

    @Override
    public void doAction(MenuItem item) {
        mProfile.execute();
    }
}
