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
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.BuildScreenLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 26/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class NewReviewCommand extends Command {
    private BuildScreenLauncher mLauncher;
    private ReviewId mTemplate;

    public NewReviewCommand(BuildScreenLauncher launcher, @Nullable ReviewId template) {
        mLauncher = launcher;
        mTemplate = template;
    }

    public NewReviewCommand() {
    }

    public void setLauncher(BuildScreenLauncher launcher) {
        mLauncher = launcher;
    }

    @Override
    void execute() {
        execute(mTemplate);
    }

    void execute(@Nullable ReviewId template) {
        if(mLauncher != null) mLauncher.launch(template);
        onExecutionComplete();
    }
}
