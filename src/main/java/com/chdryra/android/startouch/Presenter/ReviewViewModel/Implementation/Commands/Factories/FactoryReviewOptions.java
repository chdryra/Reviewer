/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands.Factories;

import android.support.annotation.NonNull;

import com.chdryra.android.startouch.Application.Implementation.Strings;
import com.chdryra.android.startouch.Application.Interfaces.ApplicationSuite;
import com.chdryra.android.startouch.Application.Interfaces.CurrentScreen;
import com.chdryra.android.startouch.Application.Interfaces.RepositorySuite;
import com.chdryra.android.startouch.Authentication.Interfaces.UserSession;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataAuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.BookmarkCommand;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.Command;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.CommandList;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.DecoratedCommand;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.DeleteCommand;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.OptionsSelector;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.ReviewOptions;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.ReviewOptionsSelector;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.ShareCommand;
import com.chdryra.android.startouch.View.LauncherModel.Interfaces.UiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 10/02/2018
 * Email: rizwan.choudrey@gmail.com
 */

public class FactoryReviewOptions {
    private final FactoryLaunchCommands mLaunchCommands;
    private ApplicationSuite mApp;

    FactoryReviewOptions(FactoryLaunchCommands launchCommands) {
        mLaunchCommands = launchCommands;
    }

    public void setApp(ApplicationSuite app) {
        mApp = app;
    }


    public ReviewOptions newReviewOptions(DataAuthorId authorId, UserSession session) {
        if (isOffline()) return new ReviewOptions(getOfflineOptions());

        CommandList commands = new CommandList();
        ReviewId reviewId = authorId.getReviewId();
        commands.add(template(reviewId));
        if (isReviewAuthor(authorId, session)) {
            commands.add(edit(reviewId));
            commands.add(delete(reviewId));
        }

        return new ReviewOptions(commands, share(reviewId), bookmark(session.getAuthorId(), reviewId));
    }

    public ReviewOptionsSelector newReviewOptionsSelector(ReviewOptionsSelector.SelectorType
                                                                  selectorType) {
        return new ReviewOptionsSelector(newOptionsSelector(), mLaunchCommands, getSession(), selectorType);
    }

    public ReviewOptionsSelector newReviewOptionsSelector(ReviewOptionsSelector.SelectorType
                                                                  selectorType, DataAuthorId
                                                                  authorId) {
        return new ReviewOptionsSelector(newOptionsSelector(), mLaunchCommands, getSession(), selectorType,
                authorId);
    }

    public OptionsSelector newOptionsSelector() {
        return new OptionsSelector(mApp.getUi().getConfig().getOptions());
    }

    private CommandList getOfflineOptions() {
        CommandList commands = new CommandList();
        commands.add(Command.NoAction(Strings.Commands.OFFLINE));
        return commands;
    }

    private boolean isOffline() {
        return !mApp.getNetwork().isOnline(mApp.getUi().getCurrentScreen());
    }

    private UserSession getSession() {
        return mApp.getAccounts().getUserSession();
    }

    private Command newLaunchEditorCommand(final ReviewId toEdit) {
        return new Command() {
            @Override
            public void execute() {
                getLauncher().launchEditUi(toEdit);
                onExecutionComplete();
            }
        };
    }
    private boolean isReviewAuthor(DataAuthorId authorId, UserSession session) {
        return authorId.toString().equals(session.getAuthorId().toString());
    }

    @NonNull
    private DeleteCommand delete(ReviewId reviewId) {
        return new DeleteCommand(getRepo().newReviewDeleter(reviewId), getScreen());
    }

    @NonNull
    private BookmarkCommand bookmark(AuthorId authorId, ReviewId reviewId) {
        return new BookmarkCommand(reviewId,
                getRepo().getReviews().getCollectionForAuthor(authorId, Strings.Playlists.BOOKMARKS),
                getScreen());
    }

    @NonNull
    private Command template(ReviewId reviewId) {
        return new DecoratedCommand(Strings.Commands.TEMPLATE, Strings.Toasts.COPYING,
                mLaunchCommands.newLaunchCreatorCommand(reviewId), getScreen());
    }

    private Command edit(ReviewId reviewId) {
        return new DecoratedCommand(Strings.Commands.EDIT, Strings.Toasts.LAUNCHING_EDITOR,
                newLaunchEditorCommand(reviewId), getScreen());
    }

    @NonNull
    private ShareCommand share(ReviewId reviewId) {
        return new ShareCommand(reviewId, getRepo().getReviews(), getScreen(), mApp.getSocial().newPublisher());
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
