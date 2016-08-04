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
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNodeComponent;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.TreeTraverser;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.VisitorReviewNode;

/**
 * Created by: Rizwan Choudrey
 * On: 18/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class ReviewNodeComponentBasic extends ReviewNodeBasic
        implements ReviewNodeComponent{
    private FactoryVisitorReviewNode mVisitorFactory;
    private FactoryNodeTraverser mTraverserFactory;


    private ReviewNodeComponent mParent;

    public ReviewNodeComponentBasic(FactoryReviewNode nodeFactory) {
        super();
        mVisitorFactory = nodeFactory.getVisitorFactory();
        mTraverserFactory = nodeFactory.getTraverserFactory();
    }

    @Nullable
    @Override
    public ReviewNode getParent() {
        return mParent;
    }

    protected ReviewNodeComponent getParentAsComponent() {
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


    private TreeTraverser newTraverser(ReviewNode root) {
        return mTraverserFactory.newTreeTraverser(root);
    }

    private void doTraversal(String visitorId, VisitorReviewNode visitor,
                             TreeTraverser.TraversalCallback traversalCallback) {
        TreeTraverser traverser = newTraverser(this);
        traverser.addVisitor(visitorId, visitor);
        traverser.traverse(traversalCallback);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReviewNodeComponentBasic)) return false;

        ReviewNodeComponentBasic that = (ReviewNodeComponentBasic) o;

        if (!mVisitorFactory.equals(that.mVisitorFactory)) return false;
        if (!mTraverserFactory.equals(that.mTraverserFactory)) return false;
        return mParent != null ? mParent.equals(that.mParent) : that.mParent == null;

    }

    @Override
    public int hashCode() {
        int result = mVisitorFactory.hashCode();
        result = 31 * result + mTraverserFactory.hashCode();
        result = 31 * result + (mParent != null ? mParent.hashCode() : 0);
        return result;
    }
}
