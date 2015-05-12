/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 12 May, 2015
 */

package com.chdryra.android.reviewer.Controller;

import com.chdryra.android.reviewer.View.GvDataList;

/**
 * Created by: Rizwan Choudrey
 * On: 12/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvDataListWrapper implements GridDataWrapper {
    private GvDataList mData;

    public GvDataListWrapper(GvDataList mdata) {
        mData = mdata;
    }

    @Override
    public GvDataList getGridData() {
        return mData;
    }
}
