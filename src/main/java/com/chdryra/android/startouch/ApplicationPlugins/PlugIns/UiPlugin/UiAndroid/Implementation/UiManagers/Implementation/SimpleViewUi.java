/*
* Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
* Unauthorized copying of this file via any medium is strictly prohibited
* Proprietary and confidential
* rizwan.choudrey@gmail.com
*
*/

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid
        .Implementation.UiManagers.Implementation;


import android.support.annotation.Nullable;
import android.view.View;

import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.Command;

/**
 * Created by: Rizwan Choudrey
 * On: 24/10/2016
 * Email: rizwan.choudrey@gmail.com
 */

public abstract class SimpleViewUi<V extends View, Value> extends ViewUi<V, Value> {
    abstract Value getValue();

    public SimpleViewUi(V view) {
        super(view);
    }

    public SimpleViewUi(V view, @Nullable Command onClick) {
        super(view, onClick);
    }

    @Override
    public void onInvalidated() {

    }
}
