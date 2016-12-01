/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation;

import android.view.View;

import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.LaunchFormattedCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.LaunchReviewOptionsCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvNode;

/**
 * Created by: Rizwan Choudrey
 * On: 18/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GridItemLaunchFormatted extends GridItemCommand<GvNode> {
    public GridItemLaunchFormatted(LaunchFormattedCommand click, LaunchReviewOptionsCommand longClick) {
        super(click, longClick, Strings.LOADING);
    }

    @Override
    public void onGridItemClick(GvNode item, int position, View v) {
        ((LaunchFormattedCommand)getClick()).execute(item.getNode(), true);
    }

    @Override
    public void onGridItemLongClick(GvNode item, int position, View v) {
        ((LaunchReviewOptionsCommand)getLongClick()).execute(item.getAuthorId());
    }
}
