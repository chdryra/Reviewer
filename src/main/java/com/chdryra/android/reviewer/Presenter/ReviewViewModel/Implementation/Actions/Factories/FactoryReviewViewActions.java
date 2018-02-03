/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Factories;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.Comparators.ComparatorCollection;
import com.chdryra.android.mygenerallibrary.Comparators.ComparatorCollectionImpl;
import com.chdryra.android.mygenerallibrary.Comparators.NamedComparator;
import com.chdryra.android.mygenerallibrary.LocationUtils.LocationClient;
import com.chdryra.android.mygenerallibrary.TextUtils.TextUtils;
import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataComparatorsPlugin.Api.DataComparatorsApi;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.AuthorReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepo;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsNodeRepo;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.PublishAction;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewEditor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.ActionsParameters;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Factories.FactoryCommands;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.Command;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.CommandList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.ConverterGv;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvAuthor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvBucket;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvNode;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSize;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSocialPlatform;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSocialPlatformList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.AdapterReviewNode;
import com.chdryra.android.reviewer.Social.Interfaces.PlatformAuthoriser;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.Configs.Interfaces.UiConfig;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.UiLauncher;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 14/08/2017
 * Email: rizwan.choudrey@gmail.com
 * <p>
 * This is one of the messiest classes in the whole project.
 * Highlights deficiencies in the ReviewView pattern.
 * Ripe for a refactor and design rethink.
 */

//TODO refactor and think about ReviewView redesign.
public class FactoryReviewViewActions {
    private final UiConfig mConfig;
    private final ReviewsNodeRepo mReviewsRepo;
    private final AuthorsRepo mAuthorsRepo;
    private final FactoryCommands mCommandsFactory;
    private final DataComparatorsApi mComparators;
    private final ConverterGv mConverter;

    public FactoryReviewViewActions(UiConfig config, ReviewsNodeRepo reviewsRepo, AuthorsRepo
            authorsRepo, FactoryCommands commandsFactory, DataComparatorsApi comparators,
                                    ConverterGv converter) {
        mConfig = config;
        mReviewsRepo = reviewsRepo;
        mAuthorsRepo = authorsRepo;
        mCommandsFactory = commandsFactory;
        mComparators = comparators;
        mConverter = converter;
    }

    public <GC extends GvDataList<? extends GvDataParcelable>>
    FactoryActionsBuildReview<GC> newCreatorActions(GvDataType<GC> dataType,
                                                    ReviewEditor.EditMode editMode,
                                                    LocationClient locationClient) {
        return new FactoryActionsBuildReview<>(dataType, mConfig,
                mCommandsFactory, editMode, locationClient);
    }

    public <GC extends GvDataList<? extends GvDataParcelable>>
    FactoryActionsBuildReview<GC> newEditorActions(GvDataType<GC> dataType,
                                                   ReviewEditor.EditMode defaultEditMode,
                                                   LocationClient locationClient,
                                                   PublishAction publishAction) {
        return new FactoryActionsEditReview<>(dataType, mConfig, mCommandsFactory,
                defaultEditMode, locationClient, publishAction);
    }

    public FactoryActionsReviewView<GvSocialPlatform>
    newPublishActions(ReviewEditor<?> editor,
                      PublishAction publishAction,
                      GvSocialPlatformList platforms,
                      PlatformAuthoriser authoriser) {
        return new FactoryActionsPublish(editor, publishAction, platforms, authoriser);
    }

    public FactoryActionsReviewView<GvNode> newFeedActions
            (ReviewNode node, FactoryReviewView viewFactory) {
        ActionsParameters<GvNode> params = newListActionParams(node, viewFactory, false);
        return new FactoryActionsViewReviews(params, mReviewsRepo, mConfig.getProfileEditor());
    }

    public FactoryActionsReviewView<GvNode> newListActions(ReviewNode node,
                                                           FactoryReviewView viewFactory,
                                                           @Nullable AuthorId followAuthor) {
        boolean follow = followAuthor != null;
        ActionsParameters<GvNode> actionParams = newListActionParams(node, viewFactory, follow);
        AuthorReference name = follow ? mAuthorsRepo.getReference(followAuthor) : null;
        return newListActions(actionParams, name);
    }

    public <T extends GvData> FactoryActionsSearch<T> newSearchActions(GvDataType<T> dataType,
                                                                       FactoryReviewView
                                                                               viewFactory) {
        FactoryActionsSearch<?> factoryActions;
        if (dataType.equals(GvAuthor.TYPE)) {
            factoryActions = new FactoryActionsSearch.Author(getLauncher(), viewFactory);
        } else {
            factoryActions = new FactoryActionsSearch<>(dataType, getLauncher(), viewFactory);
        }
        //TODO make type safe
        return (FactoryActionsSearch<T>) factoryActions;
    }

    @NonNull
    public <T extends GvData> FactoryActionsReviewView<T> newDataActions(ReviewViewAdapter<T> adapter,
                                                                         FactoryReviewView viewFactory) {
        GvDataType<T> dataType = getDataType(adapter);
        ActionsParameters<T> params
                = newDefaultActionsParameters(dataType, viewFactory).setStamp(adapter.getStamp());

        ReviewNode node = null;
        try {
            AdapterReviewNode<?> nodeAdapter = (AdapterReviewNode<?>) adapter;
            node = nodeAdapter.getNode();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }

        if (node != null) {
            params.setContextCommands(getDataContextCommands(adapter, viewFactory))
                    .setComparators(getPlaceholderComparator("Unsorted"));
        }

        //TODO make type safe
        FactoryActionsReviewView<?> factory;
        if (dataType.equals(GvComment.Reference.TYPE) && node != null) {
            factory = new FactoryActionsViewComments((ActionsParameters<GvComment.Reference>)
                    params);
        } else if (dataType.equals(GvLocation.Reference.TYPE) && node != null) {
            factory = new FactoryActionsViewLocations((ActionsParameters<GvLocation.Reference>)
                    params,
                    node, mConfig.getBespokeDatumViewer(dataType.getDatumName()), mConverter
                    .newConverterLocations());
        } else if (dataType.equals(GvImage.Reference.TYPE) && node != null) {
            factory = new FactoryActionsViewImages((ActionsParameters<GvImage.Reference>) params,
                    node);
        } else if (node != null && dataType.equals(GvSize.Reference.TYPE)) {
            params.setContextCommands(getDefaultContextCommands(node, viewFactory, Strings.Buttons
                    .FILTER, false)).setComparators(getPlaceholderComparator("Data"));
            factory = new FactoryActionsViewSummary((ActionsParameters<GvSize.Reference>)
                    params, node);
        } else {
            if (dataType.equals(GvBucket.TYPE) && node != null) {
                params.setContextCommands(getDefaultContextCommands(node, viewFactory, Strings
                        .Buttons
                        .DISTRIBUTION, false)).setComparators(getPlaceholderComparator("High-Low"));
            }

            factory = new FactoryActionsViewData<>(params);
        }

        return (FactoryActionsReviewView<T>) factory;
    }

    private UiLauncher getLauncher() {
        return mConfig.getUiLauncher();
    }

    private FactoryActionsReviewView<GvNode> newListActions
            (ActionsParameters<GvNode> parameters,

             @Nullable
                     AuthorReference
                     authorRef) {
        return new FactoryActionsViewReviews(parameters, authorRef);
    }

    @NonNull
    private ActionsParameters<GvNode> newListActionParams(ReviewNode node, FactoryReviewView
            viewFactory, boolean withFollow) {
        ActionsParameters<GvNode> params = newActionParams(GvNode.TYPE, viewFactory);
        params.setContextCommands(getDefaultContextCommands(node, viewFactory, Strings.Buttons.LIST,
                withFollow)).setComparators(mComparators.newReviewComparators());
        return params;
    }

    @NonNull
    private <T extends GvData> ActionsParameters<T> newActionParams(GvDataType<T> dataType,
                                                                    FactoryReviewView viewFactory) {
        return new ActionsParameters<>(dataType, viewFactory,
                mCommandsFactory, mAuthorsRepo, getLauncher());
    }

    @NonNull
    private <T extends GvData> ActionsParameters<T> newDefaultActionsParameters(GvDataType<T>
                                                                                        dataType,
                                                                                FactoryReviewView
                                                                                        viewFactory) {
        LaunchableConfig viewer = null;
        String datumName = dataType.getDatumName();
        if (mConfig.hasConfig(datumName)) viewer = mConfig.getViewer(datumName);

        return newActionParams(dataType, viewFactory).setGridItemConfig(viewer);
    }

    private <T extends GvData> ComparatorCollection<? super T> getPlaceholderComparator(String name) {
        //TODO redo once there is a sever-client way of doing comparators.
        NamedComparator<T> placeholder = new NamedComparator.Builder<>(name, new Comparator<T>() {
            @Override
            public int compare(T lhs, T rhs) {
                return lhs.getReviewId().toString().compareTo(rhs.getReviewId().toString());
            }
        }).build();

        return new ComparatorCollectionImpl<>(placeholder);
    }

    @NonNull
    private <T extends GvData> CommandList getDataContextCommands(final ReviewViewAdapter<T> adapter,
                                                                  final FactoryReviewView viewFactory) {

        String name = TextUtils.capitalize(getDataType(adapter).getDataName());
        CommandList list = new CommandList(name);
        list.add(mCommandsFactory.newLaunchListCommand(adapter, viewFactory));
        list.add(mCommandsFactory.newLaunchAggregateCommand(adapter, viewFactory));
        list.add(mCommandsFactory.newLaunchDistributionCommand(adapter, viewFactory));
        list.add(mCommandsFactory.newLaunchMappedExpandedCommand(adapter));
        list.add(mCommandsFactory.newLaunchPagedExpandedCommand(adapter));
        return list;
    }

    @NonNull
    private CommandList getDefaultContextCommands(final ReviewNode node,
                                                  final FactoryReviewView viewFactory,
                                                  String defaultView,
                                                  final boolean withFollowForList) {
        CommandList list = new CommandList(defaultView);
        list.add(new Command(Strings.Buttons.LIST) {
            @Override
            public void execute() {
                mCommandsFactory.newLaunchViewCommand(viewFactory.newListView(node,
                        withFollowForList ? node.getAuthorId() : null))
                        .execute();
            }
        });

        list.add(new Command(Strings.Buttons.DISTRIBUTION) {
            @Override
            public void execute() {
                mCommandsFactory.newLaunchViewCommand(viewFactory.newDistributionView(node))
                        .execute();
            }
        });

        list.add(new Command(Strings.Buttons.FILTER) {
            @Override
            public void execute() {
                mCommandsFactory.newLaunchViewCommand(viewFactory.newAggregateView(node)).execute();
            }
        });

        list.add(mCommandsFactory.newLaunchMappedCommand(node));
        list.add(mCommandsFactory.newLaunchPagedCommand(node));
        return list;
    }

    private <T extends GvData> GvDataType<T> getDataType(ReviewViewAdapter<T> adapter) {
        //TODO make type safe
        return (GvDataType<T>) adapter.getGvDataType();
    }
}
