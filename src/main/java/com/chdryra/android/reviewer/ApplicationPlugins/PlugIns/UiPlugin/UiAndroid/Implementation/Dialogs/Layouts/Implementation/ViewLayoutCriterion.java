/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Implementation;

import android.widget.RatingBar;
import android.widget.TextView;

import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCriterion;
import com.chdryra.android.reviewer.R;

/**
 * Created by: Rizwan Choudrey
 * On: 17/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewLayoutCriterion extends DatumLayoutBasic<GvCriterion> {
    private static final int LAYOUT = R.layout.dialog_criterion_view;
    private static final int SUBJECT = R.id.child_name_text_view;
    private static final int RATING = R.id.child_rating_bar;

    //Constructors
    public ViewLayoutCriterion() {
        super(new LayoutHolder(LAYOUT, SUBJECT, RATING));
    }

    //Overridden
    @Override
    public void updateView(GvCriterion data) {
        ((TextView) getView(SUBJECT)).setText(data.getSubject());
        ((RatingBar) getView(RATING)).setRating(data.getRating());
    }

    @Override
    public void initialise(GvCriterion data) {
        getView(SUBJECT).setFocusable(false);
        ((RatingBar) getView(RATING)).setIsIndicator(true);
        super.initialise(data);
    }
}
