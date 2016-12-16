/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders;

import android.widget.TextView;

import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolderBasic;
import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolderData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.reviewer.R;

/**
 * Created by: Rizwan Choudrey
 * On: 16/12/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class VhFactFormatted extends ViewHolderBasic{
    private static final int LAYOUT = R.layout.formatted_facts;
    private static final int LABEL = R.id.fact_label;
    private static final int VALUE = R.id.fact_value;
    private static final String SEPARATOR = ": ";

    public VhFactFormatted() {
        super(LAYOUT, new int[]{LABEL, VALUE});
    }

    @Override
    public void updateView(ViewHolderData data) {
        GvFact fact = (GvFact) data;
        TextView label = (TextView) getView(LABEL);
        TextView value = (TextView) getView(VALUE);
        label.setText(fact.getLabel() + SEPARATOR);
        value.setText(fact.getValue());
    }
}
