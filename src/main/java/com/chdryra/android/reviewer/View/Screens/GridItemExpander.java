/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 22 April, 2015
 */

package com.chdryra.android.reviewer.View.Screens;

import android.view.View;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewAdapter;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 22/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class GridItemExpander extends ReviewViewAction.GridItemAction {
    public void onClickExpandable(GvData item, int position, View v, ReviewViewAdapter expanded) {
    }

    public void onLongClickExpandable(GvData item, int position, View v, ReviewViewAdapter
            expanded) {
    }

    public void onClickNotExpandable(GvData item, int position, View v) {
    }

    public void onLongClickNotExpandable(GvData item, int position, View v) {
    }

    //Overridden
    @Override
    public void onGridItemClick(GvData item, int position, View v) {
        if (getAdapter().isExpandable(item)) {
            onClickExpandable(item, position, v, getAdapter().expandGridCell(item));
        } else {
            onClickNotExpandable(item, position, v);
        }
    }

    @Override
    public void onGridItemLongClick(GvData item, int position, View v) {
        if (getAdapter().isExpandable(item)) {
            onLongClickExpandable(item, position, v, getAdapter().expandGridCell(item));
        } else {
            onLongClickNotExpandable(item, position, v);
        }
    }
}
