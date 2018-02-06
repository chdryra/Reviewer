/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.Interfaces.Actions;

import android.view.View;

import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 18/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ButtonAction<T extends GvData> extends ReviewViewAction<T> {
    interface ButtonTitle {
        void update(String title);
    }

    interface ClickListener {
        void onButtonClick();
    }

    boolean onLongClick(View v);

    void onClick(View v);

    void setTitle(ButtonTitle title);

    String getButtonTitle();

    void registerListener(ClickListener listener);

    void unregisterListener(ClickListener listener);
}
