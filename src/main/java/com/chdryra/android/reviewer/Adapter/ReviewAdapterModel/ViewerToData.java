package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel;

import android.content.Context;

import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.ReviewsProviderModel.ReviewsRepository;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataCollection;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;

/**
 * Created by: Rizwan Choudrey
 * On: 05/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewerToData<T extends GvData> implements GridDataViewer<T> {
    private ReviewNode mNode;
    private GvDataCollection<T> mData;
    private Context mContext;
    private ReviewsRepository mRepository;

    public ViewerToData(Context context, ReviewNode node, ReviewsRepository repository) {
        mContext = context;
        mNode = node;
        mRepository = repository;
    }

    @Override
    public GvDataList<T> getGridData() {
        return mData.toList();
    }

    @Override
    public boolean isExpandable(T datum) {
        return datum.hasElements() && mData != null && mData.contains(datum);
    }

    @Override
    public ReviewViewAdapter expandGridCell(T datum) {
        if (isExpandable(datum)) {
            return FactoryReviewViewAdapter.newExpandToDataAdapter(mContext, mNode,
                    (GvDataCollection) datum, mRepository);
        }

        return null;
    }

    @Override
    public ReviewViewAdapter expandGridData() {
        ReviewNode meta = mRepository.createMetaReview(mData, mData.getStringSummary());
        return FactoryReviewViewAdapter.newTreeDataAdapter(mContext, meta, mRepository);
    }

    @Override
    public void setData(GvDataCollection<T> data) {
        mData = data;
    }
}

