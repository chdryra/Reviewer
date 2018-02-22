    /*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.Implementation;


import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by: Rizwan Choudrey
 * On: 24/10/2016
 * Email: rizwan.choudrey@gmail.com
 */

public abstract class SimpleViewUi<V extends View, Value> extends ViewUi<V, Value> {
    private final ViewValueGetter<Value> mValue;
    private final ViewValueSetter<Value> mSetter;

    public interface ViewValueGetter<T> {
        T getValue();
    }

    public interface ViewValueSetter<T> {
        void setValue(@Nullable T value);
    }

    @Override
    public void update() {
        setViewValue(getReferenceValue());
    }

    public SimpleViewUi(V view, ReferenceValueGetter<Value> reference, ViewValueGetter<Value> value, ViewValueSetter<Value> setter) {
        super(view, reference);
        mValue = value;
        mSetter = setter;
    }

    public Value getViewValue() {
        return mValue.getValue();
    }

    public void setViewValue(@Nullable Value value) {
        mSetter.setValue(value);
    }

}
