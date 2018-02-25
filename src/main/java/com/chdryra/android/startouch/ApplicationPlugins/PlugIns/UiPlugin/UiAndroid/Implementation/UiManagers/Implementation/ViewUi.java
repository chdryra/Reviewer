/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid
        .Implementation.UiManagers.Implementation;


import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.view.View;

import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.Command;

/**
 * Created by: Rizwan Choudrey
 * On: 05/08/2017
 * Email: rizwan.choudrey@gmail.com
 */

public abstract class ViewUi<V extends View, Value> implements Bindable<Value> {
    private final V mView;

    private Command mOnClick;
    private Command mOnLongClick;

    ViewUi(V view) {
        mView = view;
    }

    ViewUi(V view, @Nullable Command onClick) {
        mView = view;
        setOnClickCommand(onClick);
    }

    public V getView() {
        return mView;
    }

    public void setOnClickCommand(@Nullable Command onClick) {
        if (onClick == null) return;
        setClickable();
        mOnClick = onClick;
        if (mOnLongClick == null) mOnLongClick = onClick;
    }

    void setClickable() {
        getView().setClickable(true);
        setOnClick();
        setOnLongClick();
    }

    public void onClick(View v) {
        if (mOnClick != null) mOnClick.execute();
    }

    public boolean onLongClick(View v) {
        if (mOnLongClick != null) {
            mOnLongClick.execute();
            return true;
        }
        return false;
    }

    void setBackgroundAlpha(int alpha) {
        Drawable background = getView().getBackground();
        if (background != null) background.setAlpha(alpha);
    }

    private void setOnLongClick() {
        getView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return ViewUi.this.onLongClick(view);
            }
        });
    }

    private void setOnClick() {
        getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewUi.this.onClick(view);
            }
        });
    }
}
