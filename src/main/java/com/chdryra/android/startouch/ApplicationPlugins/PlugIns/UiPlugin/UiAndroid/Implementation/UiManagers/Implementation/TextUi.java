/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.Implementation;


import android.widget.TextView;

/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class TextUi<T extends TextView> extends SimpleViewUi<T, String> {
    public TextUi(final T view) {
        super(view);
    }

    @Override
    public String getViewValue() {
        return getView().getText().toString().trim();
    }

    @Override
    public void setViewValue(String s) {
        getView().setText(s);
    }
}
