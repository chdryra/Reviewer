package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel;

import android.content.Context;

import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataMap;
import com.chdryra.android.reviewer.View.GvDataModel.GvList;

/**
 * Created by: Rizwan Choudrey
 * On: 14/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewerGvDataMap implements GridDataViewer {
    private GvDataMap mData;
    private Context mContext;
    private ReviewViewAdapter mParent;

    public ViewerGvDataMap(Context context, ReviewViewAdapter parent, GvDataMap data) {
        mData = data;
        mParent = parent;
        mContext = context;
    }

    @Override
    public GvDataList getGridData() {
        return mData.getKeyList();
    }

    @Override
    public boolean isExpandable(GvData datum) {
        try {
            GvData value = mData.get(datum);
            if(value != null) {
                if(value.isCollection()) {
                    return value.hasElements();
                } else {
                    return value.isValidForDisplay();
                }
            }
        } catch (Exception e) {
            return false;
        }

        return false;
    }

    @Override
    public ReviewViewAdapter expandItem(GvData datum) {
        if(isExpandable(datum)) {
            GvData value = mData.get(datum);
            GvList wrapped = new GvList(value.getReviewId());
            return FactoryReviewViewAdapter.newGvDataCollectionAdapter(mContext, mParent, wrapped);
        }

        return null;
    }
}
