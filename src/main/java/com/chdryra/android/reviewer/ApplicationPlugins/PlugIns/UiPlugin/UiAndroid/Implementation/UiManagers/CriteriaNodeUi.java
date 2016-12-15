/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers;


import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefDataList;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.R;


/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class CriteriaNodeUi extends NodeDataExpandableUi<DataCriterion> {
    private static final int LAYOUT = R.layout.formatted_criterion_bar;
    private static final int SUBJECT = R.id.criterion_subject;
    private static final int RATING = R.id.criterion_rating;

    public CriteriaNodeUi(LinearLayout view,
                          LayoutInflater inflater,
                          final ReviewNode node) {
        super(view, new ValueGetter<RefDataList<DataCriterion>>() {
            @Override
            public RefDataList<DataCriterion> getValue() {
                return node.getCriteria();
            }
        }, Strings.FORMATTED.CRITERIA, LAYOUT, inflater);
    }

    @Override
    protected void updateView(View view, DataCriterion criterion) {
        TextView subject = (TextView) view.findViewById(SUBJECT);
        RatingBar rating = (RatingBar) view.findViewById(RATING);
        subject.setText(criterion.getSubject());
        rating.setRating(criterion.getRating());
    }
}
