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
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.ReviewStamp;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
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
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Factories
        .FactoryActionsNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Factories
        .FactoryActionsPublish;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Factories
        .FactoryActionsReviewSummary;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Factories
        .FactoryActionsReviewsList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Factories
        .FactoryActionsSearch;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Factories
        .FactoryActionsViewData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Factories
        .FactoryReviewViewActions;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .Implementation.ReviewViewActions;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .Implementation.SubjectBannerFilter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvAuthor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvNode;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSize;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvSocialPlatform;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvSocialPlatformList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.ReviewLauncher
        .ReviewLauncher;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewDefault;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewParams;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View
        .ReviewViewPerspective;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewsListView;
import com.chdryra.android.reviewer.Social.Implementation.SocialPlatformList;
import com.chdryra.android.reviewer.Social.Interfaces.PlatformAuthoriser;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPlatform;
import com.chdryra.android.reviewer.View.Configs.ConfigUi;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.UiLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 26/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewView {
    private final ConfigUi mConfig;
    private final FactoryReviewEditor<?> mFactoryReviewEditor;
    private final FactoryReviewViewParams mParamsFactory;
    private final AuthorsRepository mAuthorsRepo;
    private FactoryReviewViewAdapter mAdapterFactory;

    public FactoryReviewView(ConfigUi config,
                             FactoryReviewEditor<?> factoryReviewEditor,
                             FactoryReviewViewParams paramsFactory,
                             AuthorsRepository authorsRepo) {
        mConfig = config;
        mFactoryReviewEditor = factoryReviewEditor;
        mAuthorsRepo = authorsRepo;
        mParamsFactory = paramsFactory;
    }

    public FactoryReviewViewAdapter getAdapterFactory() {
        return mAdapterFactory;
    }

    public void setAdapterFactory(FactoryReviewViewAdapter adapterFactory) {
        mAdapterFactory = adapterFactory;
        mAdapterFactory.setReviewViewFactory(this);
    }

    public ReviewEditor<? extends GvDataList<? extends GvDataParcelable>>
    newEditor(@Nullable Review template, UiLauncher launcher, LocationClient locationClient) {
        return mFactoryReviewEditor.newEditor(template, launcher, locationClient);
    }

    public <T extends GvData> ReviewView<?> newNullView(GvDataType<T> dataType) {
        return newReviewView(mAdapterFactory.newNullAdapter(dataType),
                new ReviewViewActions<>(new FactoryActionsNone<>(dataType)), new ReviewViewParams());
    }

    public ReviewsListView newFeedView(ReviewNode node) {
        return newReviewsListView(node, mAdapterFactory.newFeedAdapter(node),
                new FactoryActionsReviewsList.Feed(this, getOptionsConfig()));
    }

    public ReviewsListView newReviewsListView(ReviewNode node, @Nullable AuthorId followAuthorId) {
        return newReviewsListView(node, mAdapterFactory.newChildListAdapter(node),
                new FactoryActionsReviewsList(this, getOptionsConfig(), followAuthorId));
    }

    public ReviewView<?> newPublishView(ReviewViewAdapter<?> toPublish,
                                        ReviewPublisher publisher,
                                        PublishAction.PublishCallback callback,
                                        SocialPlatformList platforms,
                                        PlatformAuthoriser authoriser) {
        GvSocialPlatformList list = new GvSocialPlatformList();
        for(SocialPlatform platform : platforms) {
            list.add(new GvSocialPlatform(platform));
        }

        PublishAction publishAction = new PublishAction(publisher, callback);
        FactoryActionsPublish factory = new FactoryActionsPublish(authoriser, list, publishAction);

        return newReviewView(mAdapterFactory.newPublishAdapter(list, toPublish),
                new ReviewViewActions<>(factory), mParamsFactory.newPublishParams());
    }

    public <T extends GvData> ReviewView<T> newDefaultView(ReviewViewAdapter<T> adapter,
                                                           UiLauncher uiLauncher) {
        return newReviewView(adapter, newViewActions(adapter, uiLauncher),
                mParamsFactory.newViewParams(getDataType(adapter)));
    }

    public <T extends GvData> ReviewView<T> newSearchView(ReviewViewAdapter.Filterable<T> adapter,
                                                          UiLauncher launcher,
                                                          String hint) {
        GvDataType<T> dataType = getDataType(adapter);
        return newReviewView(adapter, newSearchActions(dataType, launcher),
                mParamsFactory.newSearchParams(dataType, hint));
    }

    //private
    private LaunchableConfig getOptionsConfig() {
        return mConfig.getReviewOptions();
    }

    private <T extends GvData> ReviewView<T> newReviewView(ReviewViewAdapter<T> adapter,
                                                           ReviewViewActions<T> actions,
                                                           ReviewViewParams params) {
        return new ReviewViewDefault<>(newPerspective(adapter, actions, params));
    }

    @NonNull
    private ReviewsListView newReviewsListView(ReviewNode node,
                                               ReviewViewAdapter<GvNode> adapter,
                                               FactoryReviewViewActions<GvNode> actionsFactory) {
        ReviewViewActions<GvNode> actions = new ReviewViewActions<>(actionsFactory);
        ReviewViewParams params = mParamsFactory.newReviewsListParams();
        return new ReviewsListView(node, newPerspective(adapter, actions, params));
    }

    @NonNull
    private <T extends GvData> ReviewViewPerspective<T> newPerspective(ReviewViewAdapter<T> adapter,
                                                                       ReviewViewActions<T> actions,
                                                                       ReviewViewParams params) {
        return new ReviewViewPerspective<>(adapter, actions, params);
    }

    @NonNull
    private <T extends GvData> ReviewViewActions<T> newSearchActions(GvDataType<T> dataType, UiLauncher launcher) {
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
    private <T extends GvData> ReviewViewActions<T> newViewActions(ReviewViewAdapter<T> adapter,
                                                                   UiLauncher uiLauncher) {
        GvDataType<T> dataType = getDataType(adapter);
        FactoryReviewViewActions<?> factory;
        ReviewStamp stamp = adapter.getStamp();

        if (dataType.equals(GvSize.Reference.TYPE)) {
            factory = new FactoryActionsReviewSummary(this, uiLauncher, getOptionsConfig(), stamp, mAuthorsRepo);
        } else {
            LaunchableConfig viewer = mConfig.getViewer(dataType.getDatumName());
            viewer.setLauncher(uiLauncher);
            ReviewLauncher reviewLauncher = uiLauncher.newReviewLauncher();

            if (dataType.equals(GvComment.Reference.TYPE)) {
                factory = new FactoryActionsViewData.Comments(this, reviewLauncher, stamp,
                        mAuthorsRepo, viewer);
            } else {
                factory = new FactoryActionsViewData<>(dataType, this, reviewLauncher, stamp,
                        mAuthorsRepo, viewer);
            }
        }

        //TODO make type safe
        return new ReviewViewActions<>((FactoryReviewViewActions<T>) factory);
    }

    private <T extends GvData> GvDataType<T> getDataType(ReviewViewAdapter<T> adapter) {
        //TODO make type safe
        return (GvDataType<T>) adapter.getGvDataType();
    }
}
