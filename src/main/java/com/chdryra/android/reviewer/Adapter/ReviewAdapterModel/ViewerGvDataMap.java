package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel;

import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataCollection;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataMap;
import com.chdryra.android.reviewer.View.GvDataModel.GvList;

/**
 * Created by: Rizwan Choudrey
 * On: 14/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewerGvDataMap<K extends GvData> implements GridDataViewer<K> {
    private GvDataMap<K, ? extends GvData> mData;
    private ReviewViewAdapter<? extends GvData> mParent;

    public ViewerGvDataMap(ReviewViewAdapter<? extends GvData> parent, GvDataMap<K, ? extends
            GvData> data) {
        mData = data;
        mParent = parent;
    }

    @Override
    public GvDataList<K> getGridData() {
        return mData.getKeyList();
    }

    @Override
    public boolean isExpandable(K datum) {
        GvData value = mData.get(datum);
        if (value != null) {
            if (value.isCollection()) {
                return ((GvDataCollection) value).size() > 0;
            } else {
                return value.isValidForDisplay();
            }
        }

        return false;
    }

    @Override
    public ReviewViewAdapter<? extends GvData> expandItem(K datum) {
        if(isExpandable(datum)) {
            GvData value = mData.get(datum);
            GvDataCollection<? extends GvData> expanded;
            if(value.isCollection()) {
                expanded = (GvDataCollection<? extends GvData>) value;
            } else {
                GvList list = new GvList(value.getReviewId());
                list.add(value);
                expanded = list;
            }
            return FactoryReviewViewAdapter.newGvDataCollectionAdapter(mParent, expanded);
        }

        return null;
    }
}
