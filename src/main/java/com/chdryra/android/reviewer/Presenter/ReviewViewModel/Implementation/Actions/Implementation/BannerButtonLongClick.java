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
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.Command;


/**
 * Created by: Rizwan Choudrey
 * On: 29/11/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class BannerButtonLongClick<T extends GvData> extends BannerButtonActionNone<T> {
    private final Command mLongClick;

    public BannerButtonLongClick(Command longClick) {
        mLongClick = longClick;
    }

    public BannerButtonLongClick(Command longClick, String title) {
        super(title);
        mLongClick = longClick;
    }

    @Override
    public boolean onLongClick(View v) {
        mLongClick.execute();
        return true;
    }
}
