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
    interface ButtonTitle {
        void update(String title);
    }

    interface ClickListener {
        void onBannerClick();
    }

    boolean onLongClick(View v);

    void onClick(View v);

    void setTitle(ButtonTitle title);

    String  getTitleString();

    void registerListener(ClickListener listener);

    void unregisterListener(ClickListener listener);
}
