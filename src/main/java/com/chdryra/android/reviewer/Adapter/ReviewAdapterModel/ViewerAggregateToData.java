package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel;

import com.chdryra.android.reviewer.View.GvDataModel.GvCanonical;
import com.chdryra.android.reviewer.View.GvDataModel.GvCanonicalCollection;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;

/**
 * Created by: Rizwan Choudrey
 * On: 07/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewerAggregateToData<T extends GvData> implements GridDataViewer<GvCanonical> {
    private GvCanonicalCollection<T> mData;
    private FactoryReviewViewAdapter mAdapterFactory;
    private GridDataViewer<GvCanonical> mViewer;

    //Constructors
    public ViewerAggregateToData(GvCanonicalCollection<T> aggregateData,
                                 FactoryReviewViewAdapter adapterFactory) {
        mData = aggregateData;
        mAdapterFactory = adapterFactory;
        mViewer = mAdapterFactory.newDataToReviewsViewer(mData);
    }

    public FactoryReviewViewAdapter getAdapterFactory() {
        return mAdapterFactory;
    }

    //protected methods
    protected ReviewViewAdapter newDataToReviewsAdapter(GvCanonical datum) {
        return mAdapterFactory.newDataToReviewsAdapter(datum.toList(),
                datum.getCanonical().getStringSummary());
    }

    //Overridden
    @Override
    public GvDataList<GvCanonical> getGridData() {
        return mData.toList();
    }

    @Override
    public boolean isExpandable(GvCanonical datum) {
        return mData.contains(datum);
    }

    @Override
    public ReviewViewAdapter expandGridCell(GvCanonical datum) {
        ReviewViewAdapter adapter;
        if (!isExpandable(datum)) {
            adapter = null;
        } else if (datum.size() == 1) {
            return mViewer.expandGridCell(datum);
        } else {
            adapter = newDataToReviewsAdapter(datum);
        }

        return adapter;
    }

    @Override
    public ReviewViewAdapter expandGridData() {
        return mViewer.expandGridData();
    }
}
