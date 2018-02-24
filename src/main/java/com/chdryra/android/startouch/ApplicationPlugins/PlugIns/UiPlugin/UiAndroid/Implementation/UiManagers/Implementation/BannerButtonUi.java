/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.Implementation;


import android.view.View;
import android.widget.Button;

import com.chdryra.android.startouch.Presenter.Interfaces.Actions.ButtonAction;

/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class BannerButtonUi extends SimpleViewUi<Button, String> {
    private final TitleDecorator mDecorator;
    private final ButtonAction<?> mAction;

    //return reviewView.getActions().getBannerButtonAction().getButtonTitle();

    BannerButtonUi(Button view, ButtonAction<?> action, TitleDecorator decorator, int alpha) {
        super(view);
        mDecorator = decorator;
        mAction = action;
        mAction.setTitle(new ButtonTitle());
        setClickable();
        setBackgroundAlpha(alpha);
    }

    @Override
    public String getViewValue() {
        return mDecorator.unDecorate(getView().getText().toString().trim());
    }

    @Override
    public void setViewValue(String s) {
        getView().setText(s != null ? mDecorator.decorate(s) : "");
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
            BannerButtonUi.this.update(title);
        }
    }
}
