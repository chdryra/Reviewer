package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.MdGvConverter;
import com.chdryra.android.reviewer.Model.ReviewData.MdIdableList;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.Model.TagsModel.TagsManager;
import com.chdryra.android.reviewer.View.GvDataAggregation.GvDataAggregater;
import com.chdryra.android.reviewer.View.GvDataModel.GvCanonical;
import com.chdryra.android.reviewer.View.GvDataModel.GvCanonicalCollection;
import com.chdryra.android.reviewer.View.GvDataModel.GvCriterionList;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataCollection;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewOverviewList;

/**
 * Created by: Rizwan Choudrey
 * On: 05/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryGridDataViewer {
    public GridDataViewer<GvData> newNodeDataViewer(ReviewNode node,
                                                    MdGvConverter converter,
                                                    TagsManager tagsManager,
                                                    FactoryReviewViewAdapter adapterFactory,
                                                    GvDataAggregater aggregateFactory) {
        GridDataViewer<GvData> viewer;
        MdIdableList<ReviewNode> children = node.getChildren();
        if (children.size() > 1) {
            //aggregate children into meta review
            viewer = new ViewerTreeData(node, converter, tagsManager, this, adapterFactory,
                    aggregateFactory);
        } else {
            ReviewNode toExpand = children.size() == 0 ? node : children.getItem(0);
            ReviewNode expanded = toExpand.expand();
            if (expanded.equals(toExpand)) {
                //must be a leaf node so view review
                viewer = new ViewerReviewData(expanded, converter, tagsManager, adapterFactory);
            } else {
                //expand next layer of tree
                viewer = newNodeDataViewer(expanded, converter, tagsManager, adapterFactory, aggregateFactory);
            }
        }

        return viewer;
    }

    public <T extends GvData> GridDataViewer<T> newDataToDataViewer(ReviewNode parent,
                                                                    GvDataCollection<T> data,
                                                                    FactoryReviewViewAdapter adapterFactory) {
        return new ViewerDataToData<>(parent, data, adapterFactory);
    }

    public <T extends GvData> GridDataViewer<GvCanonical> newAggregateToDataViewer(GvCanonicalCollection<T> data,
                                                                         FactoryReviewViewAdapter adapterFactory,
                                                                                   GvDataAggregater aggregateFactory) {
        GridDataViewer<GvCanonical> viewer;
        if (data.getGvDataType().equals(GvCriterionList.GvCriterion.TYPE)) {
            viewer = new ViewerAggregateCriteria( (GvCanonicalCollection<GvCriterionList.GvCriterion>) data,
                    this, adapterFactory, aggregateFactory);
        } else {
            viewer = new ViewerAggregateToData<>(data, this, adapterFactory);
        }

        return viewer;
    }

    public <T extends GvData> GridDataViewer<T> newDataToReviewsViewer(GvDataCollection<T> data,
                                                                       FactoryReviewViewAdapter adapterFactory) {
        return new ViewerDataToReviews<>(data, adapterFactory);
    }

    public GridDataViewer<GvReviewOverviewList.GvReviewOverview> newChildListViewer(ReviewNode node,
                                                                                    MdGvConverter converter,
                                                                                    TagsManager tagsManager,
                                                                                    FactoryReviewViewAdapter adapterFactory) {
        return new ViewerChildList(node, converter, tagsManager, adapterFactory);
    }
}
