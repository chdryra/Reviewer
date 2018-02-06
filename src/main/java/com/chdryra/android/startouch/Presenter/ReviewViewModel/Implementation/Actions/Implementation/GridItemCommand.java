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
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands.Implementation.Command;

/**
 * Created by: Rizwan Choudrey
 * On: 18/11/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class GridItemCommand<T extends GvData> extends GridItemActionNone<T> {
    private final Command mClick;
    private final Command mLongClick;
    private final String mToast;

    public GridItemCommand(Command click, @Nullable Command longClick, @Nullable String toast) {
        mClick = click;
        mLongClick = longClick;
        mToast = toast;
    }

    Command getClick() {
        return mClick;
    }

    @Nullable
    Command getLongClick() {
        return mLongClick;
    }

    @Override
    public void onGridItemClick(T item, int position, View v) {
        if(mToast != null) showToast(mToast);
        mClick.execute();
    }

    @Override
    public void onGridItemLongClick(T item, int position, View v) {
        if(mLongClick == null) {
            super.onGridItemLongClick(item, position, v);
        } else {
            mLongClick.execute();
        }
    }
}
