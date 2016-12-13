/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;


import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefDataList;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.Command;
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
                       final ReviewNode node,
                       @Nullable final Command onClick) {
        super(view, new ValueGetter<RefDataList<DataFact>>() {
            @Override
            public RefDataList<DataFact> getValue() {
                return node.getFacts();
            }
        }, onClick, LAYOUT, placeholder, inflater);
    }

    @Override
    protected void updateView(View view, DataFact fact) {
        TextView subject = (TextView) view.findViewById(LABEL);
        TextView rating = (TextView) view.findViewById(VALUE);
        String label = fact.getLabel() + SEPARATOR;
        subject.setText(label);
        rating.setText(fact.getValue());
    }
}
