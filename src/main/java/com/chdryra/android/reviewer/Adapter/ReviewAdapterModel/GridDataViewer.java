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

/**
 *
 */
public interface GridDataViewer<T extends GvData> extends GridDataExpander<T> {
    GvDataList<T> getGridData();

    @Override
    boolean isExpandable(T datum);

    @Override
    ReviewViewAdapter expandGridCell(T datum);

    @Override
    ReviewViewAdapter expandGridData();

    @Override
    void setData(GvDataCollection<T> data);
}