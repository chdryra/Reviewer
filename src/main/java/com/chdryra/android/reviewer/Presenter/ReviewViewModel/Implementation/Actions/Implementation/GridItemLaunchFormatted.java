/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .Implementation;

import android.view.View;

import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.LaunchFormattedCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.ReviewOptionsSelector;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvNode;

/**
 * Created by: Rizwan Choudrey
 * On: 18/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GridItemLaunchFormatted extends GridItemCommand<GvNode> {
    public GridItemLaunchFormatted(LaunchFormattedCommand click,
                                   ReviewOptionsSelector longClick) {
        super(click, longClick, Strings.Progress.LOADING);
    }

    @Override
    public void onGridItemClick(GvNode item, int position, View v) {
        ((LaunchFormattedCommand) getClick()).execute(item.getNode(), true);
    }

    @Override
    public void onGridItemLongClick(GvNode item, int position, View v) {
        getOptionsCommand().execute(item.getAuthorId());
    }

    @Override
    public boolean onOptionSelected(int requestCode, String option) {
        return getOptionsCommand().onOptionSelected(requestCode, option);
    }

    private ReviewOptionsSelector getOptionsCommand() {
        return (ReviewOptionsSelector) getLongClick();
    }
}
