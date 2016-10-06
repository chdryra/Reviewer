/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvNode;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.UiTypeLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 24/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewsListView extends ReviewViewDefault<GvNode>{
    private final ReviewNode mNode;

    public ReviewsListView(ReviewNode node, ReviewViewPerspective<GvNode> perspective) {
        super(perspective);
        mNode = node;
    }

    public ReviewNode getNode() {
        return mNode;
    }

    @Override
    public void launch(UiTypeLauncher launcher) {
        launcher.launch(this);
    }
}