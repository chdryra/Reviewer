/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Factories;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.corelibrary.LocationUtils.LocationClient;
import com.chdryra.android.startouch.Application.Implementation.Strings;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataRating;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.startouch.NetworkServices.ReviewPublishing.Interfaces.ReviewPublisher;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Factories.FactoryReviewEditor;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation.PublishAction;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.ReviewEditor;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Factories.FactoryActionsReviewView;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Factories.FactoryActionsSearch;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Factories.FactoryReviewViewActions;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Implementation.ReviewViewActions;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Implementation.SubjectFilter;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvAuthorName;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvNode;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSocialPlatform;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSocialPlatformList;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.VhBucket;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.VhRatingBucket;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.ViewHolderFactory;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.View.AdapterNodeFollowable;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.View.ReviewViewDefault;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.View.ReviewViewNode;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.View.ReviewViewParams;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.View.ReviewViewPerspective;
import com.chdryra.android.startouch.Social.Implementation.SocialPlatformList;
import com.chdryra.android.startouch.Social.Interfaces.PlatformAuthoriser;
import com.chdryra.android.startouch.Social.Interfaces.SocialPlatform;

/**
 * Created by: Rizwan Choudrey
 * On: 26/11/2015
 * Email: rizwan.choudrey@gmail.com
 */

public class FactoryReviewView {
    private final FactoryReviewEditor<?> mEditorFactory;
    private final FactoryReviewViewAdapter mAdapterFactory;
    private final FactoryReviewViewActions mActionsFactory;
    private final FactoryReviewViewParams mParamsFactory;

    public FactoryReviewView(FactoryReviewEditor<?> editorFactory,
                             FactoryReviewViewAdapter adapterFactory,
                             FactoryReviewViewActions actionsFactory,
                             FactoryReviewViewParams paramsFactory) {
        mEditorFactory = editorFactory;
        mAdapterFactory = adapterFactory;
        mParamsFactory = paramsFactory;
        mActionsFactory = actionsFactory;
    }

    public ReviewEditor<? extends GvDataList<? extends GvDataParcelable>>
    newReviewCreator(ReviewEditor.EditMode editMode,
                     LocationClient locationClient,
                     @Nullable Review template) {
        return mEditorFactory.newReviewCreator(editMode, locationClient,
                template);
    }

    public ReviewEditor<? extends GvDataList<? extends GvDataParcelable>>
    newReviewEditor(Review review, LocationClient locationClient,
                    ReviewPublisher publisher, PublishAction.PublishCallback callback) {
        return mEditorFactory.newReviewEditor(review, publisher, callback, locationClient);
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

        ReviewViewAdapter<GvSocialPlatform> adapter
                = mAdapterFactory.newPublishAdapter(list, toPublish.getAdapter());
        FactoryActionsReviewView<GvSocialPlatform> factory
                = mActionsFactory.newPublishActions(toPublish, publishAction, list, authoriser);
        ReviewViewParams params = mParamsFactory.newPublishParams();

        return newReviewView(adapter, newReviewViewActions(factory), params);
    }

    public ReviewViewNode newFeedView(ReviewNode node) {
        ReviewViewAdapter<GvNode> adapter = mAdapterFactory.newFeedAdapter(node);
        FactoryActionsReviewView<GvNode> actionsFactory
                = mActionsFactory.newFeedActions(node, this);

        return newReviewViewNode(node, adapter, actionsFactory);
    }

    public ReviewViewNode newListView(ReviewNode node, @Nullable AuthorId followAuthor) {
        ReviewViewAdapter<GvNode> adapter
                = mAdapterFactory.newReviewsListAdapter(node, followAuthor);
        FactoryActionsReviewView<GvNode> actionsFactory =
                mActionsFactory.newListActions(node, this, followAuthor);

        return newReviewViewNode(node, adapter, actionsFactory);
    }

    public ReviewView<?> newDataView(ReviewNode node, GvDataType<?> dataType) {
        return newReviewView(mAdapterFactory.newReviewDataAdapter(node, dataType));
    }

    public ReviewView<?> newSummaryView(ReviewNode node) {
        return newReviewView(mAdapterFactory.newSummaryAdapter(node));
    }

    public ReviewView<?> newSearchView() {
        ReviewViewAdapter.Filterable<GvAuthorName> adapter = mAdapterFactory.newFollowSearchAdapter();
        return newSearchView(adapter, Strings.EditTexts.Hints.AUTHOR_NAME);
    }

    public ReviewView<?> newAggregateView(ReviewNode node) {
        return newReviewView(mAdapterFactory.newTreeAdapter(node));
    }

    public ReviewView<?> newBucketsView(ReviewNode node) {
        return newReviewView(mAdapterFactory.newBucketAdapter(node,
                new ViewHolderFactory<VhBucket<Float, DataRating>>() {
                    @Override
                    public VhBucket<Float, DataRating> newViewHolder() {
                        return new VhRatingBucket();
                    }
                }));
    }

    public <T extends GvData> ReviewView<T> newReviewView(ReviewViewAdapter<T> adapter) {
        try {
            //TODO make type safe and less hacky
            return (ReviewView<T>) newReviewViewNode((AdapterNodeFollowable) adapter);
        } catch (ClassCastException e) {
            ReviewViewActions<T> actions
                    = newReviewViewActions(mActionsFactory.newDataActions(adapter, this));
            ReviewViewParams params = mParamsFactory.newViewParams(getDataType(adapter));

            return newReviewView(adapter, actions, params);
        }
    }

    /***** private methods *****/
    private <T extends GvData> ReviewView<T> newReviewView(ReviewViewAdapter<T> adapter,
                                                           ReviewViewActions<T> actions,
                                                           ReviewViewParams params) {
        return new ReviewViewDefault<>(newPerspective(adapter, actions, params));
    }

    @NonNull
    private ReviewViewNode newReviewViewNode(ReviewNode node,
                                             ReviewViewAdapter<GvNode> adapter,
                                             FactoryActionsReviewView<GvNode> actionsFactory) {
        ReviewViewActions<GvNode> actions = newReviewViewActions(actionsFactory);
        ReviewViewParams params = mParamsFactory.newReviewsListParams();

        return new ReviewViewNode(node, newPerspective(adapter, actions, params));
    }

    @NonNull
    private ReviewView<?> newReviewViewNode(AdapterNodeFollowable adapter) {
        ReviewNode node = adapter.getNode();
        AuthorId followAuthor = adapter.getFollowAuthorId();
        FactoryActionsReviewView<GvNode> actionsFactory
                = mActionsFactory.newListActions(node, this, followAuthor);

        return newReviewViewNode(node, adapter, actionsFactory);
    }

    private <T extends GvData> ReviewView<T> newSearchView(ReviewViewAdapter.Filterable<T> adapter,
                                                           String hint) {
        GvDataType<T> dataType = getDataType(adapter);
        FactoryActionsSearch<T> factory = mActionsFactory.newSearchActions(dataType, this);
        SubjectFilter<T> subjectBanner = factory.newSubjectBannerFilter();

        ReviewViewActions<T> actions = new ReviewViewActions<>(subjectBanner,
                factory.newRatingBar(), subjectBanner, factory.newGridItem(),
                factory.newMenu(), factory.newContextButton());
        ReviewViewParams params = mParamsFactory.newSearchParams(dataType, hint);

        return newReviewView(adapter, actions, params);
    }

    @NonNull
    private <T extends GvData> ReviewViewActions<T> newReviewViewActions
            (FactoryActionsReviewView<T> factory) {
        return new ReviewViewActions<>(factory);
    }

    @NonNull
    private <T extends GvData> ReviewViewPerspective<T> newPerspective(ReviewViewAdapter<T> adapter,
                                                                       ReviewViewActions<T> actions,
                                                                       ReviewViewParams params) {
        return new ReviewViewPerspective<>(adapter, actions, params);
    }

    private <T extends GvData> GvDataType<T> getDataType(ReviewViewAdapter<T> adapter) {
        //TODO make type safe
        return (GvDataType<T>) adapter.getGvDataType();
    }
}
