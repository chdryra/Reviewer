/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 14 May, 2015
 */

package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Factories;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Implementation
        .GvConverters.ConverterGv;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.Interfaces.ReviewViewAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Implementation
        .AdapterCommentsAggregate;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Implementation
        .AdapterReviewNode;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Implementation
        .ViewerChildList;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Implementation
        .ViewerDataToReviews;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Interfaces
        .GridDataViewer;
import com.chdryra.android.reviewer.Models.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Models.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Models.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.ReviewsProviderModel.Interfaces.ReviewsProvider;
import com.chdryra.android.reviewer.TreeMethods.Factories.FactoryVisitorReviewNode;
import com.chdryra.android.reviewer.View.GvDataAggregation.GvDataAggregater;
import com.chdryra.android.reviewer.View.GvDataModel.GvCanonical;
import com.chdryra.android.reviewer.View.GvDataModel.GvCanonicalCollection;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvCriterionList;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataCollection;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.GvFactList;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewOverviewList;
import com.chdryra.android.reviewer.View.Launcher.Factories.FactoryLaunchableUi;
import com.chdryra.android.reviewer.View.Launcher.Factories.FactoryLauncherUi;
import com.chdryra.android.reviewer.View.ReviewViewModel.Builders.BuilderChildListView;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.GridItemLauncher;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.MenuActionNone;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.ReviewViewActions;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.ReviewViewDefault;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.ReviewViewParams;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.ReviewViewPerspective;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.GridItemAction;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.MenuAction;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.ReviewView;

/**
 * Created by: Rizwan Choudrey
 * On: 14/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewViewAdapter {
    private BuilderChildListView mChildViewBuilder;
    private FactoryGridDataViewer mViewerFactory;
    private FactoryVisitorReviewNode mVisitorFactory;
    private FactoryLauncherUi mLauncherFactory;
    private FactoryLaunchableUi mLaunchableFactory;
    private GvDataAggregater mAggregater;
    private ConverterGv mConverter;
    private ReviewsProvider mProvider;

    //Constructors
    public FactoryReviewViewAdapter(BuilderChildListView childViewBuilder,
                                    FactoryVisitorReviewNode visitorFactory,
                                    FactoryLauncherUi launcherFactory,
                                    FactoryLaunchableUi launchableFactory,
                                    GvDataAggregater aggregater,
                                    ReviewsProvider provider,
                                    ConverterGv converter) {
        mChildViewBuilder = childViewBuilder;
        mViewerFactory = new FactoryGridDataViewer(this);
        mAggregater = aggregater;
        mProvider = provider;
        mConverter = converter;
        mVisitorFactory = visitorFactory;
        mLauncherFactory = launcherFactory;
        mLaunchableFactory = launchableFactory;
    }

    private TagsManager getTagsManager() {
        return mProvider.getTagsManager();
    }

    public ReviewViewAdapter newReviewsListAdapter(ReviewNode node) {
        GridItemAction<GvReviewOverviewList.GvReviewOverview> gi
                = new GridItemLauncher<>(mLauncherFactory, mLaunchableFactory);
        MenuAction<GvReviewOverviewList.GvReviewOverview> ma = new MenuActionNone<>();
        ReviewView view = mChildViewBuilder.buildView(node, this, gi, ma);
        return view.getAdapter();
    }

    public <T extends GvData> ReviewViewAdapter newReviewsListAdapter(T datum) {
        Review meta = mProvider.asMetaReview(datum, datum.getStringSummary());
        return newReviewsListAdapter(meta.getTreeRepresentation());
    }

    public <T extends GvData> ReviewViewAdapter newFlattenedReviewsListAdapter(GvDataCollection<T> data) {
        Review meta = mProvider.createFlattenedMetaReview(data, data.getStringSummary());
        return newReviewsListAdapter(meta.getTreeRepresentation());
    }

    public ReviewViewAdapter<GvReviewOverviewList.GvReviewOverview> newChildListAdapter(ReviewNode node) {
        GridDataViewer<GvReviewOverviewList.GvReviewOverview> viewer;
        viewer = new ViewerChildList(node, mConverter.getConverterReviews(), this);
        return  newAdapterReviewNode(node, viewer);
    }

    public ReviewViewAdapter<GvData> newNodeDataAdapter(ReviewNode node) {
        GridDataViewer<GvData> viewer = mViewerFactory.newNodeDataViewer(node, mConverter,
                getTagsManager(), mVisitorFactory, mAggregater);
        return newAdapterReviewNode(node, viewer);
    }

    public <T extends GvData> ReviewViewAdapter<?> newNodeDataAdapter(GvDataCollection<T> data) {
        Review meta = mProvider.createMetaReview(data, data.getStringSummary());
        return newNodeDataAdapter(meta.getTreeRepresentation());
    }

    public <T extends GvData> ReviewViewAdapter<?> newDataToDataAdapter(ReviewNode parent,
                                                                     GvDataCollection<T> data) {
        return newAdapterReviewNode(parent, mViewerFactory.newDataToDataViewer(parent, data));
    }

    public <T extends GvData> ReviewViewAdapter<?> newAggregateToReviewsAdapter
    (GvCanonicalCollection<T> data, String subject) {
        GvDataType<T> type = data.getGvDataType();

        if (type.equals(GvCommentList.GvComment.TYPE)) {
            ReviewNode node = mProvider.createMetaReview(data, subject).getTreeRepresentation();
            //TODO make type safe
            return new AdapterCommentsAggregate(node, mConverter.getConverterImages(),
                    (GvCanonicalCollection<GvCommentList.GvComment>) data, mViewerFactory,
                    mAggregater);
        }

        GridDataViewer<GvCanonical> viewer;
        boolean aggregateToData = type.equals(GvCriterionList.GvCriterion.TYPE) ||
                type.equals(GvFactList.GvFact.TYPE) || type.equals(GvImageList.GvImage.TYPE);
        if (aggregateToData) {
            viewer = mViewerFactory.newAggregateToDataViewer(data, mAggregater);
        } else {
            viewer = mViewerFactory.newDataToReviewsViewer(data);
        }

        return newMetaReviewAdapter(data, subject, viewer);
    }

    public <T extends GvData> ReviewViewAdapter<T> newDataToReviewsAdapter(GvDataCollection<T> data,
                                                                        String subject) {
        ViewerDataToReviews<T> viewer = new ViewerDataToReviews<>(data, this);
        return newMetaReviewAdapter(data, subject, viewer);
    }

    private <T extends GvData> ReviewViewAdapter<T> newAdapterReviewNode(ReviewNode node,
                                                                      GridDataViewer<T> viewer) {
        return new AdapterReviewNode<>(node, mConverter.getConverterImages(), viewer);
    }

    private <T extends GvData> ReviewViewAdapter<T> newMetaReviewAdapter(GvDataCollection<T> data,
                                                                      String subject,
                                                                      GridDataViewer<T> viewer) {
        ReviewNode node = mProvider.createMetaReview(data, subject).getTreeRepresentation();
        return newAdapterReviewNode(node, viewer);
    }
}
