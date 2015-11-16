/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 14 May, 2015
 */

package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Factories;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.ConverterGv;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.Interfaces.ReviewViewAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Implementation.AdapterCommentsAggregate;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Implementation.AdapterReviewNode;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Interfaces
        .GridDataViewer;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Implementation.ViewerDataToReviews;
import com.chdryra.android.reviewer.Models.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Models.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Models.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.ReviewsProviderModel.Interfaces.ReviewsProvider;
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
import com.chdryra.android.reviewer.View.Screens.Builders.BuilderChildListScreen;
import com.chdryra.android.reviewer.View.Screens.GiLaunchReviewDataScreen;
import com.chdryra.android.reviewer.View.Screens.Interfaces.ReviewView;
import com.chdryra.android.reviewer.View.Screens.ReviewViewAction;

/**
 * Created by: Rizwan Choudrey
 * On: 14/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewViewAdapter {
    private BuilderChildListScreen mListScreenFactory;
    private FactoryGridDataViewer mViewerFactory;
    private GvDataAggregater mAggregater;
    private ConverterGv mConverter;
    private ReviewsProvider mProvider;

    //Constructors
    public FactoryReviewViewAdapter(BuilderChildListScreen listScreenFactory,
                                    FactoryGridDataViewer viewerFactory,
                                    GvDataAggregater aggregater,
                                    ReviewsProvider provider,
                                    ConverterGv converter) {
        mListScreenFactory = listScreenFactory;
        mViewerFactory = viewerFactory;
        mAggregater = aggregater;
        mProvider = provider;
        mConverter = converter;
    }

    //public methods
    private TagsManager getTagsManager() {
        return mProvider.getTagsManager();
    }

    public ReviewViewAdapter newReviewsListAdapter(ReviewNode node) {
        ReviewViewAction.GridItemAction gi = new GiLaunchReviewDataScreen();
        ReviewView view = mListScreenFactory.createView(node, mConverter.getConverterReviews(),
                mConverter.getConverterImages(), this, gi, null);
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

    public ReviewViewAdapter newNodeDataAdapter(ReviewNode node) {
        GridDataViewer<GvData> viewer = mViewerFactory.newNodeDataViewer(node, mConverter,
                getTagsManager(), this, mAggregater);
        return newAdapterReviewNode(node, viewer);
    }

    public <T extends GvData> ReviewViewAdapter newNodeDataAdapter(GvDataCollection<T> data) {
        Review meta = mProvider.createMetaReview(data, data.getStringSummary());
        return newNodeDataAdapter(meta.getTreeRepresentation());
    }

    public <T extends GvData> ReviewViewAdapter newDataToDataAdapter(ReviewNode parent,
                                                                     GvDataCollection<T> data) {
        return newAdapterReviewNode(parent, mViewerFactory.newDataToDataViewer(parent, data, this));
    }

    //TODO make type safe
    public <T extends GvData> ReviewViewAdapter newAggregateToReviewsAdapter
    (GvCanonicalCollection<T> data, String subject) {
        GvDataType<T> type = data.getGvDataType();

        if (type.equals(GvCommentList.GvComment.TYPE)) {
            ReviewNode node = mProvider.createMetaReview(data, subject).getTreeRepresentation();
            return new AdapterCommentsAggregate(node, mConverter.getConverterImages(),
                    (GvCanonicalCollection<GvCommentList.GvComment>) data, mViewerFactory, this,
                    mAggregater);
        }

        GridDataViewer<GvCanonical> viewer;
        boolean aggregateToData = type.equals(GvCriterionList.GvCriterion.TYPE) ||
                type.equals(GvFactList.GvFact.TYPE) || type.equals(GvImageList.GvImage.TYPE);
        if (aggregateToData) {
            viewer = mViewerFactory.newAggregateToDataViewer(data, this, mAggregater);
        } else {
            viewer = mViewerFactory.newDataToReviewsViewer(data, this);
        }

        return newMetaReviewAdapter(data, subject, viewer);
    }

    public <T extends GvData> ReviewViewAdapter newDataToReviewsAdapter(GvDataCollection<T> data,
                                                                        String subject) {
        ViewerDataToReviews<T> viewer = new ViewerDataToReviews<>(data, this);
        return newMetaReviewAdapter(data, subject, viewer);
    }

    private <T extends GvData> ReviewViewAdapter newAdapterReviewNode(ReviewNode node,
                                                                      GridDataViewer<T> viewer) {
        return new AdapterReviewNode<>(node, mConverter.getConverterImages(), viewer);
    }

    private <T extends GvData> ReviewViewAdapter newMetaReviewAdapter(GvDataCollection<T> data,
                                                                      String subject,
                                                                      GridDataViewer<T> viewer) {
        ReviewNode node = mProvider.createMetaReview(data, subject).getTreeRepresentation();
        return newAdapterReviewNode(node, viewer);
    }
}
