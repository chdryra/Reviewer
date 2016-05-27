/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewContainer;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.UiLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;

/**
 * Created by: Rizwan Choudrey
 * On: 27/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class BuildScreenShareButton {
    private static final int LAUNCH_SHARE_SCREEN = RequestCodeGenerator.getCode("ShareScreen");
    private static final int TOAST_ENTER_SUBJECT = R.string.toast_enter_subject;

    private UiLauncher mLauncher;
    private LaunchableUi mShareScreenUi;

    public BuildScreenShareButton(UiLauncher launcher, LaunchableUi shareScreenUi) {
        mLauncher = launcher;
        mShareScreenUi = shareScreenUi;
    }

    public void execute(ReviewView reviewView) {
        ReviewViewContainer container = reviewView.getContainer();
        Activity activity = container.getActivity();

        if (container.getSubject().length() == 0) {
            Toast.makeText(activity, TOAST_ENTER_SUBJECT, Toast.LENGTH_SHORT).show();
            return;
        }

        mLauncher.launch(mShareScreenUi, activity, LAUNCH_SHARE_SCREEN, new Bundle());
    }
}
