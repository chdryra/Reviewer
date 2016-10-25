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

import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.BannerButtonAction;

/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class BannerButtonUi extends ButtonUi {

    public BannerButtonUi(Button view, final BannerButtonAction<?> action, int textColour) {
        super(view, new ValueGetter<String>() {
            @Override
            public String getValue() {
                return action.getTitleString();
            }
        }, textColour);

        initialise(action);
    }

    private void initialise(final BannerButtonAction<?> action) {
        action.setTitle(new ButtonTitle());

        getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                action.onClick(v);
            }
        });

        getView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return action.onLongClick(v);
            }
        });

        update();
    }

    private class ButtonTitle implements BannerButtonAction.ButtonTitle {
        @Override
        public void update(String title) {
            getView().setText(title);
        }
    }
}
