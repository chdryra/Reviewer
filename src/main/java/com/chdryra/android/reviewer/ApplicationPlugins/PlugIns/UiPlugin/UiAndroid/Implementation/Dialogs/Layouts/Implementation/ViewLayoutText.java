/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Implementation;

import android.widget.TextView;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.R;

/**
 * Created by: Rizwan Choudrey
 * On: 18/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewLayoutText<T extends GvData> extends DialogLayoutBasic<T> {
    public static final int LAYOUT = R.layout.dialog_text_view_large;
    public static final int TEXT = R.id.large_text_view;

    public ViewLayoutText() {
        super(new LayoutHolder(LAYOUT, TEXT));
    }

    @Override
    public void updateLayout(GvData datum) {
        ((TextView) getView(TEXT)).setText(datum.getStringSummary());
    }
}