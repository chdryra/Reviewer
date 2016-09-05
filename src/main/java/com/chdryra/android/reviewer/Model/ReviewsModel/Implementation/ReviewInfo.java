/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;


import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataReviewInfo;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 17/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewInfo implements DataReviewInfo {
    private final ReviewId mReviewId;
    private final DataSubject mSubject;
    private final DataRating mRating;
    private final DataAuthorId mAuthorId;
    private final DataDate mPublishDate;

    public ReviewInfo(ReviewId reviewId, DataSubject subject, DataRating rating, DataAuthorId
            authorId, DataDate publishDate) {
        mReviewId = reviewId;
        mSubject = subject;
        mRating = rating;
        mAuthorId = authorId;
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
    public DataAuthorId getAuthorId() {
        return mAuthorId;
    }

    @Override
    public DataDate getPublishDate() {
        return mPublishDate;
    }

    public boolean isValid() {
        return mReviewId != null && mSubject != null && mRating != null && mAuthorId != null && mPublishDate != null;
    }
}
