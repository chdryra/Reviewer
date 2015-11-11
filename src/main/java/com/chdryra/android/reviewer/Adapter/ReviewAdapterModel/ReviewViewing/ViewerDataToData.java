package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewAdapter;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataCollection;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;

/**
 * Created by: Rizwan Choudrey
 * On: 05/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewerDataToData<T extends GvData> implements GridDataViewer<T> {
    private ReviewNode mNode;
    private GvDataCollection<T> mData;
    private FactoryReviewViewAdapter mAdapterFactory;

    //Constructors
    public ViewerDataToData(ReviewNode node,
                            GvDataCollection<T> data,
                            FactoryReviewViewAdapter adapterFactory) {
        mNode = node;
        mData = data;
        mAdapterFactory = adapterFactory;
    }

    //Overridden
    @Override
    public GvDataList<T> getGridData() {
        return mData.toList();
    }

    @Override
    public boolean isExpandable(T datum) {
        return datum.hasElements() && mData.contains(datum);
    }

    @Override
    public ReviewViewAdapter expandGridCell(T datum) {
        if (isExpandable(datum)) {
            return mAdapterFactory.newDataToDataAdapter(mNode, (GvDataCollection) datum);
        }

        return null;
    }

    @Override
    public ReviewViewAdapter expandGridData() {
        return mAdapterFactory.newNodeDataAdapter(mData);
    }
}

