/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 14 May, 2015
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories;

import com.chdryra.android.reviewer.DataDefinitions.DataConverters.Implementation.GvConverters
        .ConverterGv;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviewTreeTraverser;
import com.chdryra.android.reviewer.Model.Factories.FactoryVisitorReviewNode;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.ReviewNode;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel.ReviewsFeed;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.TagsManager;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataCollection;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.GridDataViewer;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCanonical;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCanonicalCollection;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCriterion;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvDataAggregater;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvReviewOverview;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View
        .AdapterCommentsAggregate;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.AdapterReviewNode;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ViewerChildList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View
        .ViewerDataToReviews;

/**
 * Created by: Rizwan Choudrey
 * On: 14/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewViewAdapter {
    private FactoryReviewViewLaunchable mLaunchableFactory;
    private FactoryGridDataViewer mViewerFactory;
    private FactoryVisitorReviewNode mVisitorFactory;
    private FactoryReviewTreeTraverser mTraverserFactory;
    private GvDataAggregater mAggregater;
    private ConverterGv mConverter;
    private ReviewsFeed mProvider;

    //Constructors
    public FactoryReviewViewAdapter(FactoryReviewViewLaunchable launchableFactory,
                                    FactoryVisitorReviewNode visitorFactory,
                                    FactoryReviewTreeTraverser traverserFactory,
                                    GvDataAggregater aggregater,
                                    ReviewsFeed provider,
                                    ConverterGv converter) {
        mViewerFactory = new FactoryGridDataViewer(this);
        mLaunchableFactory = launchableFactory;
        mAggregater = aggregater;
        mProvider = provider;
        mConverter = converter;
        mVisitorFactory = visitorFactory;
        mTraverserFactory = traverserFactory;
    }

    private TagsManager getTagsManager() {
        return mProvider.getTagsManager();
    }

    public ReviewViewAdapter<?> newReviewsListAdapter(ReviewNode node) {
        return ((ReviewView<?>)mLaunchableFactory.newReviewsListScreen(node, this)).getAdapter();
    }

    public <T extends GvData> ReviewViewAdapter<?> newReviewsListAdapter(T datum) {
        Review meta = mProvider.asMetaReview(datum, datum.getStringSummary());
        return newReviewsListAdapter(meta.getTreeRepresentation());
    }

    public <T extends GvData> ReviewViewAdapter<?> newFlattenedReviewsListAdapter(GvDataCollection<T> data) {
        Review meta = mProvider.getFlattenedMetaReview(data, data.getStringSummary());
        return newReviewsListAdapter(meta.getTreeRepresentation());
    }

    public ReviewViewAdapter<GvReviewOverview> newChildListAdapter(ReviewNode node) {
        GridDataViewer<GvReviewOverview> viewer;
        viewer = new ViewerChildList(node, mConverter.getConverterReviews(), this);
        return  newAdapterReviewNode(node, viewer);
    }

    public ReviewViewAdapter<GvData> newNodeDataAdapter(ReviewNode node) {
        GridDataViewer<GvData> viewer = mViewerFactory.newNodeDataViewer(node, mConverter,
                getTagsManager(), mVisitorFactory, mTraverserFactory, mAggregater);
        return newAdapterReviewNode(node, viewer);
    }

    public <T extends GvData> ReviewViewAdapter<?> newNodeDataAdapter(GvDataCollection<T> data) {
        return newNodeDataAdapter(mProvider.getMetaReview(data, data.getStringSummary()));
    }

    public <T extends GvData> ReviewViewAdapter<?> newDataToDataAdapter(ReviewNode parent,
                                                                     GvDataCollection<T> data) {
        return newAdapterReviewNode(parent, mViewerFactory.newDataToDataViewer(parent, data));
    }

    public <T extends GvData> ReviewViewAdapter<?> newAggregateToReviewsAdapter
    (GvCanonicalCollection<T> data, String subject) {
        GvDataType<T> type = data.getGvDataType();

        if (type.equals(GvComment.TYPE)) {
            ReviewNode node = mProvider.getMetaReview(data, subject);
            //TODO make type safe
            return new AdapterCommentsAggregate(node, mConverter.getConverterImages(),
                    (GvCanonicalCollection<GvComment>) data, mViewerFactory,
                    mAggregater);
        }

        GridDataViewer<GvCanonical> viewer;
        boolean aggregateToData = type.equals(GvCriterion.TYPE) ||
                type.equals(GvFact.TYPE) || type.equals(GvImage.TYPE);
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
        ReviewNode node = mProvider.getMetaReview(data, subject);
        return newAdapterReviewNode(node, viewer);
    }
}
