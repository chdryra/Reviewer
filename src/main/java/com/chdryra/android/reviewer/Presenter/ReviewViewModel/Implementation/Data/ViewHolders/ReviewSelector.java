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
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewListReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;

/**
 * Created by: Rizwan Choudrey
 * On: 15/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewSelector implements ReviewListReference.ItemReferencesCallback<ReviewReference>,
        DataReference.DereferenceCallback<ReviewReference> {
    private Selector mSelector;
    private ReviewSelectorCallback mCallback;
    private ReviewReference mReview;
    private int mNumReviews;
    private int mCount;

    public ReviewSelector(Selector selector) {
        mSelector = selector;
    }

    public void select(ReviewNode node, ReviewSelectorCallback callback) {
        mCallback = callback;
        node.getReviews().toItemReferences(this);
    }

    public interface Selector {
        ReviewReference select(ReviewReference lhs, @Nullable ReviewReference rhs);
    }

    public interface ReviewSelectorCallback {
        void onReviewSelected(@Nullable ReviewReference review);
    }

    @Override
    public void onDereferenced(@Nullable ReviewReference data, CallbackMessage message) {
        if (data != null && !message.isError()) mReview = mSelector.select(data, mReview);
        if (++mCount == mNumReviews) mCallback.onReviewSelected(mReview);
    }

    @Override
    public void onItemReferences(IdableList<ReviewItemReference<ReviewReference>> reviews) {
        mNumReviews = reviews.size();
        mCount = 0;
        for (ReviewItemReference<ReviewReference> review : reviews) {
            review.dereference(this);
        }
    }
}
