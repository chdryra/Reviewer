/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid
        .Implementation.UiManagers.Implementation;


import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.chdryra.android.startouch.Presenter.Interfaces.Actions.ButtonAction;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.View.ReviewViewParams;

/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ContextUi extends SimpleViewUi<Button, String> {
    private final TitleDecorator mDecorator;
    private final ButtonAction<?> mAction;

    ContextUi(View view, int buttonId, @Nullable ButtonAction<?> action, ReviewViewParams
            .ContextView params,
              TitleDecorator decorator) {
        super((Button) view.findViewById(buttonId));
        mAction = action;
        mDecorator = decorator;
        initialise(view, params);
    }

    @Override
    public void update(String value) {
        getView().setText(mDecorator.decorate(value));
    }

    @Override
    String getValue() {
        return mDecorator.unDecorate(getView().getText().toString().trim());
    }

    private void initialise(View layout, ReviewViewParams.ContextView params) {
        if (mAction == null) {
            layout.setVisibility(View.GONE);
            return;
        }

        Button button = getView();
        setBackgroundAlpha(params.getAlpha());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAction.onClick(v);
            }
        });
        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return mAction.onLongClick(v);
            }
        });
    }
}
