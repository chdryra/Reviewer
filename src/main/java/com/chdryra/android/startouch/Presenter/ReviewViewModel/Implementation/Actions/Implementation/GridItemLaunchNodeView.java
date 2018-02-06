/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions
        .Implementation;

import android.view.View;

import com.chdryra.android.startouch.Application.Implementation.Strings;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands.Implementation.LaunchBespokeViewCommand;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands.Implementation.ReviewOptionsSelector;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvNode;

/**
 * Created by: Rizwan Choudrey
 * On: 18/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GridItemLaunchNodeView extends GridItemCommand<GvNode> {
    public GridItemLaunchNodeView(LaunchBespokeViewCommand click,
                                  ReviewOptionsSelector longClick) {
        super(click, longClick, Strings.Progress.LOADING);
    }

    @Override
    public void onGridItemClick(GvNode item, int position, View v) {
        ((LaunchBespokeViewCommand) getClick()).execute(item.getNode(), true);
    }

    @Override
    public void onGridItemLongClick(GvNode item, int position, View v) {
        getOptionsCommand().execute(item.getAuthorId());
    }

    @Override
    public boolean onOptionSelected(int requestCode, String option) {
        return getOptionsCommand().onOptionSelected(requestCode, option);
    }

    @Override
    public boolean onOptionsCancelled(int requestCode) {
        return getOptionsCommand().onOptionsCancelled(requestCode);
    }

    private ReviewOptionsSelector getOptionsCommand() {
        return (ReviewOptionsSelector) getLongClick();
    }
}
