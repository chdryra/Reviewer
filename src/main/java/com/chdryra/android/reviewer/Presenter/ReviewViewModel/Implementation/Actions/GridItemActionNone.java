package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions;

import android.view.View;

import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.GridItemAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GridItemActionNone<T extends GvData> extends ReviewViewActionBasic<T>
        implements GridItemAction<T> {

    @Override
    public void onGridItemClick(T item, int position, View v) {
    }

    @Override
    public void onGridItemLongClick(T item, int position, View v) {
        onGridItemClick(item, position, v);
    }
}
