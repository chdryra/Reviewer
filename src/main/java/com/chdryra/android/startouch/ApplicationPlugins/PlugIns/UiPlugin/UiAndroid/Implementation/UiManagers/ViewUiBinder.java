/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;


import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.DataReference;

/**
 * Created by: Rizwan Choudrey
 * On: 13/01/2017
 * Email: rizwan.choudrey@gmail.com
 */

public class ViewUiBinder<T> implements Bindable{
    private final ViewUi<?, ? extends DataReference<T>> mView;
    private final BindableViewUi<T> mCastView;

    private boolean mIsBound = false;

    public interface BindableViewUi<T> {
        void update(T value);

        void onInvalidated();
    }

    public ViewUiBinder(ViewUi<?, ? extends DataReference<T>> view) {
        mView = view;
        try {
            mCastView = (BindableViewUi<T>)mView;
        } catch (Exception e) {
            throw new IllegalArgumentException("View should implement BindableViewUi!");
        }
    }

    @Override
    public void bind() {
        if(!mIsBound) {
            mIsBound = true;
            mView.getReferenceValue().subscribe(new DataReference.ValueSubscriber<T>() {
                @Override
                public void onReferenceValue(T value) {
                    mCastView.update(value);
                }

                @Override
                public void onInvalidated(DataReference<T> reference) {
                    mCastView.onInvalidated();
                }
            });
        }
    }

    public boolean isBound() {
        return mIsBound;
    }
}
