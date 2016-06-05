/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import android.view.View;

import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.reviewer.Application.ApplicationInstance;
import com.chdryra.android.reviewer.Application.Strings;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.ContextualButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewContainer;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 27/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class BuildScreenShareButton<T extends GvData> extends ReviewEditorActionBasic<T>
        implements ContextualButtonAction<T>{
    private static final int SHARE_SCREEN = RequestCodeGenerator.getCode("ShareScreen");

    private LaunchableConfig mConfig;

    public BuildScreenShareButton(LaunchableConfig config) {
        mConfig = config;
    }

    @Override
    public boolean onLongClick(View v) {
        onClick(v);
        return true;
    }

    @Override
    public void onClick(View v) {
        ReviewViewContainer container = getReviewView().getContainer();
        ApplicationInstance app = container.getApp();

        if (container.getSubject().length() == 0) {
            app.getCurrentScreen().showToast(Strings.Toasts.ENTER_SUBJECT);
            return;
        }

        getApp().getUiLauncher().launch(mConfig, SHARE_SCREEN);
    }

    @Override
    public String getButtonTitle() {
        return Strings.Screens.SHARE;
    }
}