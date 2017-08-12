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

import com.chdryra.android.mygenerallibrary.Comparators.ComparatorCollection;
import com.chdryra.android.mygenerallibrary.Comparators.ComparatorCollectionImpl;
import com.chdryra.android.mygenerallibrary.Comparators.NamedComparator;
import com.chdryra.android.mygenerallibrary.LocationUtils.LocationClient;
import com.chdryra.android.mygenerallibrary.TextUtils.TextUtils;
import com.chdryra.android.reviewer.Algorithms.DataSorting.DataBucketer;
import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataComparatorsPlugin.Api.DataComparatorsApi;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.AuthorReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.TreeMethods.Factories.FactoryDataBucketer;
import com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Interfaces.ReviewPublisher;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsSource;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryReviewEditor;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.PublishAction;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewEditor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Factories.ActionsParameters;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Factories.FactoryActionsPublish;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Factories.FactoryActionsSearch;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Factories.FactoryActionsViewComments;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Factories.FactoryActionsViewData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Factories.FactoryActionsViewImages;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Factories.FactoryActionsViewLocations;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Factories.FactoryActionsViewReviews;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Factories.FactoryActionsViewSummary;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Factories.FactoryReviewViewActions;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.ReviewViewActions;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.SubjectFilter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Factories.FactoryCommands;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.Command;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.CommandsList;
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
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.VhBucket;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.VhRatingBucket;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.ViewHolderFactory;
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

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 26/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewView {
    private final UiConfig mConfig;
    private final FactoryReviewViewAdapter mAdapterFactory;
    private final FactoryReviewEditor<?> mEditorFactory;
    private final FactoryReviewViewParams mParamsFactory;
    private final FactoryDataBucketer mBucketerFactory;
    private final FactoryCommands mCommandsFactory;
    private final ReviewsSource mReviewsRepo;
    private final AuthorsRepository mAuthorsRepo;
    private final DataComparatorsApi mComparators;
    private final ConverterGv mConverter;

    public FactoryReviewView(UiConfig config,
                             FactoryReviewViewAdapter adapterFactory,
                             FactoryReviewEditor<?> editorFactory,
                             FactoryReviewViewParams paramsFactory,
                             FactoryDataBucketer bucketerFactory,
                             FactoryCommands commandsFactory,
                             ReviewsSource reviewsRepo,
                             AuthorsRepository authorsRepo,
                             DataComparatorsApi comparators,
                             ConverterGv converter) {
        mConfig = config;
        mAdapterFactory = adapterFactory;
        mEditorFactory = editorFactory;
        mParamsFactory = paramsFactory;
        mBucketerFactory = bucketerFactory;
        mCommandsFactory = commandsFactory;
        mReviewsRepo = reviewsRepo;
        mAuthorsRepo = authorsRepo;
        mComparators = comparators;
        mConverter = converter;
    }

    public ReviewEditor<? extends GvDataList<? extends GvDataParcelable>>
    newReviewCreator(ReviewEditor.EditMode editMode,
                     LocationClient locationClient,
                     @Nullable Review template) {
        return mEditorFactory.newReviewCreator(mConfig, getUiLauncher(), editMode, locationClient,
                template);
    }

    public ReviewEditor<? extends GvDataList<? extends GvDataParcelable>>
    newReviewEditor(Review review, LocationClient locationClient,
                    ReviewPublisher publisher, PublishAction.PublishCallback callback) {
        return mEditorFactory.newReviewEditor(mConfig, getUiLauncher(), locationClient,
                review, publisher, callback);
    }

    public ReviewView<?> newPublishView(ReviewEditor<?> toPublish,
                                        ReviewPublisher publisher,
                                        PublishAction.PublishCallback callback,
                                        SocialPlatformList platforms,
                                        PlatformAuthoriser authoriser) {
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

    public ReviewViewNode newFeedView(ReviewNode node) {
        FactoryActionsViewReviews actionsFactory
                = new FactoryActionsViewReviews(newListActionParams(node, false), mReviewsRepo,
                mConfig.getProfileEditor());
        return newListView(node, mAdapterFactory.newFeedAdapter(node), actionsFactory);
    }

    public ReviewViewNode newListView(ReviewNode node, boolean followMenu) {
        AuthorId followAuthorId = followMenu ? node.getAuthorId() : null;
        return newListView(node,
                mAdapterFactory.newReviewsListAdapter(node, followAuthorId), followAuthorId);
    }

    public ReviewView<?> newDataView(ReviewNode node, GvDataType<?> dataType) {
        return newViewWithAdapter(mAdapterFactory.newReviewDataAdapter(node, dataType));
    }

    public ReviewView<?> newSummaryView(ReviewNode node) {
        return newViewWithAdapter(mAdapterFactory.newSummaryAdapter(node));
    }

    public <T extends GvData> ReviewView<T> newViewWithAdapter(ReviewViewAdapter<T> adapter) {
        ReviewView view;
        try {
            AdapterNodeFollowable followable = (AdapterNodeFollowable) adapter;
            view = newListView(followable.getNode(), followable,
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

    private UiLauncher getUiLauncher() {
        return mConfig.getUiLauncher();
    }

    @NonNull
    private ActionsParameters<GvNode> newListActionParams(ReviewNode node, boolean withFollow) {
        ActionsParameters<GvNode> params = newActionParams(GvNode.TYPE);
        params.setContextCommands(getDefaultContextCommands(node, Strings.Buttons.LIST,
                withFollow)).setComparators(mComparators.newReviewComparators());
        return params;
    }

    private ReviewView<?> newAggregateView(ReviewNode node) {
        return newViewWithAdapter(mAdapterFactory.newTreeAdapter(node));
    }

    private ReviewView<?> newDistributionView(ReviewNode node) {
        ReviewViewAdapter<?> adapter = newBucketAdapter(node, mBucketerFactory.newRatingsBucketer(),
                new ViewHolderFactory<VhBucket<Float, DataRating>>() {
                    @Override
                    public VhBucket<Float, DataRating> newViewHolder() {
                        return new VhRatingBucket();
                    }
                });

        return newViewWithAdapter(adapter);
    }

    private <BucketingValue, Data extends HasReviewId> ReviewViewAdapter<?>
    newBucketAdapter(ReviewNode node,
                     DataBucketer<BucketingValue, Data> bucketer,
                     ViewHolderFactory<VhBucket<BucketingValue, Data>> vhFactory) {
        return mAdapterFactory.newBucketAdapter(node, bucketer, vhFactory);
    }

    private <T extends GvData> ReviewView<T> newSearchView(ReviewViewAdapter.Filterable<T> adapter,
                                                           String hint) {
        GvDataType<T> dataType = getDataType(adapter);
        return newReviewView(adapter, newSearchActions(dataType, getUiLauncher()),
                mParamsFactory.newSearchParams(dataType, hint));
    }

    //private
    @NonNull
    private ReviewViewNode newListView(ReviewNode node,
                                       ReviewViewAdapter<GvNode> adapter,
                                       @Nullable AuthorId followAuthorId) {
        boolean follow = followAuthorId != null;
        AuthorReference name = follow ? mAuthorsRepo.getReference
                (followAuthorId) : null;
        return newListView(node, adapter,
                new FactoryActionsViewReviews(newListActionParams(node, follow), name));
    }

    private CommandsList getDefaultContextCommands(final ReviewNode node, String defaultView,
                                                   final boolean withFollowForList) {
        CommandsList list = new CommandsList(defaultView);
        list.add(new Command(Strings.Buttons.LIST) {
            @Override
            public void execute() {
                mCommandsFactory.newLaunchViewCommand(newListView(node, withFollowForList)).execute();
            }
        });

        list.add(new Command(Strings.Buttons.DISTRIBUTION) {
            @Override
            public void execute() {
                mCommandsFactory.newLaunchViewCommand(newDistributionView(node)).execute();
            }
        });

        list.add(new Command(Strings.Buttons.AGGREGATE) {
            @Override
            public void execute() {
                mCommandsFactory.newLaunchViewCommand(newAggregateView(node)).execute();
            }
        });

        list.add(mCommandsFactory.newLaunchMappedCommand(node));
        list.add(mCommandsFactory.newLaunchPagedCommand(node));
        return list;
    }

    private <T extends GvData> ReviewView<T> newReviewView(ReviewViewAdapter<T> adapter,
                                                           ReviewViewActions<T> actions,
                                                           ReviewViewParams params) {
        return new ReviewViewDefault<>(newPerspective(adapter, actions, params));
    }

    @NonNull
    private ReviewViewNode newListView(ReviewNode node,
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

        SubjectFilter<T> subjectBanner = factory.newSubjectBannerFilter();
        return new ReviewViewActions<>(subjectBanner,
                factory.newRatingBar(), subjectBanner, factory.newGridItem(),
                factory.newMenu(), factory.newContextButton());
    }

    @NonNull
    private <T extends GvData> ReviewViewActions<T> newViewActions(ReviewViewAdapter<T> adapter) {
        ReviewNode node = null;
        try {
            AdapterReviewNode<?> nodeAdapter = (AdapterReviewNode<?>) adapter;
            node = nodeAdapter.getNode();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }

        GvDataType<T> dataType = getDataType(adapter);
        ActionsParameters<T> params = newDefaultActionsParameters(dataType).setStamp(adapter
                .getStamp());
        if(node != null) {
            params.setContextCommands(getDefaultContextCommands(node, TextUtils.capitalize(dataType.getDataName()), false))
            .setComparators(getPlaceholderComparator("Unsorted"));
        }

        //TODO make type safe
        FactoryReviewViewActions<?> factory;
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
            params.setContextCommands(getDefaultContextCommands(node, Strings.Buttons
                    .AGGREGATE, false)).setComparators(getPlaceholderComparator("Review data"));
            factory = new FactoryActionsViewSummary((ActionsParameters<GvSize.Reference>)
                    params, node);
        } else {
            if (dataType.equals(GvBucket.TYPE) && node != null) {
                params.setContextCommands(getDefaultContextCommands(node, Strings.Buttons
                        .DISTRIBUTION, false)).setComparators(getPlaceholderComparator("High-Low"));
            }
            factory = new FactoryActionsViewData<>(params);
        }

        return new ReviewViewActions<>((FactoryReviewViewActions<T>) factory);
    }

    @NonNull
    private <T extends GvData> ActionsParameters<T> newDefaultActionsParameters(GvDataType<T>
                                                                                            dataType) {
        LaunchableConfig viewer = null;
        String datumName = dataType.getDatumName();
        if (mConfig.hasConfig(datumName)) viewer = mConfig.getViewer(datumName);

        return newActionParams(dataType).setGridItemConfig(viewer);
    }

    @NonNull
    private <T extends GvData> ActionsParameters<T> newActionParams(GvDataType<T> dataType) {
        return new ActionsParameters<>(dataType, this,
                mCommandsFactory, mAuthorsRepo, getUiLauncher());
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

    private <T extends GvData> GvDataType<T> getDataType(ReviewViewAdapter<T> adapter) {
        //TODO make type safe
        return (GvDataType<T>) adapter.getGvDataType();
    }
}
