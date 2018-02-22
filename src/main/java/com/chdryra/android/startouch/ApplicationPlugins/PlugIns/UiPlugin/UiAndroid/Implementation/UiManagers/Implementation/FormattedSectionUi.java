/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.Implementation;


import android.widget.LinearLayout;
import android.widget.TextView;

import com.chdryra.android.startouch.R;

/**
 * Created by: Rizwan Choudrey
 * On: 15/12/2016
 * Email: rizwan.choudrey@gmail.com
 */

public abstract class FormattedSectionUi<Value> extends ViewUi<LinearLayout, Value> {
    private static final int TITLE = R.id.section_title;
    private static final int VALUE = R.id.section_value;


    public FormattedSectionUi(LinearLayout view, ReferenceValueGetter<Value> getter, String title) {
        super(view, getter);
        TextView titleView = (TextView)getView().findViewById(TITLE);
        titleView.setText(title);
    }

    TextView getValueView() {
        return (TextView) getView().findViewById(VALUE);
    }
}
