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
import com.chdryra.android.reviewer.Application.Interfaces.ApplicationSuite;
import com.chdryra.android.reviewer.Application.Interfaces.CurrentScreen;
import com.chdryra.android.reviewer.Application.Interfaces.RepositorySuite;
import com.chdryra.android.reviewer.Application.Interfaces.UserSession;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Fragments.FragmentFormatReview;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.BookmarkCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.Command;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.CommandsList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.CopyCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.DeleteCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.LaunchFormattedCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.LaunchMappedCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.OptionsSelector;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.ReviewOptionsSelector;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.ShareCommand;
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
        boolean hasDelete = authorId.toString().equals(session.getAuthorId().toString());

        final CommandsList commands = new CommandsList();
        commands.add(share(reviewId));
        commands.add(copy(reviewId));
        BookmarkCommand bookmark = bookmark(session, reviewId);
        commands.add(bookmark);
        if (hasDelete) commands.add(delete(reviewId));

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

    public LaunchFormattedCommand newLaunchFormattedCommand() {
        return new LaunchFormattedCommand(getReviewLauncher(), null);
    }

    public LaunchFormattedCommand newLaunchFormattedCommand(ReviewNode node) {
        return new LaunchFormattedCommand(getReviewLauncher(), node);
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

    public LaunchMappedCommand newLaunchMappedCommand() {
        return new LaunchMappedCommand(getReviewLauncher(), null);
    }

    public LaunchMappedCommand newLaunchMappedCommand(ReviewNode node) {
        return new LaunchMappedCommand(getReviewLauncher(), node);
    }

    public Command newLaunchEditorCommand(@Nullable final ReviewId template) {
        return new Command() {
            @Override
            public void execute() {
                getLauncher().launchEditUi(template);
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
    private CopyCommand copy(ReviewId reviewId) {
        return new CopyCommand(newLaunchEditorCommand(reviewId), getScreen());
    }

    @NonNull
    private ShareCommand share(ReviewId reviewId) {
        return new ShareCommand(reviewId, getRepo(), getScreen(), mApp.getSocial().newPublisher());
    }
}
