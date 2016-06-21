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

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.ReferenceWrapper;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsSource;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataCollection;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.ConverterGv;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCanonical;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCanonicalCollection;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCriterion;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataAggregator;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataListImpl;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReview;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.AdapterCommentsAggregate;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.AdapterReviewNode;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.AdapterReviewReference;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.GridDataWrapper;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewTreeSourceCallback;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ViewerChildList;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 14/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewViewAdapter {
    private FactoryReviewView mReviewViewFactory;
    private FactoryReviews mReviewsFactory;
    private FactoryGridDataViewer mViewerFactory;
    private GvDataAggregator mAggregator;
    private ConverterGv mConverter;
    private ReviewsSource mReviewSource;

    public FactoryReviewViewAdapter(FactoryReviewView reviewViewFactory,
                                    FactoryReviews reviewsFactory,
                                    GvDataAggregator aggregator,
                                    ReviewsSource reviewsSource,
                                    ConverterGv converter) {
        mViewerFactory = new FactoryGridDataViewer(this);
        mReviewViewFactory = reviewViewFactory;
        mReviewsFactory = reviewsFactory;
        mAggregator = aggregator;
        mReviewSource = reviewsSource;
        mConverter = converter;
    }

    public ReviewViewAdapter<?> newReviewsListAdapter(ReviewNode node) {
        return mReviewViewFactory.newReviewsListAdapter(node, this);
    }

    public <T extends GvData> ReviewViewAdapter<?> newReviewsListAdapter(T datum) {
        ReviewTreeSourceCallback node = newAsyncNode();
        mReviewSource.asMetaReview(datum, datum.getStringSummary(), node);
        return newReviewsListAdapter(node);
    }

    public <T extends GvData> ReviewViewAdapter<?> newReviewsListAdapter(GvDataCollection<T> data) {
        ReviewTreeSourceCallback node = newAsyncNode();
        mReviewSource.getMetaReview(data, data.getStringSummary(), node);
        return newReviewsListAdapter(node);
    }

    public <T extends GvData> ReviewViewAdapter<?> newFlattenedReviewsListAdapter
            (GvCanonicalCollection<T> data) {
        ReviewNode node = getFlattenedMetaReview(data, data.getStringSummary());
        return newReviewsListAdapter(node);
    }

    public ReviewViewAdapter<GvReview> newChildListAdapter(ReviewNode node) {
        GridDataWrapper<GvReview> viewer
                = new ViewerChildList(node, mConverter.getConverterReviews(), this);
        return newNodeAdapter(node, viewer);
    }

    public ReviewViewAdapter<GvData> newNodeDataAdapter(ReviewNode node) {
        GridDataWrapper<GvData> viewer = mViewerFactory.newNodeDataViewer(node, mConverter,
                mReviewSource.getTagsManager(), mAggregator);
        return newNodeAdapter(node, viewer);
    }

    public <T extends GvData> ReviewViewAdapter<?> newNodeDataAdapter(GvDataCollection<T> data) {
        ReviewTreeSourceCallback node = newAsyncNode();
        mReviewSource.getMetaReview(data, data.getStringSummary(), node);
        GridDataWrapper<GvData> viewer = mViewerFactory.newNodeDataViewer(node, mConverter,
                mReviewSource.getTagsManager(), mAggregator);
        return newNodeAdapter(node, viewer);
    }

    public <T extends GvData> ReviewViewAdapter<?> newDataToDataAdapter(ReviewNode parent,
                                                                        GvDataCollection<T> data) {
        return newNodeAdapter(parent, mViewerFactory.newDataToDataViewer(parent, data));
    }

    public <T extends GvData> ReviewViewAdapter<?> newAggregateToReviewsAdapter
            (GvCanonicalCollection<T> data, String subject) {
        GvDataType<T> type = data.getGvDataType();

        if (type.equals(GvComment.TYPE)) {
            ReviewTreeSourceCallback node = getFlattenedMetaReview(data, subject);
            //TODO make type safe
            return new AdapterCommentsAggregate(node, mConverter.getConverterImages(),
                    (GvCanonicalCollection<GvComment>) data, mViewerFactory, mAggregator);
        }

        GridDataWrapper<GvCanonical> viewer;
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
        GridDataWrapper<T> viewer = mViewerFactory.newDataToReviewsViewer(data);
        return newMetaReviewAdapter(data, subject, viewer);
    }

    @Nullable
    public <T extends GvData> ReviewViewAdapter<?> newDataAdapter(ReferenceWrapper reference,
                                                                  GvDataType<T> gvDataType,
                                                                  ConverterGv converter) {
        GridDataWrapper<T> viewer = mViewerFactory.newDataViewer(reference, gvDataType, converter);
        return viewer != null ? new AdapterReviewReference<>(reference, converter.getConverterImages(), viewer) : null;
    }

    //Private
    private <T extends GvData> ReviewViewAdapter<T> newNodeAdapter(ReviewNode node,
                                                                   GridDataWrapper<T> viewer) {
        return new AdapterReviewNode<>(node, mConverter.getConverterImages(), viewer);
    }

    private <T extends GvData> ReviewViewAdapter<T> newMetaReviewAdapter(GvDataCollection<T> data,
                                                                         String subject,
                                                                         GridDataWrapper<T> viewer) {
        ReviewTreeSourceCallback node = newAsyncNode();
        mReviewSource.getMetaReview(data, subject, node);
        return newNodeAdapter(node, viewer);
    }

    private <T extends GvData> ReviewViewAdapter<?> newAggregatedMetaReviewAdapter
            (GvCanonicalCollection<T> data, String subject, GridDataWrapper<GvCanonical> viewer) {

        ReviewTreeSourceCallback node = getFlattenedMetaReview(data, subject);
        return data.size() == 1 ? newReviewsListAdapter(node) : newNodeAdapter(node, viewer);
    }

    private <T extends GvData> ReviewTreeSourceCallback getFlattenedMetaReview
            (GvCanonicalCollection<T> data,
                                                                               String subject) {
        GvDataType<T> gvDataType = data.getGvDataType();
        GvDataListImpl<T> allData = new GvDataListImpl<>(gvDataType, data.getGvReviewId());
        for (GvCanonical<T> canonical : data) {
            allData.addAll(canonical.toList());
        }

        ReviewTreeSourceCallback asyncNode = newAsyncNode();
        mReviewSource.getMetaReview(allData, subject, asyncNode);

        return asyncNode;
    }

    @NonNull
    private ReviewTreeSourceCallback newAsyncNode() {
        ReviewNode fetching = mReviewsFactory.createUserReview("Fetching...", 0f,
                new ArrayList<DataCriterion>(), new ArrayList
                        <DataComment>(), new ArrayList<DataImage>(), new ArrayList<DataFact>(),
                new ArrayList<DataLocation>(), true)
                .getTreeRepresentation();
        return new ReviewTreeSourceCallback(fetching);
    }
}
