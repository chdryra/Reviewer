/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers;


import android.view.View;
import android.widget.Button;

import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.ButtonAction;

/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class BannerButtonUi extends TextUi<Button> {
    private final ButtonAction<?> mAction;

    public BannerButtonUi(Button view, final ButtonAction<?> action) {
        super(view, new ReferenceValueGetter<String>() {
            @Override
            public String getValue() {
                return action.getButtonTitle();
            }
        });

        mAction = action;
        mAction.setTitle(new ButtonTitle());
        setClickable();
    }

    @Override
    public void onClick(View v) {
        mAction.onClick(v);
    }

    @Override
    public boolean onLongClick(View v) {
        return mAction.onLongClick(v);
    }

    private class ButtonTitle implements ButtonAction.ButtonTitle {
        @Override
        public void update(String title) {
            getView().setText(title);
        }
    }
}
