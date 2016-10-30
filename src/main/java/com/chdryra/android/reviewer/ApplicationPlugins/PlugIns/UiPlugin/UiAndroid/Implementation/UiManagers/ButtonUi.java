/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;


import android.widget.Button;

/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ButtonUi extends ViewUi<Button, String>{
    public ButtonUi(Button button, ViewUi.ValueGetter<String> getter, int textColor) {
        super(button, getter);
        getView().setTextColor(textColor);
    }

    @Override
    public void update() {
        getView().setText(getValue());
    }
}
