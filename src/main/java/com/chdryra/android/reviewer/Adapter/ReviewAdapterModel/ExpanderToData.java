/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 12 May, 2015
 */

package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel;

import android.content.Context;

import com.chdryra.android.reviewer.ApplicationSingletons.Administrator;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.ReviewsProviderModel.ReviewsRepository;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataCollection;

/**
 * Created by: Rizwan Choudrey
 * On: 12/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ExpanderToData<T extends GvData> implements GridDataExpander<T> {
    private ReviewViewAdapter<? extends GvData> mParent;
    private GvDataCollection<T> mData;
    private Context mContext;

    public ExpanderToData(Context context, ReviewViewAdapter<? extends GvData> parent) {
        mContext = context;
        mParent = parent;
    }

    @Override
    public boolean isExpandable(T datum) {
        if (mData == null) return false;
        return datum.hasElements() && mData.contains(datum);
    }

    @Override
    public ReviewViewAdapter<? extends GvData> expandGridCell(T datum) {
        if (isExpandable(datum)) {
            return FactoryReviewViewAdapter.newExpandToDataAdapter(mContext, mParent,
                    (GvDataCollection<? extends GvData>) datum);
        }

        return null;
    }

    @Override
    public ReviewViewAdapter<? extends GvData> expandGridData() {
        ReviewsRepository repo = Administrator.get(mContext).getReviewsRepository();
        ReviewNode meta = repo.createMetaReview(mData, mData.getStringSummary());
        return FactoryReviewViewAdapter.newTreeDataAdapter(mContext, meta, repo.getTagsManager());
    }

    @Override
    public void setData(GvDataCollection<T> data) {
        mData = data;
    }
}
