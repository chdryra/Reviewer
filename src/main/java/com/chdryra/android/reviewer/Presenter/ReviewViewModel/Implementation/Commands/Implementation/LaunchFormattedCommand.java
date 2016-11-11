/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation;



import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.ReviewLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 26/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class LaunchFormattedCommand extends Command {
    private final ReviewLauncher mLauncher;
    private final ReviewNode mNode;

    public LaunchFormattedCommand(ReviewLauncher launcher, @Nullable ReviewNode node) {
        mLauncher = launcher;
        mNode = node;
    }

    public void execute(@Nullable ReviewNode node, boolean isPreview) {
        if(node != null) mLauncher.launchFormatted(node, isPreview);
        onExecutionComplete();
    }

    @Override
    public void execute() {
        execute(mNode, false);
    }
}
