/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories;

import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.ConverterGv;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.Model.Factories.FactoryNodeTraverser;
import com.chdryra.android.reviewer.Model.Factories.FactoryVisitorReviewNode;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.ReviewNode;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.TagsManager;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataCollection;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.GridDataViewer;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCanonical;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCanonicalCollection;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCriterion;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataAggregator;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View
        .ViewerAggregateCriteria;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View
        .ViewerAggregateToData;
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

    public FactoryGridDataViewer(FactoryReviewViewAdapter adapterFactory) {
        mAdapterFactory = adapterFactory;
    }

    public GridDataViewer<GvData> newNodeDataViewer(ReviewNode node,
                                                    ConverterGv converter,
                                                    TagsManager tagsManager,
                                                    FactoryVisitorReviewNode visitorFactory,
                                                    FactoryNodeTraverser traverserFactory,
                                                    GvDataAggregator aggregateFactory) {
        GridDataViewer<GvData> viewer;
        IdableList<ReviewNode> children = node.getChildren();
        if (children.size() > 1) {
            //aggregate children into meta review
            viewer = new ViewerTreeData(node, converter, tagsManager, mAdapterFactory,
                    visitorFactory, traverserFactory, aggregateFactory);
        } else {
            ReviewNode toExpand = children.size() == 0 ? node : children.getItem(0);
            ReviewNode expanded = toExpand.expand();
            if (expanded.equals(toExpand)) {
                //must be a leaf node so view review
                viewer = new ViewerReviewData(expanded, converter, tagsManager, mAdapterFactory);
            } else {
                //expand next layer of tree
                viewer = newNodeDataViewer(expanded, converter, tagsManager,
                        visitorFactory, traverserFactory, aggregateFactory);
            }
        }

        return viewer;
    }

    public <T extends GvData> GridDataViewer<T> newDataToDataViewer(ReviewNode parent,
                                                                    GvDataCollection<T> data) {
        return new ViewerDataToData<>(parent, data, mAdapterFactory);
    }

    public <T extends GvData> GridDataViewer<GvCanonical> newAggregateToDataViewer(GvCanonicalCollection<T> data,
                                                                                   GvDataAggregator aggregateFactory) {
        GridDataViewer<GvCanonical> viewer;
        if (data.getGvDataType().equals(GvCriterion.TYPE)) {
            viewer = new ViewerAggregateCriteria( (GvCanonicalCollection<GvCriterion>) data,
                    this, mAdapterFactory, aggregateFactory);
        } else {
            viewer = new ViewerAggregateToData<>(data, this, mAdapterFactory);
        }

        return viewer;
    }

    public <T extends GvData> GridDataViewer<T> newDataToReviewsViewer(GvDataCollection<T> data) {
        return new ViewerDataToReviews<>(data, mAdapterFactory);
    }
}
