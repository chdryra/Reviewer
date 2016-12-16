/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;



import android.widget.LinearLayout;

/**
 * Created by: Rizwan Choudrey
 * On: 13/12/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class FormattedTextUi extends FormattedSectionUi<String> {
    public FormattedTextUi(LinearLayout section, String title, ValueGetter<String> getter) {
        super(section, getter, title);
    }

    @Override
    public void update() {
        getValueView().setText(getValue());
    }
}
