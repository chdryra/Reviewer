/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders;

import android.widget.RatingBar;
import android.widget.TextView;

import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolderBasic;
import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolderData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCriterion;
import com.chdryra.android.reviewer.R;

/**
 * Created by: Rizwan Choudrey
 * On: 16/12/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class VhCriterionFormatted extends ViewHolderBasic{
    private static final int LAYOUT = R.layout.formatted_criterion_bar;
    private static final int SUBJECT = R.id.criterion_subject;
    private static final int RATING = R.id.criterion_rating;

    public VhCriterionFormatted() {
        super(LAYOUT, new int[]{SUBJECT, RATING});
    }

    @Override
    public void updateView(ViewHolderData data) {
        GvCriterion criterion = (GvCriterion) data;
        TextView subject = (TextView) getView(SUBJECT);
        RatingBar rating = (RatingBar) getView(RATING);
        subject.setText(criterion.getSubject());
        rating.setRating(criterion.getRating());
    }
}
