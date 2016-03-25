/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNodeMutable;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNodeMutableAsync;

/**
 * Created by: Rizwan Choudrey
 * On: 24/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewTreeMutableAsync extends ReviewNodeAsyncBasic<ReviewNodeMutable> implements
        ReviewNodeMutableAsync, ReviewNode.NodeObserver {

    public ReviewTreeMutableAsync(ReviewNodeMutable initial) {
        super(initial);
    }

    @Override
    public boolean addChild(ReviewNodeMutable childNode) {
        return getNode().addChild(childNode);
    }

    @Override
    public void removeChild(ReviewId reviewId) {
        getNode().removeChild(reviewId);
    }

    @Override
    public void setParent(@Nullable ReviewNodeMutable parent) {
        getNode().setParent(parent);
    }
}
