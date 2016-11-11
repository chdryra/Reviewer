/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.UiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 26/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class LaunchEditorCommand extends Command {
    private final UiLauncher mLauncher;
    private final ReviewId mTemplate;

    public LaunchEditorCommand(UiLauncher launcher, @Nullable ReviewId template) {
        mLauncher = launcher;
        mTemplate = template;
    }

    @Override
    public void execute() {
        mLauncher.launchEditUi(mTemplate);
        onExecutionComplete();
    }
}
