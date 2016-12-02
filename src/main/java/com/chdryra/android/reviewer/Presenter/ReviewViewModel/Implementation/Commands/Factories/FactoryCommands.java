/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Factories;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.Application.Interfaces.ApplicationSuite;
import com.chdryra.android.reviewer.Application.Interfaces.CurrentScreen;
import com.chdryra.android.reviewer.Application.Interfaces.RepositorySuite;
import com.chdryra.android.reviewer.Application.Interfaces.UserSession;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.Command;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.CopyCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.DeleteCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.LaunchEditorCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.LaunchFormattedCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.LaunchMappedCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.LaunchOptionsCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.LaunchReviewOptionsCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.LaunchViewCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.ShareCommand;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.ReviewLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.UiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 27/09/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class FactoryCommands {
    private ApplicationSuite mApp;

    public void setApp(ApplicationSuite app) {
        mApp = app;
    }

    public Command newCopyCommand(@Nullable ReviewId template) {
        return new CopyCommand(newLaunchEditorCommand(getLauncher(), template), getScreen());
    }

    public Command newShareCommand(ReviewId reviewId) {
        return new ShareCommand(reviewId, getRepo(), getScreen(), mApp.getSocial().newPublisher());
    }

    public Command newDeleteCommand(ReviewId reviewId) {
        return new DeleteCommand(getRepo().newReviewDeleter(reviewId), getScreen());
    }

    public LaunchReviewOptionsCommand newLaunchReviewOptionsCommand() {
        return new LaunchReviewOptionsCommand(newLaunchOptionsCommand(), this, getSession());
    }

    public LaunchReviewOptionsCommand newLaunchReviewOptionsCommand(DataAuthorId authorId) {
        return new LaunchReviewOptionsCommand(newLaunchOptionsCommand(), this, getSession(), authorId);
    }

    public LaunchOptionsCommand newLaunchOptionsCommand() {
        return new LaunchOptionsCommand(mApp.getUi().getConfig().getOptions());
    }

    public LaunchViewCommand newLaunchViewCommand(UiLauncher launcher, ReviewView<?> view) {
        return new LaunchViewCommand(launcher, view);
    }

    public LaunchFormattedCommand newLaunchFormattedCommand(ReviewLauncher launcher) {
        return new LaunchFormattedCommand(launcher, null);
    }

    public LaunchFormattedCommand newLaunchFormattedCommand(ReviewLauncher launcher, ReviewNode
            node) {
        return new LaunchFormattedCommand(launcher, node);
    }

    public LaunchMappedCommand newLaunchMappedCommand(ReviewLauncher launcher) {
        return new LaunchMappedCommand(launcher, null);
    }

    public LaunchMappedCommand newLaunchMappedCommand(ReviewLauncher launcher, ReviewNode node) {
        return new LaunchMappedCommand(launcher, node);
    }

    public LaunchEditorCommand newLaunchEditorCommand(UiLauncher launcher, @Nullable ReviewId
            template) {
        return new LaunchEditorCommand(launcher, template);
    }

    private UserSession getSession() {
        return mApp.getAuthentication().getUserSession();
    }

    private RepositorySuite getRepo() {
        return mApp.getRepository();
    }

    private CurrentScreen getScreen() {
        return mApp.getUi().getCurrentScreen();
    }

    private UiLauncher getLauncher() {
        return mApp.getUi().getLauncher();
    }
}
