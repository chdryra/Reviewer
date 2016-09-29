/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation;

import android.view.MenuItem;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataAuthorId;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.LaunchOptionsCommand;

/**
 * Created by: Rizwan Choudrey
 * On: 26/09/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class MaiReviewOptions<T extends GvData> extends MenuActionItemBasic<T> {
    private final LaunchOptionsCommand mCommand;
    private final DataAuthorId mAuthorId;

    public MaiReviewOptions(LaunchOptionsCommand command, DataAuthorId authorId) {
        mCommand = command;
        mAuthorId = authorId;
    }

    @Override
    public void doAction(MenuItem item) {
        if(getParent() == null) return;
        mCommand.execute(mAuthorId, getApp().newUiLauncher());
    }
}
