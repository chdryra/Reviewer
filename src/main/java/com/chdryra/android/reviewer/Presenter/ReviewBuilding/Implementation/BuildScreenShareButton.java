/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import android.os.Bundle;

import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.reviewer.Application.ApplicationInstance;
import com.chdryra.android.reviewer.Application.Strings;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewContainer;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;

/**
 * Created by: Rizwan Choudrey
 * On: 27/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class BuildScreenShareButton {
    private static final int LAUNCH_SHARE_SCREEN = RequestCodeGenerator.getCode("ShareScreen");

    private LaunchableUi mShareScreenUi;

    public BuildScreenShareButton(LaunchableUi shareScreenUi) {
        mShareScreenUi = shareScreenUi;
    }

    public void execute(ReviewView reviewView) {
        ReviewViewContainer container = reviewView.getContainer();
        ApplicationInstance app = container.getApp();

        if (container.getSubject().length() == 0) {
            app.getCurrentScreen().showToast(Strings.Toasts.ENTER_SUBJECT);
            return;
        }

        app.getUiLauncher().launch(mShareScreenUi, LAUNCH_SHARE_SCREEN, new Bundle());
    }
}
