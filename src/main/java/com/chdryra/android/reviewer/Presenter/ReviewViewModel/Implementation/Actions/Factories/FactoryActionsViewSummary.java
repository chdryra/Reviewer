/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Factories;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.GridItemAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.RatingBarAction;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.BannerButtonSelector;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.GridItemSummary;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MenuViewDataDefault;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.RatingBarCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.CommandsList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.LaunchBespokeViewCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.OptionsSelector;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSize;

/**
 * Created by: Rizwan Choudrey
 * On: 27/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryActionsViewSummary extends FactoryActionsViewData<GvSize.Reference> {
    private final ReviewNode mNode;

    public FactoryActionsViewSummary(ViewDataParameters<GvSize.Reference> parameters,
                                     ReviewNode node) {
        super(parameters.setButtonTitle(Strings.Buttons.SUMMARY));
        mNode = node;
    }

    @Override
    public MenuAction<GvSize.Reference> newMenu() {
        return new MenuViewDataDefault<>(Strings.Screens.SUMMARY, newOptionsMenuItem());
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

    @NonNull
    @Override
    protected BannerButtonSelector<GvSize.Reference> newBannerButtonSelector(String buttonTitle,
                                                                             OptionsSelector selector) {
        return new BannerButtonSelector<>(buttonTitle, new CommandsList(), selector);
    }

    private LaunchBespokeViewCommand launchFormatted() {
        return getCommandsFactory().newLaunchFormattedCommand(mNode);
    }
}
