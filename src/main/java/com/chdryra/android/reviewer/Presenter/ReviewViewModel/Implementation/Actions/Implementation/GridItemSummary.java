/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation;



import android.view.View;

import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.LaunchBespokeViewCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvNode;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSize;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.UiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 30/08/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GridItemSummary extends GridItemLauncher<GvSize.Reference> {
    private final LaunchBespokeViewCommand mCommand;

    public GridItemSummary(UiLauncher launcher,
                           FactoryReviewView factory,
                           LaunchBespokeViewCommand command) {
        super(launcher, factory);
        mCommand = command;
    }

    @Override
    void onClickNotExpandable(GvSize.Reference item, int position, View v) {
        if(item.getSizedType().equals(GvNode.TYPE)) mCommand.execute();
    }
}
