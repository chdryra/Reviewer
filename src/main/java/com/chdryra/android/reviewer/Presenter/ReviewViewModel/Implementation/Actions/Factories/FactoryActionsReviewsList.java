/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Factories;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.GridItemAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.RatingBarAction;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.GridItemFeed;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.GridItemReviewsList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MaiFollow;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MaiNewReview;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MaiSearchAuthors;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MaiSettings;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MenuFeed;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MenuFollow;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.RatingBarExpandGrid;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.LaunchOptionsCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvNode;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.UiLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 27/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryActionsReviewsList extends FactoryActionsNone<GvNode> {
    private UiLauncher mLauncher;
    private FactoryReviewView mFactoryReviewView;
    private LaunchableConfig mOptionsUi;
    private AuthorId mAuthorId;

    public FactoryActionsReviewsList(UiLauncher launcher,
                                     FactoryReviewView factoryReviewView,
                                     LaunchableConfig optionsUi,
                                     @Nullable AuthorId followAuthorId) {
        super(GvNode.TYPE);
        mLauncher = launcher;
        mFactoryReviewView = factoryReviewView;
        mOptionsUi = optionsUi;
        mAuthorId = followAuthorId;
    }

    FactoryReviewView getFactoryReviewView() {
        return mFactoryReviewView;
    }

    UiLauncher getUiLauncher() {
        return mLauncher;
    }

    @Override
    public MenuAction<GvNode> newMenu() {
        return mAuthorId != null ?
                new MenuFollow<>(new MaiFollow<GvNode>(mAuthorId)) : super.newMenu();
    }

    @Override
    public RatingBarAction<GvNode> newRatingBar() {
        return new RatingBarExpandGrid<>(mFactoryReviewView);
    }

    @Override
    public GridItemAction<GvNode> newGridItem() {
        return new GridItemReviewsList(mLauncher, mFactoryReviewView, newOptionsCommand());
    }

    LaunchOptionsCommand newOptionsCommand() {
        return newOptionsCommand(mOptionsUi);
    }

    public static class Feed extends FactoryActionsReviewsList {
        public Feed(UiLauncher launcher, FactoryReviewView factoryReviewView, LaunchableConfig optionsUi) {
            super(launcher, factoryReviewView, optionsUi, null);
        }

        @Override
        public GridItemAction<GvNode> newGridItem() {
            return new GridItemFeed(getUiLauncher(), getFactoryReviewView(), newOptionsCommand());
        }

        @Override
        public MenuAction<GvNode> newMenu() {
            return new MenuFeed<>(new MaiNewReview<GvNode>(getUiLauncher()),
                    new MaiSearchAuthors<GvNode>(getUiLauncher(), getFactoryReviewView()),
                    new MaiSettings<GvNode>());
        }
    }
}