/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Implementation;

import android.os.Bundle;
import android.view.MenuItem;

import com.chdryra.android.startouch.Authentication.Implementation.AuthenticatedUser;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Utils.ParcelablePacker;
import com.chdryra.android.startouch.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.startouch.View.LauncherModel.Implementation.UiLauncherArgs;

/**
 * Created by: Rizwan Choudrey
 * On: 10/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class MaiProfile<T extends GvData> extends MenuActionItemBasic<T>{
    private final LaunchableConfig mProfileEditor;

    public MaiProfile(LaunchableConfig profileEditor) {
        mProfileEditor = profileEditor;
    }

    @Override
    public void doAction(MenuItem item) {
        AuthenticatedUser user = getApp().getAccounts().getUserSession().getUser();
        Bundle args = new Bundle();
        ParcelablePacker<AuthenticatedUser> packer = new ParcelablePacker<>();
        packer.packItem(ParcelablePacker.CurrentNewDatum.CURRENT, user, args);
        mProfileEditor.launch(new UiLauncherArgs(mProfileEditor.getDefaultRequestCode()).setBundle(args));
    }
}