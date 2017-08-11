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
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataComparatorsPlugin.Api.DataComparatorsApi;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.AuthorReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsSource;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.ButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.GridItemAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.RatingBarAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.ButtonSorter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.ButtonViewer;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.GridItemLaunchNodeView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MaiBookmarks;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MaiCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MaiFollow;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MaiLogout;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MaiProfile;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MaiSearch;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MenuFeed;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MenuFollow;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.NamedReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Factories.FactoryCommands;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.LaunchBespokeViewCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.OptionsSelector;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.ReviewOptionsSelector;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvNode;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.UiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 27/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryActionsReviewsList extends FactoryActionsNone<GvNode> {
    private final ReviewNode mNode;
    private final UiLauncher mLauncher;
    private final FactoryReviewView mFactoryReviewView;
    private final ReviewView<?> mDistribution;
    private final FactoryCommands mFactoryCommands;
    private final DataComparatorsApi mComparators;

    private AuthorReference mAuthorRef;

    public FactoryActionsReviewsList(ReviewNode node,
                                     UiLauncher launcher,
                                     FactoryReviewView factoryReviewView,
                                     ReviewView<?> distribution,
                                     FactoryCommands factoryCommands,
                                     DataComparatorsApi comparators,
                                     @Nullable AuthorReference authorRef) {
        super(GvNode.TYPE);
        mNode = node;
        mLauncher = launcher;
        mFactoryReviewView = factoryReviewView;
        mDistribution = distribution;
        mFactoryCommands = factoryCommands;
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
        return super.newRatingBar();
        //return new RatingBarExpandGrid<>(mLauncher, mFactoryReviewView);
    }

    @Override
    public ButtonAction<GvNode> newBannerButton() {
        return new ButtonSorter<>(newSelector(), mComparators.newReviewComparators());
    }

    private OptionsSelector newSelector() {
        return mFactoryCommands.newOptionsSelector();
    }

    @Nullable
    @Override
    public ButtonAction<GvNode> newContextButton() {
        ButtonViewer<GvNode> button = new ButtonViewer<>(Strings.Buttons.LIST, newSelector());
        button.addView(new NamedReviewView<>(Strings.Buttons.DISTRIBUTION, mDistribution));
        if(mNode != null) {
            button.addOption(mFactoryCommands.newLaunchFormattedCommand(mNode));
        }

        return button;
    }

    @Override
    public GridItemAction<GvNode> newGridItem() {
        LaunchBespokeViewCommand click = mFactoryCommands.newLaunchFormattedCommand(null);
        ReviewOptionsSelector longClick = mFactoryCommands.newReviewOptionsSelector();
        return new GridItemLaunchNodeView(click, longClick);
    }

    public static class Feed extends FactoryActionsReviewsList {
        private final ReviewsSource mRepo;
        private final LaunchableConfig mProfileEditor;

        public Feed(ReviewNode node,
                    UiLauncher launcher,
                    FactoryReviewView factoryReviewView,
                    ReviewView<?> distribution,
                    FactoryCommands factoryCommands,
                    DataComparatorsApi comparators,
                    ReviewsSource repo,
                    LaunchableConfig profileEditor) {
            super(node, launcher, factoryReviewView, distribution, factoryCommands, comparators, null);
            mRepo = repo;
            mProfileEditor = profileEditor;
        }

        @Override
        public MenuAction<GvNode> newMenu() {
            UiLauncher launcher = getUiLauncher();
            MaiCommand<GvNode> newReview = new MaiCommand<>
                    (getFactoryCommands().newLaunchCreatorCommand(null));
            MaiBookmarks<GvNode> bookmarks = new MaiBookmarks<>(launcher, mRepo, getFactoryReviewView());
            MaiSearch<GvNode> search = new MaiSearch<>(launcher, getFactoryReviewView());
            MaiProfile<GvNode> profile = new MaiProfile<>(mProfileEditor);
            MaiLogout<GvNode> logout = new MaiLogout<>();

            return new MenuFeed<>(newReview, bookmarks, search, profile, logout);
        }
    }
}
