/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers;


import android.support.annotation.Nullable;
import android.view.View;

import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.Command;

/**
 * Created by: Rizwan Choudrey
 * On: 24/10/2016
 * Email: rizwan.choudrey@gmail.com
 */

public abstract class ViewUi<V extends View, Value> {
    private final V mView;
    private final ValueGetter<Value> mGetter;
    private Command mOnClick;
    private Command mOnLongClick;

    public interface ValueGetter<T> {
        T getValue();
    }

    public abstract void update();

    public ViewUi(V view, ValueGetter<Value> getter) {
        mView = view;
        mGetter = getter;
    }

    public V getView() {
        return mView;
    }

    public Value getValue() {
        return mGetter.getValue();
    }

    public void setOnClickCommand(@Nullable Command onClick) {
        if(onClick == null) return;
        setClickable();
        mOnClick = onClick;
        if (mOnLongClick == null) mOnLongClick = onClick;
    }

    public void setClickable() {
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
