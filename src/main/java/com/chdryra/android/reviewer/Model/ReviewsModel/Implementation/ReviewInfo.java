/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;


import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDateReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataReviewInfo;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 17/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewInfo implements DataReviewInfo{
    private ReviewId mReviewId;
    private DataSubject mSubject;
    private DataRating mRating;
    private DataAuthorReview mAuthor;
    private DataDateReview mPublishDate;

    public ReviewInfo() {
    }

    public ReviewInfo(ReviewId reviewId, DataSubject subject, DataRating rating, DataAuthorReview
            author, DataDateReview publishDate) {
        mReviewId = reviewId;
        mSubject = subject;
        mRating = rating;
        mAuthor = author;
        mPublishDate = publishDate;
    }

    @Override
    public ReviewId getReviewId() {
        return mReviewId;
    }

    @Override
    public DataSubject getSubject() {
        return mSubject;
    }

    @Override
    public DataRating getRating() {
        return mRating;
    }

    @Override
    public DataAuthorReview getAuthor() {
        return mAuthor;
    }

    @Override
    public DataDateReview getPublishDate() {
        return mPublishDate;
    }

    public boolean isValid() {
        return mReviewId != null && mSubject != null && mRating != null && mAuthor != null && mPublishDate != null;
    }
}
