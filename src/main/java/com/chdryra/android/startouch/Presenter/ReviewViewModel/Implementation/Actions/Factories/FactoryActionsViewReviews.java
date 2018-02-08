/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Factories;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.AuthorReference;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsNodeRepo;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.GridItemAction;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions
        .Implementation.ActionsParameters;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Implementation.GridItemLaunchNodeView;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MaiBookmarks;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MaiCommand;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MaiFollow;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MaiLogout;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MaiProfile;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MaiSearch;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MenuFeed;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MenuFollow;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands.Implementation.LaunchBespokeViewCommand;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands.Implementation.ReviewOptionsSelector;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvNode;
import com.chdryra.android.startouch.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.startouch.View.LauncherModel.Interfaces.UiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 27/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryActionsViewReviews extends FactoryActionsViewData<GvNode> {
    private AuthorReference mAuthorRef;
    private ReviewsNodeRepo mRepo;
    private LaunchableConfig mProfileEditor;

    public FactoryActionsViewReviews(ActionsParameters<GvNode> parameters,
                                     @Nullable AuthorReference authorRef) {
        super(parameters);
        mAuthorRef = authorRef;
    }

    public FactoryActionsViewReviews(ActionsParameters<GvNode> parameters,
                                     ReviewsNodeRepo repo,
                                     LaunchableConfig profileEditor) {
        this(parameters, null);
        mRepo = repo;
        mProfileEditor = profileEditor;
    }

    @Override
    public MenuAction<GvNode> newMenu() {
        return mRepo != null ? newFeedMenu(mRepo, mProfileEditor) : newDefaultMenu();
    }

    @Override
    public GridItemAction<GvNode> newGridItem() {
        LaunchBespokeViewCommand click = getCommandsFactory().newLaunchPagedCommand(null);
        ReviewOptionsSelector longClick = getCommandsFactory().newReviewOptionsSelector(ReviewOptionsSelector.SelectorType.BASIC);
        return new GridItemLaunchNodeView(click, longClick);
    }
    
    @NonNull
    private MenuAction<GvNode> newFeedMenu(ReviewsNodeRepo repo, LaunchableConfig profileEditor) {
        UiLauncher launcher = getLauncher();
        MaiCommand<GvNode> newReview = new MaiCommand<>
                (getCommandsFactory().newLaunchCreatorCommand(null));
        MaiBookmarks<GvNode> bookmarks = new MaiBookmarks<>(launcher, repo, getViewFactory());
        MaiSearch<GvNode> search = new MaiSearch<>(launcher, getViewFactory());
        MaiProfile<GvNode> profile = new MaiProfile<>(profileEditor);
        MaiLogout<GvNode> logout = new MaiLogout<>();

        return new MenuFeed<>(newReview, bookmarks, search, profile, logout);
    }

    private MenuAction<GvNode> newDefaultMenu() {
        return mAuthorRef != null ?
                new MenuFollow<>(new MaiFollow<GvNode>(mAuthorRef.getAuthorId()), mAuthorRef) :
                super.newDefaultMenu(getDataType().getDataName());
    }
}