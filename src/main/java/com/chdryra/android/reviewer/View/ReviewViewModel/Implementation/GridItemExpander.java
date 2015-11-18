/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 22 April, 2015
 */

package com.chdryra.android.reviewer.View.ReviewViewModel.Implementation;

import android.view.View;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.Interfaces.ReviewViewAdapter;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 22/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class GridItemExpander<T extends GvData> extends GridItemActionNone<T> {
    public void onClickExpandable(T item, int position, View v, ReviewViewAdapter<?> expanded) {
    }

    public void onLongClickExpandable(T item, int position, View v, ReviewViewAdapter<?>
            expanded) {
    }

    public void onClickNotExpandable(T item, int position, View v) {
    }

    public void onLongClickNotExpandable(T item, int position, View v) {
    }

    //Overridden
    @Override
    public void onGridItemClick(T item, int position, View v) {
        if (getAdapter().isExpandable(item)) {
            onClickExpandable(item, position, v, getAdapter().expandGridCell(item));
        } else {
            onClickNotExpandable(item, position, v);
        }
    }

    @Override
    public void onGridItemLongClick(T item, int position, View v) {
        if (getAdapter().isExpandable(item)) {
            onLongClickExpandable(item, position, v, getAdapter().expandGridCell(item));
        } else {
            onLongClickNotExpandable(item, position, v);
        }
    }
}
