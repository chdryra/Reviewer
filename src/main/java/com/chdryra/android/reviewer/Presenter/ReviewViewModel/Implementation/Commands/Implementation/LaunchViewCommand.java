/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation;


import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Fragments.FragmentFormatReview;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.View.LauncherModel.Implementation.UiLauncherArgs;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.UiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 31/10/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class LaunchViewCommand extends Command {
    private final ReviewView<?> mView;
    private final UiLauncher mLauncher;

    public LaunchViewCommand(UiLauncher launcher, ReviewView<?> view) {
        mView = view;
        mLauncher = launcher;
    }

    @Override
    public void execute() {
        int code = RequestCodeGenerator.getCode(FragmentFormatReview.class, mView.getLaunchTag());
        mLauncher.launch(mView, new UiLauncherArgs(code));
    }
}
