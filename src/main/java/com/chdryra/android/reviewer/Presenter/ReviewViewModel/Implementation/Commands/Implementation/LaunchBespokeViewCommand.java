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
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.ReviewLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 26/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class LaunchBespokeViewCommand extends Command {
    private final ReviewLauncher mLauncher;
    private final ReviewNode mNode;
    private final GvDataType<?> mDataType;

    public LaunchBespokeViewCommand(String commandName,
                                    ReviewLauncher launcher,
                                    @Nullable ReviewNode node,
                                    GvDataType<?> dataType) {
        super(commandName);
        mLauncher = launcher;
        mNode = node;
        mDataType = dataType;
    }

    public void execute(@Nullable ReviewNode node, boolean isPublished) {
        execute(node, 0, isPublished);
    }

    public void execute(@Nullable ReviewNode node, int datumIndex, boolean isPublished) {
        if(node != null) mLauncher.launchNodeView(node, mDataType, datumIndex, isPublished);
        onExecutionComplete();
    }

    public void execute(int datumIndex) {
        execute(mNode, datumIndex, true);
    }

    @Override
    public void execute() {
        execute(mNode, 0, true);
    }
}
