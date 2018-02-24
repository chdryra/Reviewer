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
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.Interfaces.ReferenceBinder;

/**
 * Created by: Rizwan Choudrey
 * On: 13/01/2017
 * Email: rizwan.choudrey@gmail.com
 */

public class ViewUiRefBinder<T> implements ReferenceBinder<T> {
    private final Bindable<T> mBindable;
    private final DataReference<T> mReference;
    private final ReferenceGetter<T> mGetter;

    private boolean mIsBound = false;

    public interface ReferenceGetter<T> {
        DataReference<T> getData();
    }

    public ViewUiRefBinder(Bindable<T> bindable, ReferenceGetter<T> getter) {
        mBindable = bindable;
        mReference = null;
        mGetter = getter;
    }

    ViewUiRefBinder(Bindable<T> bindable, DataReference<T> reference) {
        mBindable = bindable;
        mReference = reference;
        mGetter = null;
    }

    @Override
    public void onReferenceValue(T value) {
        mBindable.update(value);
    }

    @Override
    public void onInvalidated(DataReference<T> reference) {
        mBindable.onInvalidated();
    }

    @Override
    public void bind() {
        if (!mIsBound) {
            mIsBound = true;
            getReference().subscribe(this);
        }
    }

    @Override
    public void unbind() {
        if (mIsBound) {
            getReference().unsubscribe(this);
            mIsBound = false;
        }
    }

    boolean isBound() {
        return mIsBound;
    }

    private DataReference<T> getReference() {
        return mGetter != null ? mGetter.getData() : mReference;
    }
}
