/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.LocationUtils.LocationClient;
import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataComparatorsPlugin.Api.DataComparatorsApi;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.ReviewStamp;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.AuthorReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Interfaces.ReviewPublisher;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepository;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryReviewEditor;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.PublishAction;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewEditor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Factories.FactoryActionsPublish;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Factories.FactoryActionsReviewsList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Factories.FactoryActionsSearch;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Factories.FactoryActionsViewComments;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Factories.FactoryActionsViewData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Factories.FactoryActionsViewLocations;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Factories.FactoryReviewViewActions;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.ReviewViewActions;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.SubjectBannerFilter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Factories.FactoryCommands;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvAuthor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvNode;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSize;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSocialPlatform;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSocialPlatformList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.AdapterNodeFollowable;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.AdapterReviewNode;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewDefault;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewNode;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewParams;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewPerspective;
import com.chdryra.android.reviewer.Social.Implementation.SocialPlatformList;
import com.chdryra.android.reviewer.Social.Interfaces.PlatformAuthoriser;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPlatform;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.Configs.Interfaces.UiConfig;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.UiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 26/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewView {
    private final UiConfig mConfig;
    private final FactoryReviewViewAdapter mAdapterFactory;
    private final FactoryReviewEditor<?> mFactoryReviewEditor;
    private final FactoryReviewViewParams mParamsFactory;
    private final FactoryCommands mCommandsFactory;
    private final AuthorsRepository mAuthorsRepo;
    private final DataComparatorsApi mComparators;

    public FactoryReviewView(UiConfig config,
                             FactoryReviewViewAdapter adapterFactory,
                             FactoryReviewEditor<?> factoryReviewEditor,
                             FactoryReviewViewParams paramsFactory,
                             FactoryCommands commandsFactory,
                             AuthorsRepository authorsRepo,
                             DataComparatorsApi comparators) {
        mConfig = config;
        mAdapterFactory = adapterFactory;
        mFactoryReviewEditor = factoryReviewEditor;
        mParamsFactory = paramsFactory;
        mCommandsFactory = commandsFactory;
        mAuthorsRepo = authorsRepo;
        mComparators = comparators;
    }

    public ReviewEditor<? extends GvDataList<? extends GvDataParcelable>>
    newEditor(ReviewEditor.EditMode editMode, LocationClient locationClient, @Nullable Review
            template) {
        return mFactoryReviewEditor.newEditor(mConfig, getUiLauncher(), editMode, locationClient,
                template);
    }

    public ReviewViewNode newFeedView(ReviewNode node) {
        return newReviewsListView(node, mAdapterFactory.newFeedAdapter(node),
                new FactoryActionsReviewsList.Feed(getUiLauncher(), this, mCommandsFactory,
                        getOptionsConfig(), mComparators));
    }

    public ReviewViewNode newReviewsListView(ReviewNode node, boolean followMenu) {
        AuthorId followAuthorId = followMenu ? node.getAuthorId() : null;
        return newDefaultReviewsListView(node,
                mAdapterFactory.newReviewsListAdapter(node, followAuthorId), followAuthorId);
    }

    public ReviewView<?> newPublishView(ReviewEditor<?> toPublish,
                                        ReviewPublisher publisher,
                                        SocialPlatformList platforms,
                                        PlatformAuthoriser authoriser,
                                        PublishAction.PublishCallback callback) {
        GvSocialPlatformList list = new GvSocialPlatformList();
        for (SocialPlatform platform : platforms) {
            list.add(new GvSocialPlatform(platform));
        }

        PublishAction publishAction = new PublishAction(publisher, callback);
        FactoryActionsPublish factory = new FactoryActionsPublish(toPublish, publishAction, list,
                authoriser);

        return newReviewView(mAdapterFactory.newPublishAdapter(list, toPublish.getAdapter()),
                new ReviewViewActions<>(factory), mParamsFactory.newPublishParams());
    }
    public ReviewView<?> newDataView(ReviewNode node, GvDataType<?> dataType) {
        return newDefaultView(mAdapterFactory.newReviewDataAdapter(node, dataType));
    }

    public ReviewView<?> newSummaryView(ReviewNode node) {
        return newDefaultView(mAdapterFactory.newSummaryAdapter(node));
    }

    public <T extends GvData> ReviewView<T> newDefaultView(ReviewViewAdapter<T> adapter) {
        ReviewView view;
        try {
            AdapterNodeFollowable followable = (AdapterNodeFollowable) adapter;
            view = newDefaultReviewsListView(followable.getNode(), followable,
                    followable.getFollowAuthorId());
        } catch (ClassCastException e) {
            view = newReviewView(adapter, newViewActions(adapter),
                    mParamsFactory.newViewParams(getDataType(adapter)));
        }

        //TODO make type safe
        return (ReviewView<T>) view;
    }

    public ReviewView<?> newSearchView() {
        ReviewViewAdapter.Filterable<GvAuthor> adapter = mAdapterFactory.newFollowSearchAdapter();
        return newSearchView(adapter, Strings.EditTexts.Hints.AUTHOR_NAME);
    }

    private <T extends GvData> ReviewView<T> newSearchView(ReviewViewAdapter.Filterable<T> adapter,
                                                          String hint) {
        GvDataType<T> dataType = getDataType(adapter);
        return newReviewView(adapter, newSearchActions(dataType, getUiLauncher()),
                mParamsFactory.newSearchParams(dataType, hint));
    }

    private UiLauncher getUiLauncher() {
        return mConfig.getUiLauncher();
    }

    //private
    private LaunchableConfig getOptionsConfig() {
        return mConfig.getReviewOptions();
    }

    @NonNull
    private ReviewViewNode newDefaultReviewsListView(ReviewNode node,
                                                     ReviewViewAdapter<GvNode> adapter,
                                                     @Nullable AuthorId followAuthorId) {
        AuthorReference name = followAuthorId == null ? null : mAuthorsRepo.getReference(followAuthorId);
        return newReviewsListView(node, adapter,
                new FactoryActionsReviewsList(getUiLauncher(), this, mCommandsFactory,
                        getOptionsConfig(), mComparators, name));
    }

    private <T extends GvData> ReviewView<T> newReviewView(ReviewViewAdapter<T> adapter,
                                                           ReviewViewActions<T> actions,
                                                           ReviewViewParams params) {
        return new ReviewViewDefault<>(newPerspective(adapter, actions, params));
    }

    @NonNull
    private ReviewViewNode newReviewsListView(ReviewNode node,
                                              ReviewViewAdapter<GvNode> adapter,
                                              FactoryReviewViewActions<GvNode> actionsFactory) {
        ReviewViewActions<GvNode> actions = new ReviewViewActions<>(actionsFactory);
        ReviewViewParams params = mParamsFactory.newReviewsListParams();
        return new ReviewViewNode(node, newPerspective(adapter, actions, params));
    }

    @NonNull
    private <T extends GvData> ReviewViewPerspective<T> newPerspective(ReviewViewAdapter<T> adapter,
                                                                       ReviewViewActions<T> actions,
                                                                       ReviewViewParams params) {
        return new ReviewViewPerspective<>(adapter, actions, params);
    }

    @NonNull
    private <T extends GvData> ReviewViewActions<T> newSearchActions(GvDataType<T> dataType,
                                                                     UiLauncher launcher) {
        FactoryActionsSearch<?> factoryActions;
        if (dataType.equals(GvAuthor.TYPE)) {
            factoryActions = new FactoryActionsSearch.Author(launcher, this);
        } else {
            factoryActions = new FactoryActionsSearch<>(dataType, launcher, this);
        }
        //TODO make type safe
        FactoryActionsSearch<T> factory = (FactoryActionsSearch<T>) factoryActions;

        SubjectBannerFilter<T> subjectBanner = factory.newSubjectBannerFilter();
        return new ReviewViewActions<>(subjectBanner,
                factory.newRatingBar(), subjectBanner, factory.newGridItem(),
                factory.newMenu(), factory.newContextButton());
    }

    @NonNull
    private <T extends GvData> ReviewViewActions<T> newViewActions(ReviewViewAdapter<T> adapter) {
        GvDataType<T> dataType = getDataType(adapter);
        FactoryReviewViewActions<?> factory;
        ReviewStamp stamp = adapter.getStamp();

        ReviewNode node = null;
        try {
            AdapterReviewNode<?> nodeAdapter = (AdapterReviewNode<?>) adapter;
            node = nodeAdapter.getNode();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }

        LaunchableConfig viewer = dataType.equals(GvSize.Reference.TYPE) ? null :
                mConfig.getViewer(dataType.getDatumName());
        if (viewer != null && dataType.equals(GvComment.Reference.TYPE)) {
                factory = new FactoryActionsViewComments(this, mCommandsFactory, stamp,
                        mAuthorsRepo, getUiLauncher(), getOptionsConfig(), viewer, node);
        } else if (viewer != null && dataType.equals(GvLocation.Reference.TYPE)) {
                factory = new FactoryActionsViewLocations(this, mCommandsFactory, stamp,
                        mAuthorsRepo, getUiLauncher(), getOptionsConfig(), viewer, node);
        } else {
            factory = new FactoryActionsViewData<>(dataType, this, mCommandsFactory, stamp,
                        mAuthorsRepo, getUiLauncher(), getOptionsConfig(), viewer, node);
        }

        //TODO make type safe
        return new ReviewViewActions<>((FactoryReviewViewActions<T>) factory);
    }

    private <T extends GvData> GvDataType<T> getDataType(ReviewViewAdapter<T> adapter) {
        //TODO make type safe
        return (GvDataType<T>) adapter.getGvDataType();
    }
}
