/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.ReviewViewActions;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReviewAsync;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewDefault;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewParams;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewPerspective;

/**
 * Created by: Rizwan Choudrey
 * On: 30/08/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryChildListView {
    public ReviewView<GvReviewAsync>
    newView(ReviewViewAdapter<GvReviewAsync> adapter, ReviewViewActions<GvReviewAsync> actions) {
        ReviewViewParams params = getReviewViewParams();

        ReviewViewPerspective<GvReviewAsync> perspective;
        perspective = new ReviewViewPerspective<>(adapter, actions, params);

        return new ReviewViewDefault<>(perspective);
    }

    @NonNull
    private ReviewViewParams getReviewViewParams() {
        ReviewViewParams params = new ReviewViewParams();
        ReviewViewParams.CellDimension full = ReviewViewParams.CellDimension.FULL;
        ReviewViewParams.GridViewAlpha trans = ReviewViewParams.GridViewAlpha.TRANSPARENT;
        params.setSubjectVisible(true).setRatingVisible(true).setBannerButtonVisible(true)
                .setCoverManager(false).setCellHeight(full).setCellWidth(full).setGridAlpha(trans);
        return params;
    }
}
