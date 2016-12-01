/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Factories;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataComparatorsPlugin.Api.DataComparatorsApi;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.AuthorReference;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.BannerButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.GridItemAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.RatingBarAction;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.BannerButtonSorter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.GridItemLaunchFormatted;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MaiCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MaiFollow;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MaiSearch;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MaiLogout;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MenuFeed;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MenuFollow;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.RatingBarExpandGrid;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Factories.FactoryCommands;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.LaunchFormattedCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.LaunchOptionsCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvNode;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.UiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 27/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryActionsReviewsList extends FactoryActionsNone<GvNode> {
    private final UiLauncher mLauncher;
    private final FactoryReviewView mFactoryReviewView;
    private final FactoryCommands mFactoryCommands;
    private final LaunchableConfig mOptionsConfig;
    private final DataComparatorsApi mComparators;

    private AuthorReference mAuthorRef;

    public FactoryActionsReviewsList(UiLauncher launcher,
                                     FactoryReviewView factoryReviewView,
                                     FactoryCommands factoryCommands,
                                     LaunchableConfig optionsConfig,
                                     DataComparatorsApi comparators,
                                     @Nullable AuthorReference authorRef) {
        super(GvNode.TYPE);
        mLauncher = launcher;
        mFactoryReviewView = factoryReviewView;
        mFactoryCommands = factoryCommands;
        mOptionsConfig = optionsConfig;
        mComparators = comparators;
        mAuthorRef = authorRef;
    }

    FactoryReviewView getFactoryReviewView() {
        return mFactoryReviewView;
    }

    UiLauncher getUiLauncher() {
        return mLauncher;
    }

    FactoryCommands getFactoryCommands() {
        return mFactoryCommands;
    }

    @Override
    public MenuAction<GvNode> newMenu() {
        return mAuthorRef != null ?
                new MenuFollow<>(new MaiFollow<GvNode>(mAuthorRef.getAuthorId()), mAuthorRef) :
                super.newDefaultMenu(getDataType().getDataName());
    }

    @Override
    public RatingBarAction<GvNode> newRatingBar() {
        return new RatingBarExpandGrid<>(mLauncher, mFactoryReviewView);
    }

    @Override
    public BannerButtonAction<GvNode> newBannerButton() {
        return new BannerButtonSorter<>(mComparators.newReviewComparators());
    }

    @Override
    public GridItemAction<GvNode> newGridItem() {
        LaunchFormattedCommand click = mFactoryCommands.newLaunchFormattedCommand(mLauncher.getReviewLauncher());
        LaunchOptionsCommand longClick = mFactoryCommands.newLaunchOptionsCommand(mOptionsConfig);
        return new GridItemLaunchFormatted(click, longClick);
    }

    public static class Feed extends FactoryActionsReviewsList {
        public Feed(UiLauncher launcher, FactoryReviewView factoryReviewView,
                    FactoryCommands factoryCommands, LaunchableConfig optionsUi,
                    DataComparatorsApi comparators) {
            super(launcher, factoryReviewView, factoryCommands, optionsUi, comparators, null);
        }

        @Override
        public MenuAction<GvNode> newMenu() {
            UiLauncher launcher = getUiLauncher();
            MaiCommand<GvNode> newReview = new MaiCommand<>
                    (getFactoryCommands().newLaunchEditorCommand(launcher, null));
            MaiSearch<GvNode> search = new MaiSearch<>(launcher, getFactoryReviewView());
            MaiLogout<GvNode> logout = new MaiLogout<>();

            return new MenuFeed<>(newReview, search, logout);
        }
    }
}
