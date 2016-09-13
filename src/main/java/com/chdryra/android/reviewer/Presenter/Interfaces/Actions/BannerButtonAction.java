/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.Interfaces.Actions;

import android.view.View;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 18/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface BannerButtonAction<T extends GvData> extends ReviewViewAction<T> {
    interface BannerButton {
        void setTitle(String title);
    }

    boolean onLongClick(View v);

    void onClick(View v);

    void setButton(BannerButton button);
}
