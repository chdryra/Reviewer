/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands.Implementation;

import com.chdryra.android.corelibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.startouch.View.LauncherModel.Implementation.UiLauncherArgs;
import com.chdryra.android.startouch.View.LauncherModel.Interfaces.UiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 15/08/2017
 * Email: rizwan.choudrey@gmail.com
 */
public class LaunchViewCommand extends Command {
    private final ViewCreator mCreator;
    private final UiLauncher mLauncher;

    public interface ViewCreator {
        ReviewView<?> newView();
    }

    public LaunchViewCommand(String name, final ReviewView<?> view, UiLauncher launcher) {
        super(name);
        mCreator = new ViewCreator() {
            @Override
            public ReviewView<?> newView() {
                return view;
            }
        };
        mLauncher = launcher;
    }

    public LaunchViewCommand(String name, ViewCreator creator, UiLauncher launcher) {
        super(name);
        mCreator = creator;
        mLauncher = launcher;
    }

    @Override
    public void execute() {
        ReviewView<?> view = mCreator.newView();
        if(view == null) return;
        int code = RequestCodeGenerator.getCode(view.getClass(), view.getLaunchTag());
        mLauncher.launch(view, new UiLauncherArgs(code));
    }
}
