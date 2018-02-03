/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Persistence.Implementation;

import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 22/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class RepoResult {
    private AuthorId mAuthorId;
    private Review mReview;
    private ReviewNode mNode;
    private ReviewId mId;
    private ReviewReference mReference;
    private final Collection<Review> mReviews = new ArrayList<>();
    private final Collection<ReviewReference> mReferences = new ArrayList<>();
    private final CallbackMessage mMessage;

    public RepoResult(CallbackMessage message) {
        mMessage = message;
    }

    public RepoResult(ReviewId id, CallbackMessage message) {
        mId = id;
        mMessage = message;
    }

    public RepoResult(AuthorId authorId, Collection<Review> reviews, CallbackMessage message) {
        mAuthorId = authorId;
        mReviews.addAll(reviews);
        mMessage = message;
    }

    public RepoResult(Collection<ReviewReference> references, CallbackMessage message) {
        mReferences.addAll(references);
        mMessage = message;
    }

    public RepoResult(Review review) {
        this(review, CallbackMessage.ok());
    }

    public RepoResult(@Nullable Review review, CallbackMessage message) {
        mReview = review;
        mId = review != null ? mReview.getReviewId() : null;
        mMessage = message;
    }
    public RepoResult(ReviewReference reference) {
        this(reference, CallbackMessage.ok());
    }


    private RepoResult(@Nullable ReviewReference reference, CallbackMessage message) {
        mReference = reference;
        mId = reference != null ? mReference.getReviewId() : null;
        mMessage = message;
    }

    public RepoResult(ReviewNode node, CallbackMessage message) {
        mNode = node;
        mId = node != null ? node.getReviewId() : null;
        mMessage = message;
    }

    @Nullable
    public ReviewId getReviewId() {
        return mId;
    }

    @Nullable
    public AuthorId getAuthorId() {
        return mAuthorId;
    }

    public Review getReview() {
        return mReview;
    }

    public ReviewNode getReviewNode() {
        return mNode;
    }

    public Collection<Review> getReviews() {
        return mReviews;
    }

    public ReviewReference getReference() {
        return mReference;
    }

    public Collection<ReviewReference> getReferences() {
        return mReferences;
    }

    public boolean isError() {
        return mMessage.isError();
    }

    public boolean isReview() {
        return !isError() && mReview != null;
    }

    public boolean isReference() {
        return !isError() && mReference != null;
    }

    public boolean isReviewNode() {
        return !isError() && mNode != null;
    }

    public CallbackMessage getMessage() {
        return mMessage;
    }
}
