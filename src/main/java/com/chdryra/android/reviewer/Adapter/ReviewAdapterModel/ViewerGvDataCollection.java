/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 12 May, 2015
 */

package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel;

import android.content.Context;

import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataCollection;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;

/**
 * Created by: Rizwan Choudrey
 * On: 12/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewerGvDataCollection implements GridDataViewer {
    private GvDataCollection mData;
    private ExpanderGridCell mExpander;

    public ViewerGvDataCollection(Context context, ReviewViewAdapter parent, GvDataCollection data) {
        mData = data;
        mExpander = new ExpanderGridCell(context, parent);
    }

    @Override
    public GvDataList getGridData() {
        return mData.toList();
    }

    @Override
    public boolean isExpandable(GvData datum) {
        return mExpander.isExpandable(datum);
    }

    @Override
    public ReviewViewAdapter expandItem(GvData datum) {
        return mExpander.expandItem(datum);
    }
}
