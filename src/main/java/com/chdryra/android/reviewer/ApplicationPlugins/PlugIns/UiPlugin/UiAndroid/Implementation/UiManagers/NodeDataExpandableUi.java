/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;


import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefDataList;

/**
 * Created by: Rizwan Choudrey
 * On: 13/12/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class NodeDataExpandableUi<T extends HasReviewId>
        extends NodeDataSectionUi<T, RefDataList<T>> {
    private final int mValueLayout;
    private final LayoutInflater mInflater;

    protected abstract void updateView(View view, T criterion);

    public NodeDataExpandableUi(LinearLayout view,
                                ValueGetter<RefDataList<T>> getter,
                                String title,
                                int valueLayout,
                                LayoutInflater inflater) {
        super(view, getter, title);
        mValueLayout = valueLayout;
        mInflater = inflater;
    }

    @Override
    protected void setEmpty() {
        TextView placeholder = getValueView();
        placeholder.setTypeface(placeholder.getTypeface(), Typeface.ITALIC);
        placeholder.setText(Strings.FORMATTED.NONE);
    }

    @Override
    protected void updateView(IdableList<T> data) {
        getValueView().setVisibility(View.GONE);
        LinearLayout layout = new LinearLayout(getView().getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        for (T datum : data) {
            View datumView = mInflater.inflate(mValueLayout, null);
            updateView(datumView, datum);
            layout.addView(datumView);
        }
        getView().addView(layout);
    }
}
