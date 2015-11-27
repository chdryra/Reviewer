package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Implementation;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.Interfaces.ReviewViewAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Factories
        .FactoryGridDataViewer;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Factories
        .FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Interfaces
        .GridDataViewer;
import com.chdryra.android.reviewer.View.DataAggregation.GvCanonical;
import com.chdryra.android.reviewer.View.DataAggregation.GvCanonicalCollection;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvDataListImpl;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvDataType;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Interfaces.GvData;

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
                                 FactoryGridDataViewer viewerfactory,
                                 FactoryReviewViewAdapter adapterFactory) {
        mData = aggregateData;
        mAdapterFactory = adapterFactory;
        mViewer = viewerfactory.newDataToReviewsViewer(mData);
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
    public GvDataType<? extends GvData> getGvDataType() {
        return mData.getGvDataType();
    }

    @Override
    public GvDataListImpl<GvCanonical> getGridData() {
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
