package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel;

import android.content.Context;

import com.chdryra.android.reviewer.ReviewsProviderModel.ReviewsRepository;
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
    private Context mContext;
    private GvCanonicalCollection<T> mData;
    private ReviewsRepository mRepository;

    public ViewerAggregateToData(Context context,
                       GvCanonicalCollection<T> data,
                       ReviewsRepository repository) {
        mContext = context;
        mData = data;
        mRepository = repository;
    }

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
        } else if(datum.size() == 1) {
            return new ViewerDataToReviews<>(mContext, mData, mRepository).expandGridCell(datum);
        } else {
            adapter = FactoryReviewViewAdapter.newDataToReviewsAdapter(mContext, datum.toList(),
                    mRepository, datum.getCanonical().getStringSummary());
        }

        return adapter;
    }

    @Override
    public ReviewViewAdapter expandGridData() {
        return new ViewerDataToReviews<>(mContext, mData, mRepository).expandGridData();
    }

    protected Context getContext() {
        return mContext;
    }

    protected ReviewsRepository getRepository() {
        return mRepository;
    }
}
