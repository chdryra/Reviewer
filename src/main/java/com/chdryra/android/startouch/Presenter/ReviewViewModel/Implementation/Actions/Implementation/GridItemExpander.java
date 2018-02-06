/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Implementation;

import android.support.annotation.Nullable;
import android.view.View;

import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ReviewViewAdapter;

/**
 * Created by: Rizwan Choudrey
 * On: 22/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class GridItemExpander<T extends GvData> extends GridItemActionNone<T> {
    void onClickExpandable(T item, int position, View v, @Nullable ReviewViewAdapter<?> expanded) {
    }

    void onLongClickExpandable(T item, int position, View v, @Nullable ReviewViewAdapter<?>
            expanded) {
    }

    void onClickNotExpandable(T item, int position, View v) {
    }

    void onLongClickNotExpandable(T item, int position, View v) {
    }

    @Override
    public void onGridItemClick(T item, int position, View v) {
        ReviewViewAdapter<?> expanded = getAdapter().expandGridCell(item);
        if (expanded != null) {
            onClickExpandable(item, position, v, expanded);
        } else {
            onClickNotExpandable(item, position, v);
        }
    }

    @Override
    public void onGridItemLongClick(T item, int position, View v) {
        ReviewViewAdapter<?> expanded = getAdapter().expandGridCell(item);
        if (expanded != null) {
            onLongClickExpandable(item, position, v, getAdapter().expandGridCell(item));
        } else {
            onLongClickNotExpandable(item, position, v);
        }
    }
}
