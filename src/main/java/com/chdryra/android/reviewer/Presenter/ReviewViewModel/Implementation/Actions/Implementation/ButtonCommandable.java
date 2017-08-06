/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation;


import android.view.View;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.Command;

/**
 * Created by: Rizwan Choudrey
 * On: 09/12/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class ButtonCommandable<T extends GvData> extends ButtonActionNone<T> {
    private Command mClick;
    private Command mLongClick;

    public ButtonCommandable() {
    }

    public ButtonCommandable(String title) {
        super(title);
    }

    public void setClick(Command click) {
        mClick = click;
    }

    public void setLongClick(Command longClick) {
        mLongClick = longClick;
    }

    @Override
    public void onClick(View v) {
        if(mClick != null) mClick.execute();
        notifyListeners();
    }

    @Override
    public boolean onLongClick(View v) {
        if(mLongClick != null) {
            mLongClick.execute();
            return true;
        } else {
            return super.onLongClick(v);
        }
    }
}
