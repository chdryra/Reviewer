package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel;

import android.content.Context;

import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.ReviewsProviderModel.ReviewsRepository;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataCollection;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.Screens.ReviewListScreen;

/**
 * Created by: Rizwan Choudrey
 * On: 13/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewerToReviews<T extends GvData> implements GridDataViewer<T> {
    private Context mContext;
    private ReviewsRepository mRepository;
    private GvDataCollection<T> mData;

    public ViewerToReviews(Context context, ReviewsRepository repository) {
        mContext = context;
        mRepository = repository;
    }

    @Override
    public GvDataList<T> getGridData() {
        return mData != null ? mData.toList() : null;
    }

    @Override
    public boolean isExpandable(T datum) {
        boolean isExpandable = false;
        for (int i = 0; i < mData.size(); ++i) {
            T item = mData.getItem(i);
            isExpandable = item.equals(datum);
            if (isExpandable) break;
        }

        return isExpandable;
    }

    @Override
    public ReviewViewAdapter expandGridCell(T datum) {
        if (isExpandable(datum)) {
            String title = datum.getStringSummary();
            ReviewNode meta = mRepository.createMetaReview(datum, title);
            if (meta != null) return getReviewsScreen(meta);
        }

        return null;
    }

    @Override
    public ReviewViewAdapter expandGridData() {
        ReviewNode meta = mRepository.createFlattenedMetaReview(mData, mData.getStringSummary());
        return getReviewsScreen(meta);
    }

    @Override
    public void setData(GvDataCollection<T> data) {
        mData = data;
    }

    protected Context getContext() {
        return mContext;
    }

    protected ReviewsRepository getReviewsRepository() {
        return mRepository;
    }

    private ReviewViewAdapter getReviewsScreen(ReviewNode node) {
        return ReviewListScreen.newScreen(mContext, node, mRepository).getAdapter();
    }
}
