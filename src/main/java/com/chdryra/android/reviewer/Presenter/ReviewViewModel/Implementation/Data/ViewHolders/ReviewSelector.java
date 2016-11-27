/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.reviewer.DataDefinitions.References.Implementation.DataValue;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ReviewListReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;

import java.util.Stack;

/**
 * Created by: Rizwan Choudrey
 * On: 15/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewSelector implements ReviewListReference.ItemReferencesCallback<ReviewReference, ReviewItemReference<ReviewReference>>,
        DataReference.DereferenceCallback<ReviewReference>, ReviewNode.NodeObserver {
    private final Selector mSelector;
    private ReviewSelectorCallback mCallback;
    private ReviewNode mNode;
    private ReviewReference mReview;
    private int mNumReviews;
    private int mCount;
    private final Stack<ReviewNode> mPending;
    private boolean mInProgress = false;

    interface Selector {
        @Nullable
        ReviewReference select(ReviewReference lhs, @Nullable ReviewReference rhs);
    }

    public interface ReviewSelectorCallback {
        void onReviewSelected(@Nullable ReviewReference review);
    }

    public ReviewSelector(Selector selector) {
        mSelector = selector;
        mPending = new Stack<>();
    }

    public void select(ReviewNode node, ReviewSelectorCallback callback) {
        if (mNode != null) unregisterAndReset();
        mCallback = callback;
        mNode = node;
        mNode.registerObserver(this);
        doSelection(mNode);
    }

    public void unregister(ReviewId nodeId) {
        if(mNode != null && mNode.getReviewId().equals(nodeId)) unregisterAndReset();
    }

    @Override
    public void onDereferenced(DataValue<ReviewReference> value) {
        if (mInProgress) {
            if (value.hasValue()) mReview = mSelector.select(value.getData(), mReview);
            if (++mCount == mNumReviews) {
                if (mPending.size() > 0) {
                    doSelection(mPending.pop());
                } else {
                    mCallback.onReviewSelected(mReview);
                    reset();
                }
            }
        }
    }

    @Override
    public void onItemReferences(IdableList<ReviewItemReference<ReviewReference>> reviews) {
        if (mInProgress) {
            mNumReviews = reviews.size();
            for (ReviewItemReference<ReviewReference> review : reviews) {
                review.dereference(this);
            }
        }
    }

    @Override
    public void onChildAdded(ReviewNode child) {
        if (mInProgress) {
            mPending.add(child);
        } else {
            doSelection(child);
        }
    }

    @Override
    public void onChildRemoved(ReviewNode child) {
        reselect();
    }

    @Override
    public void onTreeChanged() {
        reselect();
    }

    @Override
    public void onNodeChanged() {
        reselect();
    }

    private void reselect() {
        reset();
        doSelection(mNode);
    }

    private void unregisterAndReset() {
        mNode.unregisterObserver(this);
        mNode = null;
        reset();
    }

    private void reset() {
        mInProgress = false;
        mReview = null;
    }

    private void doSelection(ReviewNode node) {
        mInProgress = true;
        mNumReviews = 0;
        mCount = 0;
        node.getReviews().toItemReferences(this);
//        if(node.isLeaf()) {
//            mNumReviews = 1;
//            ReviewReference reference = node.getReference();
//            onDereferenced(reference != null ? new DataValue<>(reference) : new DataValue
//                    <ReviewReference>());
//        } else {
//            node.getReviews().toItemReferences(this);
//        }
    }
}
