/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers;


import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefDataList;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.Command;
import com.chdryra.android.reviewer.R;


/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class CriteriaNodeUi extends NodeDataUi<DataCriterion, RefDataList<DataCriterion>,
        LinearLayout> {
    private static final int LAYOUT = R.layout.formatted_criterion_bar;
    private static final int SUBJECT = R.id.criterion_subject;
    private static final int RATING = R.id.criterion_rating;

    private final int mPlaceholder;
    private final LayoutInflater mInflater;

    public CriteriaNodeUi(LinearLayout view,
                          int placeholder,
                          LayoutInflater inflater,
                          final ReviewNode node,
                          @Nullable final Command onClick) {
        super(view, new ValueGetter<RefDataList<DataCriterion>>() {
            @Override
            public RefDataList<DataCriterion> getValue() {
                return node.getCriteria();
            }
        }, onClick);
        mPlaceholder = placeholder;
        mInflater = inflater;
    }

    @Override
    protected void updateView(IdableList<DataCriterion> data) {
        getPlaceholder().setVisibility(View.GONE);
        LinearLayout layout = getView();
        for (DataCriterion criterion : data) {
            newCriterionView(layout, criterion);
        }
    }

    @Override
    protected void setEmpty() {
        TextView placeholder = getPlaceholder();
        placeholder.setTypeface(placeholder.getTypeface(), Typeface.ITALIC);
        placeholder.setText(Strings.FORMATTED.NONE);
    }

    private TextView getPlaceholder() {
        return (TextView) getView().findViewById(mPlaceholder);
    }

    @NonNull
    private View newCriterionView(LinearLayout layout, DataCriterion criterion) {
        View view = mInflater.inflate(LAYOUT, layout);
        TextView subject = (TextView) view.findViewById(SUBJECT);
        RatingBar rating = (RatingBar) view.findViewById(RATING);
        subject.setText(criterion.getSubject());
        rating.setRating(criterion.getRating());
        return view;
    }
}
