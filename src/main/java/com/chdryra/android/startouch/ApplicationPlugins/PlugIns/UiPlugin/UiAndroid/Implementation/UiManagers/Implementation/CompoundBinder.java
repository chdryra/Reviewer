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


/**
 * Created by: Rizwan Choudrey
 * On: 13/01/2017
 * Email: rizwan.choudrey@gmail.com
 */

public class CompoundBinder<T> extends DataBinder<T> {
    private final DataBinder<?> mBinder;
    private final BinderEvent<T> mEvent;

    private boolean mIsSubstitute = false;

    public interface BinderEvent<T> {
        boolean bindOnValue(T value);
    }

    public CompoundBinder(Bindable<T> bindable,
                          DataReference<T> reference,
                          DataBinder<?> binder,
                          BinderEvent<T> event) {
        super(bindable, reference);
        mBinder = binder;
        mEvent = event;
    }

    @Override
    public void onReferenceValue(T value) {
        super.onReferenceValue(value);
        if (mEvent.bindOnValue(value)) {
            bindBinderIfNecessary();
        } else {
            unbindBinderIfNecessary();
        }
    }

    @Override
    public void onInvalidated(DataReference<T> reference) {
        super.onInvalidated(reference);
        bindBinderIfNecessary();
    }

    @Override
    public void unbind() {
        super.unbind();
        unbindBinderIfNecessary();
    }

    private void bindBinderIfNecessary() {
        if (!mIsSubstitute) {
            mBinder.bind();
            mIsSubstitute = true;
        }
    }

    private void unbindBinderIfNecessary() {
        if (mIsSubstitute) {
            mBinder.unbind();
            mIsSubstitute = false;
        }
    }
}
