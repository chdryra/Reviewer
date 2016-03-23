/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories;

import com.chdryra.android.reviewer.Model.Factories.FactoryNodeTraverser;
import com.chdryra.android.reviewer.Model.Factories.FactoryVisitorReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces.ReviewsSource;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataCollection;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.GridDataViewer;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
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
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReviewOverview;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.AdapterCommentsAggregate;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.AdapterReviewNode;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ViewerChildList;

/**
 * Created by: Rizwan Choudrey
 * On: 14/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewViewAdapter {
    private FactoryReviewViewLaunchable mLaunchableFactory;
    private FactoryGridDataViewer mViewerFactory;
    private FactoryVisitorReviewNode mVisitorFactory;
    private FactoryNodeTraverser mTraverserFactory;
    private GvDataAggregator mAggregator;
    private ConverterGv mConverter;
    private ReviewsSource mReviewSource;

    public FactoryReviewViewAdapter(FactoryReviewViewLaunchable launchableFactory,
                                    FactoryVisitorReviewNode visitorFactory,
                                    FactoryNodeTraverser traverserFactory,
                                    GvDataAggregator aggregator,
                                    ReviewsSource reviewsSource,
                                    ConverterGv converter) {
        mViewerFactory = new FactoryGridDataViewer(this);
        mLaunchableFactory = launchableFactory;
        mAggregator = aggregator;
        mReviewSource = reviewsSource;
        mConverter = converter;
        mVisitorFactory = visitorFactory;
        mTraverserFactory = traverserFactory;
    }

    public ReviewViewAdapter<?> newReviewsListAdapter(ReviewNode node) {
        return ((ReviewView<?>)mLaunchableFactory.newReviewsListScreen(node, this)).getAdapter();
    }

    public <T extends GvData> ReviewViewAdapter<?> newReviewsListAdapter(T datum) {
        ReviewNode meta = mReviewSource.asMetaReview(datum, datum.getStringSummary());
        return newReviewsListAdapter(meta);
    }

    public <T extends GvData> ReviewViewAdapter<?> newReviewsListAdapter(GvDataCollection<T> data) {
        ReviewNode meta = mReviewSource.getMetaReview(data, data.getStringSummary());
        return newReviewsListAdapter(meta);
    }

    public <T extends GvData> ReviewViewAdapter<?> newFlattenedReviewsListAdapter(GvCanonicalCollection<T> data) {
        return newReviewsListAdapter(getFlattenedMetaReview(data, data.getStringSummary()));
    }

    public ReviewViewAdapter<GvReviewOverview> newChildListAdapter(ReviewNode node) {
        GridDataViewer<GvReviewOverview> viewer;
        viewer = new ViewerChildList(node, mConverter.getConverterReviews(), this);
        return  newAdapterReviewNode(node, viewer);
    }

    public ReviewViewAdapter<GvData> newNodeDataAdapter(ReviewNode node) {
        GridDataViewer<GvData> viewer = mViewerFactory.newNodeDataViewer(node, mConverter,
                mReviewSource.getTagsManager(), mVisitorFactory, mTraverserFactory, mAggregator);
        return newAdapterReviewNode(node, viewer);
    }

    public <T extends GvData> ReviewViewAdapter<?> newNodeDataAdapter(GvDataCollection<T> data) {
        return newNodeDataAdapter(mReviewSource.getMetaReview(data, data.getStringSummary()));
    }

    public <T extends GvData> ReviewViewAdapter<?> newDataToDataAdapter(ReviewNode parent,
                                                                     GvDataCollection<T> data) {
        return newAdapterReviewNode(parent, mViewerFactory.newDataToDataViewer(parent, data));
    }

    public <T extends GvData> ReviewViewAdapter<?> newAggregateToReviewsAdapter
    (GvCanonicalCollection<T> data, String subject) {
        GvDataType<T> type = data.getGvDataType();

        if (type.equals(GvComment.TYPE)) {
            ReviewNode node = getFlattenedMetaReview(data, subject);
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

    private <T extends GvData> ReviewViewAdapter<T> newAdapterReviewNode(ReviewNode node,
                                                                      GridDataViewer<T> viewer) {
        return new AdapterReviewNode<>(node, mConverter.getConverterImages(), viewer);
    }

    private <T extends GvData> ReviewViewAdapter<T> newMetaReviewAdapter(GvDataCollection<T> data,
                                                                      String subject,
                                                                      GridDataViewer<T> viewer) {
        ReviewNode node = mReviewSource.getMetaReview(data, subject);
        return newAdapterReviewNode(node, viewer);
    }

    private <T extends GvData> ReviewViewAdapter<?> newAggregatedMetaReviewAdapter(GvCanonicalCollection<T> data,
                                                                                   String subject,
                                                                                   GridDataViewer<GvCanonical> viewer) {

        ReviewNode node = getFlattenedMetaReview(data, subject);
        return data.size() == 1 ? newReviewsListAdapter(node) : newAdapterReviewNode(node, viewer);
    }

    private <T extends GvData> ReviewNode getFlattenedMetaReview(GvCanonicalCollection<T> data,
                                                                 String subject) {
        GvDataType<T> gvDataType = data.getGvDataType();
        GvDataListImpl<T> allData = new GvDataListImpl<>(gvDataType, data.getGvReviewId());
        for(GvCanonical<T> canonical : data) {
            allData.addAll(canonical.toList());
        }
        return mReviewSource.getMetaReview(allData, subject);
    }
}
