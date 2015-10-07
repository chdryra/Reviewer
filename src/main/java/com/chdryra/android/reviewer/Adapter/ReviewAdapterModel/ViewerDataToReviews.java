package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel;

import android.content.Context;

import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.ReviewsProviderModel.ReviewsRepository;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataCollection;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;

/**
 * Created by: Rizwan Choudrey
 * On: 13/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewerDataToReviews<T extends GvData> implements GridDataViewer<T> {
    private Context mContext;
    private GvDataCollection<T> mData;
    private ReviewsRepository mRepository;

    //Constructors
    public ViewerDataToReviews(Context context,
                               GvDataCollection<T> data,
                               ReviewsRepository repository) {
        mContext = context;
        mData = data;
        mRepository = repository;
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
            ReviewNode meta = mRepository.createMetaReview(datum, datum.getStringSummary());
            return FactoryReviewViewAdapter.newReviewsListAdapter(mContext, meta, mRepository);
        } else {
            return null;
        }
    }

    @Override
    public ReviewViewAdapter expandGridData() {
        ReviewNode meta = mRepository.createFlattenedMetaReview(mData, mData.getStringSummary());
        return FactoryReviewViewAdapter.newReviewsListAdapter(mContext, meta, mRepository);
    }
}
