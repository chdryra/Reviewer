/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;



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

public abstract class ViewUi<V extends View, Value> {
    private final V mView;
    private final ReferenceValueGetter<Value> mReference;

    private Command mOnClick;
    private Command mOnLongClick;

    public interface ReferenceValueGetter<T> {
        T getValue();
    }

    public abstract void update();

    ViewUi(V view, ReferenceValueGetter<Value> reference) {
        mView = view;
        mReference = reference;
    }

    public V getView() {
        return mView;
    }

    public Value getReferenceValue() {
        return mReference.getValue();
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

    protected void setBackgroundAlpha(int alpha) {
        Drawable background = getView().getBackground();
        if (background != null) background.setAlpha(alpha);
    }
}
