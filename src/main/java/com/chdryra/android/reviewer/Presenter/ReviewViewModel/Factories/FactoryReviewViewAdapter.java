/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.Model.Factories.FactoryNodeTraverser;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.Factories.FactoryVisitorReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces.ReviewsSource;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataCollection;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.GridDataViewer;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters
        .ConverterGv;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCanonical;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCanonicalCollection;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCriterion;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvDataAggregator;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvDataListImpl;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvReviewOverview;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View
        .AdapterAsyncWrapper;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View
        .AdapterCommentsAggregate;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.AdapterReviewNode;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View
        .AdapterReviewNodeAsync;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewNodeAsync;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ViewerChildList;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 14/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewViewAdapter {
    private final ReviewNode mAsyncNodeInitial;
    private FactoryReviews mReviewsFactory;
    private FactoryReviewViewLaunchable mLaunchableFactory;
    private FactoryGridDataViewer mViewerFactory;
    private FactoryVisitorReviewNode mVisitorFactory;
    private FactoryNodeTraverser mTraverserFactory;
    private GvDataAggregator mAggregator;
    private ConverterGv mConverter;
    private ReviewsSource mReviewSource;

    public FactoryReviewViewAdapter(FactoryReviewViewLaunchable launchableFactory,
                                    FactoryReviews reviewsFactory,
                                    FactoryVisitorReviewNode visitorFactory,
                                    FactoryNodeTraverser traverserFactory,
                                    GvDataAggregator aggregator,
                                    ReviewsSource reviewsSource,
                                    ConverterGv converter) {
        mReviewsFactory = reviewsFactory;
        mViewerFactory = new FactoryGridDataViewer(this);
        mLaunchableFactory = launchableFactory;
        mAggregator = aggregator;
        mReviewSource = reviewsSource;
        mConverter = converter;
        mVisitorFactory = visitorFactory;
        mTraverserFactory = traverserFactory;

        mAsyncNodeInitial = mReviewsFactory.createUserReview("Fetching...", 0f, new ArrayList
                        <DataComment>(), new ArrayList<DataImage>(), new ArrayList<DataFact>(),
                new ArrayList<DataLocation>(), new ArrayList<Review>(), true)
                .getTreeRepresentation();
    }

    public ReviewViewAdapter<?> newReviewsListAdapter(ReviewNode node) {
        return ((ReviewView<?>) mLaunchableFactory.newReviewsListScreen(node, this)).getAdapter();
    }

    public <T extends GvData> ReviewViewAdapter<?> newReviewsListAdapter(T datum) {
        ReviewNodeAsync node = newAsyncNode();
        mReviewSource.asMetaReview(datum, datum.getStringSummary(), node);
        return newAsyncReviewsListAdapter(node);
    }

    public <T extends GvData> ReviewViewAdapter<?> newReviewsListAdapter(GvDataCollection<T> data) {
        ReviewNodeAsync node = newAsyncNode();
        mReviewSource.getMetaReview(data, data.getStringSummary(), node);
        return newAsyncReviewsListAdapter(node);
    }

    public <T extends GvData> ReviewViewAdapter<?> newFlattenedReviewsListAdapter
            (GvCanonicalCollection<T> data) {
        ReviewNodeAsync asyncNode = getFlattenedMetaReview(data, data.getStringSummary());
        return newAsyncReviewsListAdapter(asyncNode);
    }

    public ReviewViewAdapter<GvReviewOverview> newChildListAdapter(ReviewNode node) {
        GridDataViewer<GvReviewOverview> viewer;
        viewer = new ViewerChildList(node, mConverter.getConverterReviews(), this);
        return newSyncNodeAdapter(node, viewer);
    }

    public ReviewViewAdapter<GvData> newNodeDataAdapter(ReviewNode node) {
        GridDataViewer<GvData> viewer = mViewerFactory.newNodeDataViewer(node, mConverter,
                mReviewSource.getTagsManager(), mVisitorFactory, mTraverserFactory, mAggregator);
        return newSyncNodeAdapter(node, viewer);
    }

    public <T extends GvData> ReviewViewAdapter<?> newNodeDataAdapter(GvDataCollection<T> data) {
        ReviewNodeAsync node = newAsyncNode();
        mReviewSource.getMetaReview(data, data.getStringSummary(), node);
        GridDataViewer<GvData> viewer = mViewerFactory.newNodeDataViewer(node, mConverter,
                mReviewSource.getTagsManager(), mVisitorFactory, mTraverserFactory, mAggregator);
        return newAsyncNodeAdapter(node, viewer);
    }

    public <T extends GvData> ReviewViewAdapter<?> newDataToDataAdapter(ReviewNode parent,
                                                                        GvDataCollection<T> data) {
        return newSyncNodeAdapter(parent, mViewerFactory.newDataToDataViewer(parent, data));
    }

    public <T extends GvData> ReviewViewAdapter<?> newAggregateToReviewsAdapter
            (GvCanonicalCollection<T> data, String subject) {
        GvDataType<T> type = data.getGvDataType();

        if (type.equals(GvComment.TYPE)) {
            ReviewNodeAsync node = getFlattenedMetaReview(data, subject);
            //TODO make type safe
            return new AdapterCommentsAggregate(node, mConverter.getConverterImages(),
                    (GvCanonicalCollection<GvComment>) data, mViewerFactory,
                    mAggregator);
        }

        GridDataViewer<GvCanonical> viewer;
        boolean aggregateToData = type.equals(GvCriterion.TYPE) ||
                type.equals(GvFact.TYPE) || type.equals(GvImage.TYPE);
        if (aggregateToData) {
            viewer = mViewerFactory.newAggregateToDataViewer(data, mAggregator);
        } else {
            viewer = mViewerFactory.newAggregateToReviewsViewer(data);
        }

        return newAggregatedMetaReviewAdapter(data, subject, viewer);
    }

    public <T extends GvData> ReviewViewAdapter<T> newDataToReviewsAdapter(GvDataCollection<T> data,
                                                                           String subject) {
        GridDataViewer<T> viewer = mViewerFactory.newDataToReviewsViewer(data);
        return newMetaReviewAdapter(data, subject, viewer);
    }

    private ReviewViewAdapter<?> newAsyncReviewsListAdapter(ReviewNodeAsync node) {
        ReviewViewAdapter<? extends GvData> adapter = ((ReviewView<?>) mLaunchableFactory
                .newReviewsListScreen(node, this)).getAdapter();
        return new AdapterAsyncWrapper<>(node, adapter);
    }

    private <T extends GvData> ReviewViewAdapter<T> newAsyncNodeAdapter(ReviewNodeAsync node,
                                                                        GridDataViewer<T> viewer) {
        return new AdapterReviewNodeAsync<>(node, mConverter.getConverterImages(), viewer);
    }

    private <T extends GvData> ReviewViewAdapter<T> newSyncNodeAdapter(ReviewNode node,
                                                                       GridDataViewer<T> viewer) {
        return new AdapterReviewNode<>(node, mConverter.getConverterImages(), viewer);
    }

//    private <T extends GvData> ReviewViewAdapter<T> newReviewNodeAdapter(ReviewNode node,
//                                                                         GridDataViewer<T>
// viewer) {
//        return new AdapterReviewNode<>(node, mConverter.getConverterImages(), viewer);
//    }

    private <T extends GvData> ReviewViewAdapter<T> newMetaReviewAdapter(GvDataCollection<T> data,
                                                                         String subject,
                                                                         GridDataViewer<T> viewer) {
        ReviewNodeAsync node = newAsyncNode();
        mReviewSource.getMetaReview(data, subject, node);
        return newAsyncNodeAdapter(node, viewer);
    }

    private <T extends GvData> ReviewViewAdapter<?> newAggregatedMetaReviewAdapter
            (GvCanonicalCollection<T> data, String subject, GridDataViewer<GvCanonical> viewer) {

        ReviewNodeAsync node = getFlattenedMetaReview(data, subject);
        return data.size() == 1 ? newAsyncReviewsListAdapter(node) : newAsyncNodeAdapter(node, viewer);
    }

    private <T extends GvData> ReviewNodeAsync getFlattenedMetaReview(GvCanonicalCollection<T> data,
                                                                      String subject) {
        GvDataType<T> gvDataType = data.getGvDataType();
        GvDataListImpl<T> allData = new GvDataListImpl<>(gvDataType, data.getGvReviewId());
        for (GvCanonical<T> canonical : data) {
            allData.addAll(canonical.toList());
        }

        ReviewNodeAsync asyncNode = newAsyncNode();
        mReviewSource.getMetaReview(allData, subject, asyncNode);

        return asyncNode;
    }

    @NonNull
    private ReviewNodeAsync newAsyncNode() {
        return new ReviewNodeAsync(mAsyncNodeInitial);
    }
}
