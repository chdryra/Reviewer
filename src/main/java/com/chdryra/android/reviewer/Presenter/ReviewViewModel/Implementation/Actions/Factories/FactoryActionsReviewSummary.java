/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Factories;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.ReviewStamp;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepository;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.BannerButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.GridItemAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuActionItem;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.RatingBarAction;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.BannerButtonActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.BannerButtonLaunchAuthorReviews;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.GridItemLauncher;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MaiReviewOptions;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MenuActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MenuReviewOptions;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.RatingBarExecuteCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Factories.FactoryCommands;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.LaunchFormattedCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.LaunchOptionsCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSize;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.UiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 27/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryActionsReviewSummary extends FactoryActionsNone<GvSize.Reference> {
    private final FactoryReviewView mFactoryView;
    private final UiLauncher mLauncher;
    private final FactoryCommands mFactoryCommands;
    private final LaunchableConfig mOptionsConfig;
    private final ReviewStamp mStamp;
    private final AuthorsRepository mRepo;
    private final ReviewNode mNode;

    public FactoryActionsReviewSummary(FactoryReviewView factoryView,
                                       FactoryCommands factoryCommands,
                                       UiLauncher launcher,
                                       LaunchableConfig optionsConfig,
                                       ReviewStamp stamp,
                                       AuthorsRepository repo,
                                       @Nullable ReviewNode node) {
        super(GvSize.Reference.TYPE);
        mFactoryView = factoryView;
        mLauncher = launcher;
        mFactoryCommands = factoryCommands;
        mOptionsConfig = optionsConfig;
        mStamp = stamp;
        mRepo = repo;
        mNode = node;
    }

    @Override
    public MenuAction<GvSize.Reference> newMenu() {
        String title = Strings.Screens.SUMMARY;
        MenuAction<GvSize.Reference> menu = new MenuActionNone<>(title);
        if (mStamp.isValid()) {
            LaunchOptionsCommand command = mFactoryCommands.newLaunchOptionsCommand(mOptionsConfig);
            MenuActionItem<GvSize.Reference> options
                    = new MaiReviewOptions<>(command, mStamp.getDataAuthorId());
            menu = new MenuReviewOptions<>(title, options);
        }

        return menu;
    }


    @Override
    public BannerButtonAction<GvSize.Reference> newBannerButton() {
        return mStamp.isValid() ? new BannerButtonLaunchAuthorReviews<GvSize.Reference>(mLauncher.getReviewLauncher(),
                mStamp, mRepo) : new BannerButtonActionNone<GvSize.Reference>(Strings.Buttons.SUMMARY);
    }

    @Override
    public RatingBarAction<GvSize.Reference> newRatingBar() {
        if(mNode == null) return super.newRatingBar();

        LaunchFormattedCommand command
                = mFactoryCommands.newLaunchFormattedCommand(mLauncher.getReviewLauncher(), mNode);
        return new RatingBarExecuteCommand<>(command, Strings.LOADING);
    }

    @Override
    public GridItemAction<GvSize.Reference> newGridItem() {
        return new GridItemLauncher<>(mLauncher, mFactoryView);
    }
}
