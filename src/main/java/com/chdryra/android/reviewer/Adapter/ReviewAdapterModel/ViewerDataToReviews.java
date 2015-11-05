package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel;

import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataCollection;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;

/**
 * Created by: Rizwan Choudrey
 * On: 13/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewerDataToReviews<T extends GvData> implements GridDataViewer<T> {
    private GvDataCollection<T> mData;
    private FactoryReviewViewAdapter mAdapterFactory;

    //Constructors
    public ViewerDataToReviews(GvDataCollection<T> data, FactoryReviewViewAdapter adapterFactory) {
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
            return mAdapterFactory.newReviewsListAdapter(datum);
        } else {
            return null;
        }
    }

    @Override
    public ReviewViewAdapter expandGridData() {
        return mAdapterFactory.newFlattenedReviewsListAdapter(mData);
    }
}
