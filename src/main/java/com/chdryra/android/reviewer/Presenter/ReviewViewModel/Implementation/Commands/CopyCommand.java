/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands;

import com.chdryra.android.reviewer.Application.CurrentScreen;
import com.chdryra.android.reviewer.Application.Strings;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.BuildScreenLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 26/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class CopyCommand extends Command {
    private CurrentScreen mScreen;
    private BuildScreenLauncher mLauncher;
    private ReviewId mReviewId;

    public CopyCommand(int requestCode, ExecutionListener listener, CurrentScreen screen,
                       BuildScreenLauncher launcher, ReviewId reviewId) {
        super(requestCode, listener);
        mScreen = screen;
        mLauncher = launcher;
        mReviewId = reviewId;
    }

    @Override
    public void execute() {
        mScreen.showToast(Strings.Toasts.COPYING);
        mLauncher.launch(mReviewId);
        onExecutionComplete();
    }
}
