/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryBinders;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.ReferenceBinder;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataCollection;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.GridDataViewer;
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
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.GridDataWrapper;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View
        .ViewerAggregateCriteria;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View
        .ViewerAggregateToData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View
        .ViewerAggregateToReviews;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ViewerData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ViewerDataToData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View
        .ViewerDataToReviews;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ViewerReviewData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ViewerTreeData;

/**
 * Created by: Rizwan Choudrey
 * On: 05/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryGridDataViewer {
    private FactoryReviewViewAdapter mAdapterFactory;
    private FactoryBinders mBindersFactory;

    public FactoryGridDataViewer(FactoryReviewViewAdapter adapterFactory, FactoryBinders bindersFactory) {
        mAdapterFactory = adapterFactory;
        mBindersFactory = bindersFactory;
    }

    public GridDataWrapper<?> newNodeDataViewer(ReviewNode node,
                                                     ConverterGv converter,
                                                     TagsManager tagsManager,
                                                     GvDataAggregator aggregateFactory) {
        GridDataWrapper<?> viewer;
        IdableList<ReviewNode> children = node.getChildren();
        if (children.size() > 1) {
            //aggregate children into meta review
            viewer = new ViewerTreeData(node, converter, tagsManager, mAdapterFactory,
                    aggregateFactory);
        } else {
            ReviewNode toView = children.size() == 0 ? node : children.getItem(0);
            ReferenceBinder binder = mBindersFactory.bindTo(toView);
            viewer = new ViewerReviewData(binder, converter, tagsManager, mAdapterFactory);
        }

        return viewer;
    }

    @Nullable
    public <T extends GvData> GridDataWrapper<T> newDataViewer(ReviewReference review,
                                                               GvDataType<T> type,
                                                               ConverterGv converter) {
        GridDataViewer viewer = null;
        if(type.equals(GvTag.TYPE)) {
            viewer = new ViewerData.Tags(review, converter.getConverterTags());
        } else if(type.equals(GvCriterion.TYPE)) {
            viewer = new ViewerData.Criteria(review, converter.getConverterCriteria());
        } else if(type.equals(GvImage.TYPE)) {
            viewer = new ViewerData.Images(review, converter.getConverterImages());
        } else if(type.equals(GvComment.TYPE)) {
            viewer = new ViewerData.Comments(review, converter.getConverterComments());
        } else if(type.equals(GvLocation.TYPE)) {
            viewer = new ViewerData.Locations(review, converter.getConverterLocations());
        } else if(type.equals(GvFact.TYPE)) {
            viewer = new ViewerData.Facts(review, converter.getConverterFacts());
        }

        return (GridDataWrapper<T>) viewer;
    }

    public <T extends GvData> GridDataWrapper<T> newDataToDataViewer(ReviewNode parent,
                                                                    GvDataType<T> dataType) {
        return new ViewerDataToData<>(parent, dataType, mAdapterFactory);
    }

    public <T extends GvData> GridDataWrapper<GvCanonical> newAggregateToDataViewer(GvCanonicalCollection<T> data,
                                                                                   GvDataAggregator aggregateFactory) {
        GridDataWrapper<GvCanonical> viewer;
        if (data.getGvDataType().equals(GvCriterion.TYPE)) {
            viewer = new ViewerAggregateCriteria( (GvCanonicalCollection<GvCriterion>) data,
                    this, mAdapterFactory, aggregateFactory);
        } else {
            viewer = new ViewerAggregateToData<>(data, this, mAdapterFactory);
        }

        return viewer;
    }

    public <T extends GvData> GridDataWrapper<T> newDataToReviewsViewer(GvDataCollection<T> data) {
        return new ViewerDataToReviews<>(data, mAdapterFactory);
    }

    public <T extends GvData> GridDataWrapper<GvCanonical> newAggregateToReviewsViewer(GvCanonicalCollection<T> data) {
        return new ViewerAggregateToReviews<>(data, mAdapterFactory);
    }
}
