package com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;

/**
 * Review Data: comment
 */
public class MdComment implements DataComment {
    private final String mComment;
    private final boolean mIsHeadline;
    private final MdReviewId mReviewId;

    //Constructors
    public MdComment(MdReviewId reviewId, String comment, boolean isHeadline) {
        mComment = comment;
        mIsHeadline = isHeadline;
        mReviewId = reviewId;
    }

    //Overridden
    @Override
    public ReviewId getReviewId() {
        return mReviewId;
    }

    @Override
    public boolean hasData(DataValidator dataValidator) {
        return dataValidator.validate(this);
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MdComment)) return false;

        MdComment mdComment = (MdComment) o;

        if (mComment != null ? !mComment.equals(mdComment.mComment) : mdComment.mComment !=
                null) {
            return false;
        }
        if (mReviewId != null ? !mReviewId.equals(mdComment.mReviewId) :
                mdComment.mReviewId != null) {
            return false;
        }
        if (mIsHeadline != mdComment.mIsHeadline) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = mComment != null ? mComment.hashCode() : 0;
        result = 31 * result + (mReviewId != null ? mReviewId.hashCode() : 0);
        result = 31 * result + (mIsHeadline ? 1 : 0);
        return result;
    }
}
