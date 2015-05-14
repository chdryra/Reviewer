/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 12 May, 2015
 */

package com.chdryra.android.reviewer.Controller;

import android.content.Context;

import com.chdryra.android.reviewer.View.GvData;
import com.chdryra.android.reviewer.View.GvDataList;

/**
 * Created by: Rizwan Choudrey
 * On: 12/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ExpanderGridCell implements GridDataExpander {
    private Context           mContext;
    private ReviewViewAdapter mParent;

    public ExpanderGridCell(Context context, ReviewViewAdapter parent) {
        mContext = context;
        mParent = parent;
    }

    @Override
    public boolean isExpandable(GvData datum) {
        GvDataList data = mParent.getGridData();
        return data.contains(datum) && datum.isList() && ((GvDataList) datum).size() > 0;
    }

    @Override
    public ReviewViewAdapter expandItem(GvData datum) {
        if (isExpandable(datum)) {
            return new AdapterGridData(mContext, mParent, new WrapperGvDataList((GvDataList)
                    datum));
        }

        return null;
    }
}
