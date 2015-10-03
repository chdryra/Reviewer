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

/**
 * Created by: Rizwan Choudrey
 * On: 12/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface GridDataExpander<T extends GvData> {
    boolean isExpandable(T datum);

    ReviewViewAdapter expandGridCell(T datum);

    ReviewViewAdapter expandGridData();

    void setData(GvDataCollection<T> data);
}
