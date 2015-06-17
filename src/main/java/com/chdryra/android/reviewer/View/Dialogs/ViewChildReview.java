/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 17 June, 2015
 */

package com.chdryra.android.reviewer.View.Dialogs;

import android.widget.RatingBar;
import android.widget.TextView;

import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.GvDataModel.GvChildList;

/**
 * Created by: Rizwan Choudrey
 * On: 17/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewChildReview extends DialogLayout<GvChildList.GvChildReview> {
    public static final int   LAYOUT  = R.layout.dialog_criterion;
    public static final int   SUBJECT = R.id.child_name_edit_text;
    public static final int   RATING  = R.id.child_rating_bar;
    public static final int[] VIEWS   = new int[]{SUBJECT, RATING};

    public ViewChildReview() {
        super(LAYOUT, VIEWS);
    }

    @Override
    public void updateLayout(GvChildList.GvChildReview data) {
        ((TextView) getView(SUBJECT)).setText(data.getSubject());
        ((RatingBar) getView(RATING)).setRating(data.getRating());
    }

    @Override
    public void initialise(GvChildList.GvChildReview data) {
        getView(SUBJECT).setFocusable(false);
        ((RatingBar) getView(RATING)).setIsIndicator(true);
        super.initialise(data);
    }
}