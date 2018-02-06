/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Implementation;

import android.widget.TextView;

import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.startouch.R;

/**
 * Created by: Rizwan Choudrey
 * On: 18/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewLayoutFact extends DatumLayoutBasic<GvFact> {
    private static final int LAYOUT = R.layout.dialog_fact_view;
    private static final int LABEL = R.id.fact_label_text_view;
    private static final int VALUE = R.id.fact_value_text_view;

    public ViewLayoutFact() {
        super(new LayoutHolder(LAYOUT, LABEL, VALUE));
    }

    @Override
    public void updateView(GvFact fact) {
        ((TextView) getView(LABEL)).setText(fact.getLabel());
        ((TextView) getView(VALUE)).setText(fact.getValue());
    }
}
