/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 6 December, 2014
 */

package com.chdryra.android.reviewer.Model;

import com.chdryra.android.reviewer.Controller.DataComment;
import com.chdryra.android.reviewer.Controller.DataValidator;

/**
 * Created by: Rizwan Choudrey
 * On: 06/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class MdCommentList extends MdDataList<MdCommentList.MdComment> {
    /**
     * Review Data: comment
     * <p>
     * {@link #hasData()}: A string at least 1 character in length.
     * </p>
     */
    public MdCommentList(ReviewId reviewId) {
        super(reviewId);
    }

    public static class MdComment implements MdData, DataComment {
        private final String   mComment;
        private final boolean  mIsHeadline;
        private final ReviewId mReviewId;

        public MdComment(String comment, boolean isHeadline, ReviewId reviewId) {
            mComment = comment;
            mIsHeadline = isHeadline;
            mReviewId = reviewId;
        }

        @Override
        public ReviewId getReviewId() {
            return mReviewId;
        }

        @Override
        public boolean hasData() {
            return DataValidator.validate(this);
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
}
