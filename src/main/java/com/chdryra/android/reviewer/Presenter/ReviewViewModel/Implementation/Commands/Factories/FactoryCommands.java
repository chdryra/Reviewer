/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Factories;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.Application.Interfaces.CurrentScreen;
import com.chdryra.android.reviewer.Application.Interfaces.RepositorySuite;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.NetworkServices.ReviewDeleting.Interfaces.ReviewDeleter;
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
import com.chdryra.android.reviewer.Social.Interfaces.SocialPublisher;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.ReviewLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.UiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 27/09/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class FactoryCommands {
    public Command newCopyCommand(UiLauncher launcher, @Nullable ReviewId template,
                                  CurrentScreen screen) {
        return new CopyCommand(newLaunchEditorCommand(launcher, template), screen);
    }

    public Command newShareCommand(ReviewId reviewId, RepositorySuite repo, CurrentScreen screen,
                                   SocialPublisher publisher) {
        return new ShareCommand(reviewId, repo, screen, publisher);
    }

    public Command newDeleteCommand(ReviewDeleter deleter, CurrentScreen screen) {
        return new DeleteCommand(deleter, screen);
    }

    public LaunchReviewOptionsCommand newLaunchReviewOptionsCommand(LaunchableConfig optionsUi) {
        return new LaunchReviewOptionsCommand(optionsUi);
    }

    public LaunchReviewOptionsCommand newLaunchReviewOptionsCommand(LaunchableConfig optionsUi, DataAuthorId authorId) {
        return new LaunchReviewOptionsCommand(optionsUi, authorId);
    }

    public LaunchOptionsCommand newLaunchOptionsCommand(LaunchableConfig optionsUi) {
        return new LaunchOptionsCommand(optionsUi);
    }

    public LaunchViewCommand newLaunchViewCommand(UiLauncher launcher, ReviewView<?> view) {
        return new LaunchViewCommand(launcher, view);
    }

    public LaunchFormattedCommand newLaunchFormattedCommand(ReviewLauncher launcher) {
        return new LaunchFormattedCommand(launcher, null);
    }

    public LaunchFormattedCommand newLaunchFormattedCommand(ReviewLauncher launcher, ReviewNode node) {
        return new LaunchFormattedCommand(launcher, node);
    }

    public LaunchMappedCommand newLaunchMappedCommand(ReviewLauncher launcher) {
        return new LaunchMappedCommand(launcher, null);
    }

    public LaunchMappedCommand newLaunchMappedCommand(ReviewLauncher launcher, ReviewNode node) {
        return new LaunchMappedCommand(launcher, node);
    }

    public LaunchEditorCommand newLaunchEditorCommand(UiLauncher launcher, @Nullable ReviewId template) {
        return new LaunchEditorCommand(launcher, template);
    }
}
