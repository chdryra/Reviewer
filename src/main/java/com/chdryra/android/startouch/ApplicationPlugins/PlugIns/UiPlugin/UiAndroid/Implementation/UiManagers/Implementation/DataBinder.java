/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid
        .Implementation.UiManagers.Implementation;


import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.DataReference;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.Interfaces.ValueBinder;

/**
 * Created by: Rizwan Choudrey
 * On: 13/01/2017
 * Email: rizwan.choudrey@gmail.com
 */

public class DataBinder<T> implements ValueBinder, DataReference.ValueSubscriber<T> {
    private final Bindable<T> mBindable;
    private final DataReference<T> mReference;

    private boolean mIsBound = false;

    public DataBinder(Bindable<T> bindable, DataReference<T> reference) {
        mBindable = bindable;
        mReference = reference;
    }

    @Override
    public void bind() {
        if (!mIsBound) {
            mReference.subscribe(this);
            mIsBound = true;
        }
    }

    @Override
    public void unbind() {
        if (mIsBound) {
            mReference.unsubscribe(this);
            mIsBound = false;
        }
    }

    @Override
    public void onReferenceValue(T value) {
        mBindable.update(value);
    }

    @Override
    public void onInvalidated(DataReference<T> reference) {
        mBindable.onInvalidated();
    }
}
