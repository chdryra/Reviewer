/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;


import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefDataList;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.R;


/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactsNodeUi extends NodeDataLayoutUi<DataFact> {
    private static final int LAYOUT = R.layout.formatted_facts;
    private static final int LABEL = R.id.fact_label;
    private static final int VALUE = R.id.fact_value;
    private static final String SEPARATOR = ": ";

    public FactsNodeUi(LinearLayout view,
                       int placeholder,
                       LayoutInflater inflater,
                       final ReviewNode node) {
        super(view, new ValueGetter<RefDataList<DataFact>>() {
            @Override
            public RefDataList<DataFact> getValue() {
                return node.getFacts();
            }
        }, LAYOUT, placeholder, inflater);
    }

    @Override
    protected void updateView(View view, DataFact fact) {
        TextView label = (TextView) view.findViewById(LABEL);
        TextView value = (TextView) view.findViewById(VALUE);
        label.setText(fact.getLabel() + SEPARATOR);
        value.setText(fact.getValue());
    }
}
