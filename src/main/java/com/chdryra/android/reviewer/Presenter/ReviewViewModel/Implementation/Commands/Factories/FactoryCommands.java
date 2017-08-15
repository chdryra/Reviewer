/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Factories;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.Application.Interfaces.ApplicationSuite;
import com.chdryra.android.reviewer.Application.Interfaces.CurrentScreen;
import com.chdryra.android.reviewer.Application.Interfaces.RepositorySuite;
import com.chdryra.android.reviewer.Application.Interfaces.UserSession;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.BookmarkCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.Command;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.CommandList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.CreateAggregateView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.CreateDistributionView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.CreateListView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.DecoratedCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.DeleteCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.LaunchBespokeExpandedCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.LaunchBespokeViewCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.LaunchViewCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.OptionsSelector;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.ReviewOptionsSelector;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.ShareCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvNode;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.ReviewLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.UiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 27/09/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class FactoryCommands {
    private ApplicationSuite mApp;

    public interface ReviewOptionsReadyCallback {
        void onReviewOptionsReady(CommandList options);
    }

    public void setApp(ApplicationSuite app) {
        mApp = app;
    }

    public void getReviewOptions(DataAuthorId authorId, UserSession session, boolean allOptions,
                                 final
    ReviewOptionsReadyCallback callback) {
        if (isOffline()) {
            callback.onReviewOptionsReady(getOfflineOptions());
            return;
        }

        final CommandList commands = getBasicReviewOptions(authorId, session);
        if (allOptions) {
            ReviewId reviewId = authorId.getReviewId();
            commands.add(share(reviewId));
            BookmarkCommand bookmark = bookmark(session, reviewId);
            commands.add(bookmark);

            bookmark.initialise(new BookmarkCommand.BookmarkCommandReadyCallback() {
                @Override
                public void onBookmarkCommandReady() {
                    callback.onReviewOptionsReady(commands);
                }
            });
        } else {
            callback.onReviewOptionsReady(commands);
        }
    }

    public ReviewOptionsSelector newReviewOptionsSelector(ReviewOptionsSelector.OptionsType
                                                                  optionsType) {
        return new ReviewOptionsSelector(newOptionsSelector(), this, getSession(), optionsType);
    }

    public ReviewOptionsSelector newReviewOptionsSelector(ReviewOptionsSelector.OptionsType
                                                                  optionsType, DataAuthorId
            authorId) {
        return new ReviewOptionsSelector(newOptionsSelector(), this, getSession(), optionsType,
                authorId);
    }

    public OptionsSelector newOptionsSelector() {
        return new OptionsSelector(mApp.getUi().getConfig().getOptions());
    }

    public Command newLaunchViewCommand(final ReviewView<?> view) {
        return newLaunchViewCommand(view, "");
    }

    public Command newLaunchViewCommand(final ReviewView<?> view, String name) {
        return new LaunchViewCommand(name, view, getLauncher());
    }

    public Command newLaunchListCommand(ReviewViewAdapter<?> unexpanded, FactoryReviewView
            viewFactory) {
        return newLaunchViewCommand(Strings.Commands.LIST, new CreateListView(unexpanded,
                viewFactory));
    }

    public Command newLaunchAggregateCommand(ReviewViewAdapter<?> unexpanded, FactoryReviewView
            viewFactory) {
        return newLaunchViewCommand(Strings.Commands.AGGREGATE, new CreateAggregateView
                (unexpanded, viewFactory));
    }

    public Command newLaunchDistributionCommand(ReviewViewAdapter<?> unexpanded,
                                                FactoryReviewView viewFactory) {
        return newLaunchViewCommand(Strings.Commands.DISTRIBUTION, new CreateDistributionView
                (unexpanded, viewFactory));
    }

    public LaunchBespokeViewCommand newLaunchPagedCommand(@Nullable ReviewNode node) {
        return newLaunchBespokeViewCommand(node, Strings.Commands.PAGED, GvNode.TYPE);
    }

    public LaunchBespokeViewCommand newLaunchMappedCommand(@Nullable ReviewNode node) {
        return newLaunchBespokeViewCommand(node, Strings.Commands.MAPPED, GvLocation.TYPE);
    }

    public LaunchBespokeViewCommand newLaunchPagedExpandedCommand(ReviewViewAdapter<?> unexpanded) {
        return newLaunchBespokeExpandedCommand(Strings.Commands.PAGED, unexpanded, GvNode.TYPE);
    }

    public LaunchBespokeViewCommand newLaunchMappedExpandedCommand(ReviewViewAdapter<?> unexpanded) {
        return newLaunchBespokeExpandedCommand(Strings.Commands.MAPPED, unexpanded, GvLocation.Reference.TYPE);
    }

    private LaunchBespokeViewCommand newLaunchBespokeExpandedCommand(String name, ReviewViewAdapter<?> unexpanded, GvDataType<?> dataType) {
        return new LaunchBespokeExpandedCommand(name, getReviewLauncher(), unexpanded, dataType);
    }

    public LaunchBespokeViewCommand newLaunchBespokeViewCommand(@Nullable ReviewNode node, String
            commandName, GvDataType<?> dataType) {
        return new LaunchBespokeViewCommand(commandName, getReviewLauncher(), node, dataType);
    }

    public Command newLaunchSummaryCommand(final ReviewId id) {
        return new Command() {
            @Override
            public void execute() {
                getReviewLauncher().launchSummary(id);
            }
        };
    }

    public Command newLaunchAuthorCommand(final AuthorId id) {
        return new Command() {
            @Override
            public void execute() {
                getReviewLauncher().launchReviewsList(id);
            }
        };
    }

    public Command newLaunchCreatorCommand(@Nullable final ReviewId template) {
        return new Command() {
            @Override
            public void execute() {
                getLauncher().launchCreateUi(template);
                onExecutionComplete();
            }
        };
    }

    public Command newLaunchEditorCommand(final ReviewId toEdit) {
        return new Command() {
            @Override
            public void execute() {
                getLauncher().launchEditUi(toEdit);
                onExecutionComplete();
            }
        };
    }

    private CommandList getOfflineOptions() {
        CommandList commands = new CommandList();
        commands.add(Command.NoAction(Strings.Commands.OFFLINE));
        return commands;
    }

    private boolean isOffline() {
        return !mApp.getNetwork().isOnline(mApp.getUi().getCurrentScreen());
    }

    private ReviewLauncher getReviewLauncher() {
        return getLauncher().getReviewLauncher();
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

    private LaunchViewCommand newLaunchViewCommand(String name, LaunchViewCommand.ViewCreator
            creator) {
        return new LaunchViewCommand(name, creator, getLauncher());
    }

    @NonNull
    private CommandList getBasicReviewOptions(DataAuthorId authorId, UserSession session) {
        final CommandList commands = new CommandList();
        ReviewId reviewId = authorId.getReviewId();
        commands.add(template(reviewId));
        if (isReviewAuthor(authorId, session)) {
            commands.add(edit(reviewId));
            commands.add(delete(reviewId));
        }
        return commands;
    }

    private boolean isReviewAuthor(DataAuthorId authorId, UserSession session) {
        return authorId.toString().equals(session.getAuthorId().toString());
    }

    @NonNull
    private DeleteCommand delete(ReviewId reviewId) {
        return new DeleteCommand(getRepo().newReviewDeleter(reviewId), getScreen());
    }

    @NonNull
    private BookmarkCommand bookmark(UserSession session, ReviewId reviewId) {
        return new BookmarkCommand(reviewId, getRepo().getReviewsRepository()
                .getMutableRepository(session).getBookmarks(), getScreen());
    }

    @NonNull
    private Command template(ReviewId reviewId) {
        return new DecoratedCommand(Strings.Commands.TEMPLATE, Strings.Toasts.COPYING,
                newLaunchCreatorCommand(reviewId), getScreen());
    }

    private Command edit(ReviewId reviewId) {
        return new DecoratedCommand(Strings.Commands.EDIT, Strings.Toasts.LAUNCHING_EDITOR,
                newLaunchEditorCommand(reviewId), getScreen());
    }

    @NonNull
    private ShareCommand share(ReviewId reviewId) {
        return new ShareCommand(reviewId, getRepo(), getScreen(), mApp.getSocial().newPublisher());
    }
}
