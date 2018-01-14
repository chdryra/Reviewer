/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers;


import android.widget.TextView;

/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class TextUi<T extends TextView> extends SimpleViewUi<T, String> {
    public TextUi(final T view, ReferenceValueGetter<String> getter) {
        super(view, getter, new ViewValueGetter<String>() {
            @Override
            public String getValue() {
                return view.getText().toString().trim();
            }
        },new ViewValueSetter<String>() {
            @Override
            public void setValue(String value) {
                view.setText(value);
            }
        });
    }
}
