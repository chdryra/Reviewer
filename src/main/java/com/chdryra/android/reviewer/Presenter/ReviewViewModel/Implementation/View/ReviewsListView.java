/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.BannerButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.RatingBarAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.SubjectAction;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.GridItemReviewsList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.ReviewViewActions;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReference;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LauncherUi;

/**
 * Created by: Rizwan Choudrey
 * On: 24/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewsListView extends ReviewViewDefault<GvReference>{
    private ReviewNode mNode;

    public ReviewsListView(ReviewNode node, ReviewViewPerspective<GvReference> perspective) {
        super(perspective);
        mNode = node;
    }

    public ReviewNode getNode() {
        return mNode;
    }

    @Override
    public void launch(LauncherUi launcher) {
        launcher.launch(this);
    }

    public static class Actions extends ReviewViewActions<GvReference> {
        public Actions(SubjectAction<GvReference> subjectAction,
                       RatingBarAction<GvReference> ratingBarAction,
                       BannerButtonAction<GvReference> bannerButtonAction,
                       GridItemReviewsList gridItemAction,
                       MenuAction<GvReference> menuAction) {
            super(subjectAction, ratingBarAction, bannerButtonAction, gridItemAction, menuAction);
        }

        @Override
        public GridItemReviewsList getGridItemAction() {
            return (GridItemReviewsList) super.getGridItemAction();
        }
    }
}