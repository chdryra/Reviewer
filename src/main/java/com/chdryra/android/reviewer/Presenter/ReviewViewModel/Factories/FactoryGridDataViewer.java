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
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataCollection;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.ConverterGv;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvAuthor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCanonical;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCanonicalCollection;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCriterion;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataAggregator;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDate;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSubject;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.GridDataWrapper;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ViewerAggregateCriteria;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ViewerAggregateToData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ViewerAggregateToReviews;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ViewerDataToReviews;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ViewerTreeData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ViewerReviewData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ViewerReviewSummary;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ViewerTreeSummary;

/**
 * Created by: Rizwan Choudrey
 * On: 05/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryGridDataViewer {
    private FactoryReviewViewAdapter mAdapterFactory;

    public FactoryGridDataViewer(FactoryReviewViewAdapter adapterFactory) {
        mAdapterFactory = adapterFactory;
    }

    public GridDataWrapper<?> newDataSummaryViewer(ReviewNode node, ConverterGv converter) {
        GridDataWrapper<?> viewer;
        IdableList<ReviewNode> children = node.getChildren();
        if (children.size() > 1) {
            viewer = new ViewerTreeSummary(node, mAdapterFactory, converter);
        } else {
            node = children.size() == 0 ? node : children.getItem(0);
            viewer = new ViewerReviewSummary(node, mAdapterFactory, converter);
        }

        return viewer;
    }

    @Nullable
    public GridDataWrapper<?> newReviewDataViewer(ReviewNode node,
                                                  GvDataType<?> dataType,
                                                  ConverterGv converter) {
        GridDataWrapper<?> viewer = null;
        if (dataType.equals(GvTag.TYPE)) {
            viewer = new ViewerReviewData<>(node.getTags(), converter.newConverterTags()
                    .getReferencesConverter());
        } else if (dataType.equals(GvCriterion.TYPE)) {
            viewer = new ViewerReviewData<>(node.getCriteria(), converter.newConverterCriteria()
                    .getReferencesConverter());
        } else if (dataType.equals(GvImage.TYPE)) {
            viewer = new ViewerReviewData<>(node.getImages(), converter.newConverterImages()
                    .getReferencesConverter());
        } else if (dataType.equals(GvComment.TYPE)) {
            viewer = new ViewerReviewData<>(node.getComments(), converter.newConverterComments()
                    .getReferencesConverter());
        } else if (dataType.equals(GvLocation.TYPE)) {
            viewer = new ViewerReviewData<>(node.getLocations(), converter.newConverterLocations
                    ().getReferencesConverter());
        } else if (dataType.equals(GvFact.TYPE)) {
            viewer = new ViewerReviewData<>(node.getFacts(), converter.newConverterFacts()
                    .getReferencesConverter());
        }

        return viewer;
    }

    @Nullable
    public GridDataWrapper<?> newTreeDataViewer(ReviewNode node,
                                                GvDataType<?> dataType,
                                                ConverterGv converter) {
        GridDataWrapper<?> viewer = null;
        if (dataType.equals(GvTag.TYPE)) {
            viewer = new ViewerTreeData<>(node.getTags(), converter.newConverterTags()
                    .getReferencesConverter(), mAdapterFactory);
        } else if (dataType.equals(GvCriterion.TYPE)) {
            viewer = new ViewerTreeData<>(node.getCriteria(), converter.newConverterCriteria()
                    .getReferencesConverter(), mAdapterFactory);
        } else if (dataType.equals(GvImage.TYPE)) {
            viewer = new ViewerTreeData<>(node.getImages(), converter.newConverterImages()
                    .getReferencesConverter(), mAdapterFactory);
        } else if (dataType.equals(GvComment.TYPE)) {
            viewer = new ViewerTreeData<>(node.getComments(), converter.newConverterComments()
                    .getReferencesConverter(), mAdapterFactory);
        } else if (dataType.equals(GvLocation.TYPE)) {
            viewer = new ViewerTreeData<>(node.getLocations(), converter.newConverterLocations
                    ().getReferencesConverter(), mAdapterFactory);
        } else if (dataType.equals(GvFact.TYPE)) {
            viewer = new ViewerTreeData<>(node.getFacts(), converter.newConverterFacts()
                    .getReferencesConverter(), mAdapterFactory);
        } else if (dataType.equals(GvAuthor.TYPE)) {
        viewer = new ViewerTreeData<>(node.getAuthorIds(), converter.newConverterAuthorsIds()
                    .getReferencesConverter(), mAdapterFactory);
        } else if (dataType.equals(GvSubject.TYPE)) {
            viewer = new ViewerTreeData<>(node.getSubjects(), converter.newConverterSubjects()
                    .getReferencesConverter(), mAdapterFactory);
        } else if (dataType.equals(GvDate.TYPE)) {
            viewer = new ViewerTreeData<>(node.getDates(), converter.newConverterDateReviews()
                    .getReferencesConverter(), mAdapterFactory);
        }

        return viewer;
    }
//
//    public <T extends GvData> GridDataWrapper<T> newDataToDataViewer(ReviewNode parent,
//                                                                    GvDataType<T> dataType) {
//        return new ViewerMetaDataToData<>(parent, dataType, mAdapterFactory);
//    }

    public <T extends GvData> GridDataWrapper<GvCanonical> newAggregateToDataViewer
            (GvCanonicalCollection<T> data,
                                                                                    GvDataAggregator aggregateFactory) {
        GridDataWrapper<GvCanonical> viewer;
        if (data.getGvDataType().equals(GvCriterion.TYPE)) {
            viewer = new ViewerAggregateCriteria((GvCanonicalCollection<GvCriterion>) data,
                    this, mAdapterFactory, aggregateFactory);
        } else {
            viewer = new ViewerAggregateToData<>(data, this, mAdapterFactory);
        }

        return viewer;
    }

    public <T extends GvData> GridDataWrapper<T> newDataToReviewsViewer(GvDataCollection<T> data) {
        return new ViewerDataToReviews<>(data, mAdapterFactory);
    }

    public <T extends GvData> GridDataWrapper<GvCanonical> newAggregateToReviewsViewer
            (GvCanonicalCollection<T> data) {
        return new ViewerAggregateToReviews<>(data, mAdapterFactory);
    }
}
