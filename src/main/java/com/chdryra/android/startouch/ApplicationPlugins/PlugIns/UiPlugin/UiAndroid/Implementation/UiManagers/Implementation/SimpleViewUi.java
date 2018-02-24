    /*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.Implementation;


import android.view.View;

/**
 * Created by: Rizwan Choudrey
 * On: 24/10/2016
 * Email: rizwan.choudrey@gmail.com
 */

public abstract class SimpleViewUi<V extends View, Value> extends ViewUi<V, Value> {
    public abstract Value getViewValue();
    public abstract void setViewValue(Value value);

    public SimpleViewUi(V view) {
        super(view);
    }

    @Override
    public void update(Value value) {
        setViewValue(value);
    }

    @Override
    public void onInvalidated() {

    }
}
