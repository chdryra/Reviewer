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
public class ViewerDataToData<T extends GvData> implements GridDataViewer<T> {
    private Context mContext;
    private ReviewNode mNode;
    private GvDataCollection<T> mData;
    private ReviewsRepository mRepository;

    public ViewerDataToData(Context context,
                            ReviewNode node,
                            GvDataCollection<T> data,
                            ReviewsRepository repository) {
        mContext = context;
        mNode = node;
        mData = data;
        mRepository = repository;
    }

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
            return FactoryReviewViewAdapter.newDataToDataAdapter(mContext, mNode,
                    (GvDataCollection) datum, mRepository);
        }

        return null;
    }

    @Override
    public ReviewViewAdapter expandGridData() {
        ReviewNode meta = mRepository.createMetaReview(mData, mData.getStringSummary());
        return FactoryReviewViewAdapter.newNodeDataAdapter(mContext, meta, mRepository);
    }
}

