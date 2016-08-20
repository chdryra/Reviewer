/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders;

import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataReference;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewListReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;

import java.util.Stack;

/**
 * Created by: Rizwan Choudrey
 * On: 15/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewSelector implements ReviewListReference.ItemReferencesCallback<ReviewReference>,
        DataReference.DereferenceCallback<ReviewReference>, ReviewNode.NodeObserver {
    private Selector mSelector;
    private ReviewSelectorCallback mCallback;
    private ReviewNode mNode;
    private ReviewReference mReview;
    private int mNumReviews;
    private int mCount;
    private Stack<ReviewNode> mPending;
    private boolean mInProgress = false;

    public interface Selector {
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
        doSelection(mNode);
        mNode.registerObserver(this);
    }

    public void unregister(ReviewId nodeId) {
        if(mNode != null && mNode.getReviewId().equals(nodeId)) unregisterAndReset();
    }

    @Override
    public void onDereferenced(@Nullable ReviewReference data, CallbackMessage message) {
        if (mInProgress) {
            if (data != null && !message.isError()) mReview = mSelector.select(data, mReview);
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
    public void onDescendantsChanged() {
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
    }
}
