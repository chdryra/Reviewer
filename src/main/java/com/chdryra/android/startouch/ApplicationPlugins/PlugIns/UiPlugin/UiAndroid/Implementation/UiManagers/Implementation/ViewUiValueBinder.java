/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid
        .Implementation.UiManagers.Implementation;


import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.Interfaces.ValueBinder;

/**
 * Created by: Rizwan Choudrey
 * On: 13/01/2017
 * Email: rizwan.choudrey@gmail.com
 */

public class ViewUiValueBinder<T> implements ValueBinder {
    private final Bindable<T> mBindable;
    private final ValueGetter<T> mGetter;

    private boolean mIsBound = false;

    public interface ValueGetter<T> {
        T getData();
    }

    public ViewUiValueBinder(Bindable<T> bindable, ValueGetter<T> getter) {
        mBindable = bindable;
        mGetter = getter;
    }

    @Override
    public void bind() {
        if (!mIsBound) {
            mIsBound = true;
            doBinding();
        }
    }

    @Override
    public void unbind() {
        if (mIsBound) {
            doUnbinding();
            mIsBound = false;
        }
    }

    private void doUnbinding() {

    }

    boolean isBound() {
        return mIsBound;
    }

    private void doBinding() {
        mBindable.update(mGetter.getData());
    }
}
