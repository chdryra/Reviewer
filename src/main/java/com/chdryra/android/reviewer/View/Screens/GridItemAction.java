package com.chdryra.android.reviewer.View.Screens;

import android.view.View;

import com.chdryra.android.reviewer.View.GvDataModel.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GridItemAction extends ReviewViewAction {

    public void onGridItemClick(GvData item, int position, View v) {
    }

    public void onGridItemLongClick(GvData item, int position, View v) {
        onGridItemClick(item, position, v);
    }
}
