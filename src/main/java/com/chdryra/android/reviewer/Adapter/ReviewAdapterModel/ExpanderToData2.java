package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel;

import android.content.Context;

import com.chdryra.android.reviewer.ApplicationSingletons.Administrator;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.ReviewsProviderModel.ReviewsRepository;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataCollection;

/**
 * Created by: Rizwan Choudrey
 * On: 05/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ExpanderToData2<T extends GvData> implements GridDataExpander<T> {
    private ReviewNode mParent;
    private GvDataCollection<T> mData;
    private Context mContext;

    public ExpanderToData2(Context context, ReviewNode parent) {
        mContext = context;
        mParent = parent;
    }

    @Override
    public boolean isExpandable(T datum) {
        return datum.hasElements() && mData != null && mData.contains(datum);
    }

    @Override
    public ReviewViewAdapter expandGridCell(T datum) {
        if (isExpandable(datum)) {
            return FactoryReviewViewAdapter.newExpandToDataAdapter(mContext, mParent,
                    (GvDataCollection) datum);
        }

        return null;
    }

    @Override
    public ReviewViewAdapter expandGridData() {
        ReviewsRepository repo = Administrator.get(mContext).getReviewsRepository();
        ReviewNode meta = repo.createMetaReview(mData, mData.getStringSummary());
        return FactoryReviewViewAdapter.newTreeDataAdapter(mContext, meta, repo.getTagsManager());
    }

    @Override
    public void setData(GvDataCollection<T> data) {
        mData = data;
    }
}

