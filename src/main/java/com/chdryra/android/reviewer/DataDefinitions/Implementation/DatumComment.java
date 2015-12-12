package com.chdryra.android.reviewer.DataDefinitions.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DatumComment implements DataComment {
    private ReviewId mReviewId;
    private String mComment;
    private boolean mIsHeadline;

    public DatumComment(ReviewId reviewId, String comment, boolean isHeadline) {
        mReviewId = reviewId;
        mComment = comment;
        mIsHeadline = isHeadline;
    }

    @Override
    public String getComment() {
        return mComment;
    }

    @Override
    public boolean isHeadline() {
        return mIsHeadline;
    }

    @Override
    public ReviewId getReviewId() {
        return mReviewId;
    }

    @Override
    public boolean hasData(DataValidator dataValidator) {
        return dataValidator.validate(this);
    }
}
