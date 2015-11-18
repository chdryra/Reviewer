package com.chdryra.android.reviewer.View.ReviewViewModel.Implementation;

import android.view.View;

import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.GridItemAction;

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
