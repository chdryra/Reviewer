/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands.Factories;

import android.support.annotation.Nullable;

import com.chdryra.android.startouch.Application.Implementation.Strings;
import com.chdryra.android.startouch.Application.Interfaces.ApplicationSuite;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.Command;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.CreateAggregateView;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.CreateBucketsView;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.CreateListView;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.LaunchBespokeExpandedCommand;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.LaunchBespokeViewCommand;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.LaunchProfileCommand;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.LaunchViewCommand;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvDataType;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvLocation;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvNode;
import com.chdryra.android.startouch.View.LauncherModel.Interfaces.ReviewLauncher;
import com.chdryra.android.startouch.View.LauncherModel.Interfaces.UiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 27/09/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class FactoryLaunchCommands {
    private final FactoryReviewOptions mOptionsFactory;
    private ApplicationSuite mApp;

    public FactoryLaunchCommands() {
        mOptionsFactory = new FactoryReviewOptions(this);
    }

    public FactoryReviewOptions getOptionsFactory() {
        return mOptionsFactory;
    }

    public void setApp(ApplicationSuite app) {
        mApp = app;
        mOptionsFactory.setApp(app);
    }

    public Command newLaunchViewCommand(final ReviewView<?> view) {
        return newLaunchViewCommand(view, "");
    }

    public Command newLaunchListCommand(ReviewViewAdapter<?> unexpanded, FactoryReviewView
            viewFactory) {
        return newLaunchViewCommand(Strings.Commands.LIST, new CreateListView(unexpanded,
                viewFactory));
    }

    public Command newLaunchAggregateCommand(ReviewViewAdapter<?> unexpanded, FactoryReviewView
            viewFactory) {
        return newLaunchViewCommand(Strings.Commands.FILTER, new CreateAggregateView
                (unexpanded, viewFactory));
    }

    public Command newLaunchBucketsCommand(ReviewViewAdapter<?> unexpanded,
                                           FactoryReviewView viewFactory) {
        return newLaunchViewCommand(Strings.Commands.BUCKETS, new CreateBucketsView
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

    public LaunchBespokeViewCommand newLaunchMappedExpandedCommand(ReviewViewAdapter<?>
                                                                           unexpanded) {
        return newLaunchBespokeExpandedCommand(Strings.Commands.MAPPED, unexpanded, GvLocation
                .Reference.TYPE);
    }

    public LaunchBespokeViewCommand newLaunchBespokeViewCommand(@Nullable ReviewNode node, String
            commandName, GvDataType<?> dataType) {
        return new LaunchBespokeViewCommand(commandName, getReviewLauncher(), node, dataType);
    }

    public Command newLaunchAuthorCommand(final AuthorId id) {
        return new Command() {
            @Override
            public void execute() {
                getReviewLauncher().launchAsList(id);
                onExecutionComplete();
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

    public LaunchProfileCommand newLaunchProfileCommand() {
        return new LaunchProfileCommand(mApp.getUi().getConfig().getProfile(), null);
    }

    public LaunchProfileCommand newLaunchProfileCommand(AuthorId authorId) {
        return new LaunchProfileCommand(mApp.getUi().getConfig().getProfile(), authorId);
    }

    private ReviewLauncher getReviewLauncher() {
        return getLauncher().getReviewLauncher();
    }


    private UiLauncher getLauncher() {
        return mApp.getUi().getLauncher();
    }

    private Command newLaunchViewCommand(final ReviewView<?> view, String name) {
        return new LaunchViewCommand(name, view, getLauncher());
    }

    private LaunchBespokeViewCommand newLaunchBespokeExpandedCommand(String name,
                                                                     ReviewViewAdapter<?>
                                                                             unexpanded,
                                                                     GvDataType<?> dataType) {
        return new LaunchBespokeExpandedCommand(name, getReviewLauncher(), unexpanded, dataType);
    }


    private LaunchViewCommand newLaunchViewCommand(String name, LaunchViewCommand.ViewCreator
            creator) {
        return new LaunchViewCommand(name, creator, getLauncher());
    }

}
