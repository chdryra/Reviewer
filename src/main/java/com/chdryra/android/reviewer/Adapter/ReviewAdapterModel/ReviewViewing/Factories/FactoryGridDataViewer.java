package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Factories;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Implementation.GvConverters.ConverterGv;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Implementation
        .ViewerAggregateCriteria;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Implementation
        .ViewerAggregateToData;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Implementation
        .ViewerDataToData;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Implementation
        .ViewerDataToReviews;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Implementation
        .ViewerReviewData;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Implementation
        .ViewerTreeData;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Interfaces
        .GridDataViewer;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.IdableList;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviewTreeTraverser;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Model.Factories.FactoryVisitorReviewNode;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvDataAggregater;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvCanonical;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvCanonicalCollection;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvCriterion;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Interfaces.GvData;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Interfaces.GvDataCollection;

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
                                                    FactoryReviewTreeTraverser traverserFactory,
                                                    GvDataAggregater aggregateFactory) {
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
                                                                                   GvDataAggregater aggregateFactory) {
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
