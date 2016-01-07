package com.chdryra.android.reviewer.DataDefinitions.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DatumComment)) return false;

        DatumComment that = (DatumComment) o;

        if (mIsHeadline != that.mIsHeadline) return false;
        if (!mReviewId.equals(that.mReviewId)) return false;
        return mComment.equals(that.mComment);

    }

    @Override
    public int hashCode() {
        int result = mReviewId.hashCode();
        result = 31 * result + mComment.hashCode();
        result = 31 * result + (mIsHeadline ? 1 : 0);
        return result;
    }
}
