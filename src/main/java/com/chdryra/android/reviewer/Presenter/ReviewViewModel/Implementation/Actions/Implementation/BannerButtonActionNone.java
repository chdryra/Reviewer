/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation;

import android.view.View;

import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.BannerButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class BannerButtonActionNone<T extends GvData> extends ReviewViewActionBasic<T>
        implements BannerButtonAction<T> {
    private String mTitle;

    public BannerButtonActionNone() {
    }

    public BannerButtonActionNone(String title) {
        mTitle = title;
    }

    @Override
    public void setButton(BannerButton button) {
        button.setTitle(mTitle);
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public boolean onLongClick(View v) {
        onClick(v);
        return true;
    }
}
