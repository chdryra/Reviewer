/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Factories;

import com.chdryra.android.startouch.Application.Implementation.Strings;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.GridItemAction;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.RatingBarAction;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions
        .Implementation.ActionsParameters;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Implementation.GridItemSummary;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MenuViewDataDefault;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Implementation.RatingBarCommand;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands.Implementation.LaunchBespokeViewCommand;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSize;

/**
 * Created by: Rizwan Choudrey
 * On: 27/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryActionsViewSummary extends FactoryActionsViewData<GvSize.Reference> {
    private final ReviewNode mNode;

    public FactoryActionsViewSummary(ActionsParameters<GvSize.Reference> parameters,
                                     ReviewNode node) {
        super(parameters);
        mNode = node;
    }

    @Override
    public MenuAction<GvSize.Reference> newMenu() {
        return new MenuViewDataDefault<>(Strings.Screens.FILTER, newOptionsMenuItem());
    }

    @Override
    public RatingBarAction<GvSize.Reference> newRatingBar() {
        return hasStamp() ?
                new RatingBarCommand<GvSize.Reference>(launchFormatted(), Strings.Progress.LOADING)
                : super.newRatingBar();
    }

    @Override
    public GridItemAction<GvSize.Reference> newGridItem() {
        return new GridItemSummary(getLauncher(), getViewFactory(), launchFormatted());
    }

    private LaunchBespokeViewCommand launchFormatted() {
        return getCommandsFactory().newLaunchPagedCommand(mNode);
    }
}
