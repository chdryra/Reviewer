package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel;

import android.content.Context;

import com.chdryra.android.reviewer.Model.ReviewData.IdableList;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.ReviewsProviderModel.ReviewsRepository;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;

/**
 * Created by: Rizwan Choudrey
 * On: 06/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewerNodeData implements GridDataViewer<GvData> {
    private GridDataViewer<GvData> mViewer;

    public ViewerNodeData(Context context, ReviewNode node, ReviewsRepository repository) {
        IdableList<ReviewNode> children = node.getChildren();
        if(children.size() > 1) {
            mViewer = new ViewerTreeData(context, node, repository);
        } else {
            ReviewNode toExpand = children.size() == 0 ? node : children.getItem(0);
            ReviewNode expanded = toExpand.expand();
            if(expanded.equals(toExpand)) {
                mViewer = new ViewerReviewData(context, expanded, repository);
            } else {
                mViewer = new ViewerNodeData(context, expanded, repository);
            }
        }
    }

    @Override
    public GvDataList<GvData> getGridData() {
        return mViewer.getGridData();
    }

    @Override
    public boolean isExpandable(GvData datum) {
        return mViewer.isExpandable(datum);
    }

    @Override
    public ReviewViewAdapter expandGridCell(GvData datum) {
        return mViewer.expandGridCell(datum);
    }

    @Override
    public ReviewViewAdapter expandGridData() {
        return mViewer.expandGridData();
    }
}
