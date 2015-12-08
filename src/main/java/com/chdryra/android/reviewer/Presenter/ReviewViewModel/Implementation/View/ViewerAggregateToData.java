package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryGridDataViewer;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.GridDataViewer;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCanonical;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCanonicalCollection;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataListImpl;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;

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