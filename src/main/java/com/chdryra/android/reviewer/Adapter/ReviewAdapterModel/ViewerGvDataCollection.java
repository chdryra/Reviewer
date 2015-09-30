/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 12 May, 2015
 */

package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel;

import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataCollection;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;

/**
 * Created by: Rizwan Choudrey
 * On: 12/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewerGvDataCollection<T extends GvData> implements GridDataViewer<T> {
    private GvDataCollection<T> mData;
    private GridDataExpander<T> mExpander;

    public ViewerGvDataCollection(GridDataExpander<T> expander, GvDataCollection<T> data) {
        mData = data;
        mExpander = expander;
        setData(data);
    }

    @Override
    public GvDataList<T> getGridData() {
        return mData.toList();
    }

    @Override
    public boolean isExpandable(T datum) {
        return mExpander.isExpandable(datum);
    }

    @Override
    public ReviewViewAdapter<? extends GvData> expandGridCell(T datum) {
        return mExpander.expandGridCell(datum);
    }

    @Override
    public ReviewViewAdapter<? extends GvData> expandGridData() {
        return mExpander.expandGridData();
    }

    @Override
    public void setData(GvDataCollection<T> data) {
        mData = data;
        mExpander.setData(data);
    }
}
