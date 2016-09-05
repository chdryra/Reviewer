/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNodeComponent;

/**
 * Created by: Rizwan Choudrey
 * On: 18/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class ReviewNodeComponentBasic extends ReviewNodeBasic
        implements ReviewNodeComponent{
    private ReviewNodeComponent mParent;

    @Nullable
    @Override
    public ReviewNode getParent() {
        return getParentAsComponent();
    }

    ReviewNodeComponent getParentAsComponent() {
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
        mParent = parentNode;
        if (mParent != null) mParent.addChild(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReviewNodeComponentBasic)) return false;
        if (!super.equals(o)) return false;

        ReviewNodeComponentBasic that = (ReviewNodeComponentBasic) o;

        return mParent != null ? mParent.equals(that.mParent) : that.mParent == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (mParent != null ? mParent.hashCode() : 0);
        return result;
    }
}
