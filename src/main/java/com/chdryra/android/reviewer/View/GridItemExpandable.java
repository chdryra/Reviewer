/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 22 April, 2015
 */

package com.chdryra.android.reviewer.View;

import android.view.View;

import com.chdryra.android.reviewer.Controller.ReviewViewAction;
import com.chdryra.android.reviewer.Controller.ReviewViewAdapter;

/**
 * Created by: Rizwan Choudrey
 * On: 22/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class GridItemExpandable extends ReviewViewAction.GridItemAction {
    private ReviewViewAdapter mAdapter;

    public GridItemExpandable(ReviewViewAdapter adapter) {
        mAdapter = adapter;
    }

    ;

    public void onClickExpanded(GvData item, int position, View v,
            ReviewViewAdapter expanded) {
    }

    ;

    public void onLongClickExpanded(GvData item, int position, View v,
            ReviewViewAdapter expanded) {
    }

    @Override
    public void onGridItemClick(GvData item, int position, View v) {
        if (mAdapter.isExpandable(item)) {
            onClickExpanded(item, position, v, mAdapter.expandItem(item));
        }
    }

    @Override
    public void onGridItemLongClick(GvData item, int position, View v) {
        if (mAdapter.isExpandable(item)) {
            onLongClickExpanded(item, position, v, mAdapter.expandItem(item));
        }
    }
}
