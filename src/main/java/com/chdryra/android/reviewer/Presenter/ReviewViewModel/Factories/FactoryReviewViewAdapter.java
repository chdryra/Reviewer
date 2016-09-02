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

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.References.Factories.FactoryReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNodeComponent;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepository;
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
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvNode;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.AdapterComments;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.AdapterCommentsAggregate;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.AdapterReviewNode;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.GridDataWrapper;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewTreeFlat;
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
    private AuthorsRepository mAuthorsRepository;
    private ReviewsSource mReviewSource;

    public FactoryReviewViewAdapter(FactoryReviewView reviewViewFactory,
                                    FactoryReviews reviewsFactory,
                                    FactoryReference referenceFactory,
                                    GvDataAggregator aggregator,
                                    ReviewsSource reviewsSource,
                                    AuthorsRepository authorsRepository,
                                    ConverterGv converter) {
        mAuthorsRepository = authorsRepository;
        mReviewViewFactory = reviewViewFactory;
        mReviewsFactory = reviewsFactory;
        mAggregator = aggregator;
        mReviewSource = reviewsSource;
        mConverter = converter;
        mViewerFactory = new FactoryGridDataViewer(this, referenceFactory, mAuthorsRepository);
    }

    public ReviewViewAdapter<?> newReviewsListAdapter(ReviewNode node) {
        //adapter shows children nodes
        return mReviewViewFactory.newReviewsListAdapter(node, this);
    }

    public ReviewViewAdapter<?> newFlattenedReviewsListAdapter(ReviewNode toFlatten) {
        //adapter shows list of all reviews
        return mReviewViewFactory.newReviewsListAdapter(newFlattenedTree(toFlatten), this);
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

    public ReviewViewAdapter<GvNode> newChildListAdapter(ReviewNode node) {
        GridDataWrapper<GvNode> viewer
                = new ViewerChildList(node, mConverter.newConverterNodes(mAuthorsRepository), this);
        return newNodeAdapter(node, viewer);
    }

    public ReviewViewAdapter<?> newNodeDataAdapter(ReviewNode node) {
        GridDataWrapper<?> viewer = mViewerFactory.newDataSummaryViewer(node, mConverter);
        return newNodeAdapter(node, viewer);
    }

    public <T extends GvData> ReviewViewAdapter<?> newNodeDataAdapter(GvDataCollection<T> data) {
        ReviewTreeSourceCallback node = newAsyncNode();
        mReviewSource.getMetaReview(data, data.getStringSummary(), node);
        GridDataWrapper<?> viewer = mViewerFactory.newDataSummaryViewer(node, mConverter);
        return newNodeAdapter(node, viewer);
    }
//
//    public <T extends GvData> ReviewViewAdapter<?> newDataToDataAdapter(ReviewNode parent,
//                                                                        GvDataType<T> data) {
//        return newNodeAdapter(parent, mViewerFactory.newDataToDataViewer(parent, data));
//    }

    public <T extends GvData> ReviewViewAdapter<?> newAggregateToReviewsAdapter
            (GvCanonicalCollection<T> data, String subject) {
        GvDataType<T> type = data.getGvDataType();

        if (type.equals(GvComment.TYPE)) {
            ReviewTreeSourceCallback node = getFlattenedMetaReview(data, subject);
            //TODO make type safe
            return new AdapterCommentsAggregate(node, mConverter.newConverterImages(),
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
    public <T extends GvData> ReviewViewAdapter<?> newDataAdapter(ReviewNode node,
                                                                  GvDataType<T> dataType) {
        if (dataType == GvComment.TYPE) {
            return new AdapterComments(node, mConverter.newConverterImages(), mViewerFactory.newReviewCommentsViewer(node, mConverter));
        } else {
            return newNodeAdapter(node, mViewerFactory.newReviewDataViewer(node, dataType, mConverter));
        }
    }

    @Nullable
    public <T extends GvData> ReviewViewAdapter<?> newTreeDataAdapter(ReviewNode node,
                                                                      GvDataType<T> dataType) {

        if (dataType == GvComment.TYPE) {
            return new AdapterComments(node, mConverter.newConverterImages(), mViewerFactory.newTreeCommentsViewer(node, mConverter));
        } else {
            return newNodeAdapter(node, mViewerFactory.newTreeDataViewer(node, dataType, mConverter));
        }
    }

    //Private
    @Nullable
    private <T extends GvData> ReviewViewAdapter<T> newNodeAdapter(ReviewNode node,
                                                                   @Nullable GridDataWrapper<T> viewer) {
        return viewer != null ? new AdapterReviewNode<>(node, mConverter.newConverterImages(), viewer) : null;
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
            (GvCanonicalCollection<T> data, String subject) {
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
        return new ReviewTreeSourceCallback(getFetchingPlaceholder());
    }

    private ReviewNode newFlattenedTree(ReviewNode node) {
        return new ReviewTreeFlat(node, mReviewsFactory);
    }

    private ReviewNodeComponent getFetchingPlaceholder() {
        Review fetching = mReviewsFactory.createUserReview("Fetching...", 0f,
                new ArrayList<DataCriterion>(), new ArrayList<DataComment>(),
                new ArrayList<DataImage>(), new ArrayList<DataFact>(),
                new ArrayList<DataLocation>(), true);

        return mReviewsFactory.createLeafNode(mReviewsFactory.asReference(fetching, mReviewSource.getTagsManager()));
    }
}
