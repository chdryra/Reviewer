/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.Model.Factories.FactoryNodeTraverser;
import com.chdryra.android.reviewer.Model.Factories.FactoryVisitorReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNodeComponent;

/**
 * Created by: Rizwan Choudrey
 * On: 18/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class ReviewNodeComponentBasic extends ReviewNodeTraversable implements ReviewNodeComponent{
    private ReviewNodeComponent mParent;

    public ReviewNodeComponentBasic(BindersManagerMeta bindersManager,
                                    FactoryVisitorReviewNode visitorFactory,
                                    FactoryNodeTraverser traverserFactory) {
        super(bindersManager, visitorFactory, traverserFactory);
    }

    @Nullable
    @Override
    public ReviewNode getParent() {
        return mParent;
    }


    @Override
    public ReviewNode getRoot() {
        return mParent != null ? mParent.getRoot() : this;
    }

    @Override
    public void setParent(ReviewNodeComponent parentNode) {
        if (mParent != null && parentNode != null
                && mParent.getReviewId().equals(parentNode.getReviewId())) {
            return;
        }

        if (mParent != null) mParent.removeChild(getReviewId());
        ReviewNode oldParent = mParent;
        mParent = parentNode;
        if (mParent != null) mParent.addChild(this);
    }
}