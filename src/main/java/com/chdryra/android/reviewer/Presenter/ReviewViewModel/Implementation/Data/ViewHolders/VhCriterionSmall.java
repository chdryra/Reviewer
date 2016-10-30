/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders;

import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolder;
import com.chdryra.android.reviewer.R;

/**
 * {@link ViewHolder}: {@link com.chdryra.android.reviewer
 * .GvSubjectRatingList.GvSubjectRating}. Shows
 * subject
 * and rating for review children
 * (criteria).
 */
public class VhCriterionSmall extends VhCriterion {
    private static final int LAYOUT = R.layout.grid_cell_criterion_small;
    private static final int SUBJECT = R.id.review_subject_small;
    private static final int RATING = R.id.review_rating_small;

    public VhCriterionSmall() {
        super(LAYOUT, SUBJECT, RATING);
    }
}
