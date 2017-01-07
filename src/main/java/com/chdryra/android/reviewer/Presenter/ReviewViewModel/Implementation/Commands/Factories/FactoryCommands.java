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

import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.Application.Interfaces.ApplicationSuite;
import com.chdryra.android.reviewer.Application.Interfaces.CurrentScreen;
import com.chdryra.android.reviewer.Application.Interfaces.RepositorySuite;
import com.chdryra.android.reviewer.Application.Interfaces.UserSession;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Fragments.FragmentFormatReview;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.BookmarkCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.Command;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.CommandsList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.DecoratedCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.DeleteCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.LaunchBespokeViewCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.OptionsSelector;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.ReviewOptionsSelector;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.ShareCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvNode;
import com.chdryra.android.reviewer.View.LauncherModel.Implementation.UiLauncherArgs;
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

    public interface ReviewOptionsReadyCallback {
        void onReviewOptionsReady(CommandsList options);
    }

    public void getReviewOptions(DataAuthorId authorId, UserSession session, final ReviewOptionsReadyCallback callback) {
        ReviewId reviewId = authorId.getReviewId();
        boolean isOwner = authorId.toString().equals(session.getAuthorId().toString());

        final CommandsList commands = new CommandsList();
        if(!mApp.getNetwork().isOnline()) {
            mApp.getUi().getCurrentScreen().showToast(Strings.Toasts.NO_INTERNET);
            commands.add(new Command(Strings.Commands.OFFLINE));
            callback.onReviewOptionsReady(commands);
            return;
        }

        commands.add(share(reviewId));
        commands.add(template(reviewId));
        BookmarkCommand bookmark = bookmark(session, reviewId);
        commands.add(bookmark);
        if (isOwner) {
            commands.add(edit(reviewId));
            commands.add(delete(reviewId));
        }

        bookmark.initialise(new BookmarkCommand.BookmarkCommandReadyCallback() {
            @Override
            public void onBookmarkCommandReady() {
                callback.onReviewOptionsReady(commands);
            }
        });
    }

    public ReviewOptionsSelector newReviewOptionsSelector() {
        return new ReviewOptionsSelector(newOptionsSelector(), this, getSession());
    }

    public ReviewOptionsSelector newReviewOptionsSelector(DataAuthorId authorId) {
        return new ReviewOptionsSelector(newOptionsSelector(), this, getSession(), authorId);
    }

    public OptionsSelector newOptionsSelector() {
        return new OptionsSelector(mApp.getUi().getConfig().getOptions());
    }

    public Command newLaunchViewCommand(final ReviewView<?> view) {
        return newLaunchViewCommand(view, "");
    }

    public Command newLaunchViewCommand(final ReviewView<?> view, String name) {
        return new Command(name) {
            @Override
            public void execute() {
                int code = RequestCodeGenerator.getCode(FragmentFormatReview.class, view
                        .getLaunchTag());
                getLauncher().launch(view, new UiLauncherArgs(code));
            }
        };
    }

    public LaunchBespokeViewCommand newLaunchFormattedCommand(@Nullable ReviewNode node) {
        return newLaunchBespokeViewCommand(node, Strings.Commands.FORMATTED, GvNode.TYPE);
    }

    public LaunchBespokeViewCommand newLaunchBespokeViewCommand(@Nullable ReviewNode node, String commandName, GvDataType<?> dataType) {
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

    public LaunchBespokeViewCommand newLaunchMappedCommand(@Nullable ReviewNode node) {
        return newLaunchBespokeViewCommand(node, Strings.Commands.MAPPED, GvLocation.TYPE);
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
