/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.View;

import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvNode;

/**
 * Created by: Rizwan Choudrey
 * On: 24/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewViewNode extends ReviewViewDefault<GvNode>{
    private final ReviewNode mNode;

    public ReviewViewNode(ReviewNode node, ReviewViewPerspective<GvNode> perspective) {
        super(perspective);
        mNode = node;
    }

    public ReviewNode getNode() {
        return mNode;
    }
}