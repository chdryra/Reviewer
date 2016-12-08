/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Factories;

import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.ReviewStamp;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepository;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.BannerButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.GridItemAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.RatingBarAction;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .Implementation.GridItemSummary;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .Implementation.MenuViewDataDefault;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .Implementation.RatingBarCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Factories
        .FactoryCommands;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.Command;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.LaunchFormattedCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSize;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.UiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 27/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryActionsViewSummary extends FactoryActionsViewData<GvSize.Reference> {
    private final ReviewNode mNode;

    public FactoryActionsViewSummary(FactoryReviewView factoryView,
                                     FactoryCommands factoryCommands,
                                     Command distribution,
                                     ReviewStamp stamp,
                                     AuthorsRepository repo,
                                     UiLauncher launcher,
                                     ReviewNode node) {
        super(GvSize.Reference.TYPE, factoryView, factoryCommands, stamp, repo, launcher,
                distribution, null);
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
    public BannerButtonAction<GvSize.Reference> newBannerButton() {
        return newBannerButton(Strings.Buttons.SUMMARY);
    }

    @Override
    public GridItemAction<GvSize.Reference> newGridItem() {
        return new GridItemSummary(getLauncher(), getViewFactory(), launchFormatted());
    }

    private LaunchFormattedCommand launchFormatted() {
        return getCommandsFactory().newLaunchFormattedCommand(mNode);
    }
}
