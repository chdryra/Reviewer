/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.Implementation;



import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.DataReference;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.Interfaces.ValueBinder;

/**
 * Created by: Rizwan Choudrey
 * On: 13/01/2017
 * Email: rizwan.choudrey@gmail.com
 */

public class ViewUiBinderOld<T> implements ValueBinder<T> {
    private final ViewUi<?, ? extends DataReference<T>> mView;
    private final Bindable<T> mCastView;

    private boolean mIsBound = false;

    ViewUiBinderOld(ViewUi<?, ? extends DataReference<T>> view) {
        mView = view;
        try {
            mCastView = (Bindable<T>)mView;
        } catch (Exception e) {
            throw new IllegalArgumentException("View should implement BindableViewUi!");
        }
    }

    @Override
    public void onReferenceValue(T value) {
        mCastView.update(value);
    }

    @Override
    public void onInvalidated(DataReference<T> reference) {
        mCastView.onInvalidated();
    }

    @Override
    public void bind() {
        if(!mIsBound) {
            mIsBound = true;
            mView.getReferenceValue().subscribe(this);
        }
    }

    @Override
    public void unbind() {
        if(mIsBound) {
            mView.getReferenceValue().unsubscribe(this);
            mIsBound = false;
        }
    }

    boolean isBound() {
        return mIsBound;
    }
}
