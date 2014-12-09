/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 6 December, 2014
 */

package com.chdryra.android.reviewer;

/**
 * Created by: Rizwan Choudrey
 * On: 06/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class MdCommentList extends MdList<MdCommentList.MdComment> {
    /**
     * Review Data: comment
     * <p>
     * {@link #hasData()}: A string at least 1 character in length.
     * </p>
     */
    public MdCommentList(Review holdingReview) {
        super(holdingReview);
    }

    public static class MdComment implements MdData, DataComment {
        private final String mComment;
        private       Review mHoldingReview;

        public MdComment(String comment, Review holdingReview) {
            mComment = comment;
            mHoldingReview = holdingReview;
        }

        @Override
        public Review getHoldingReview() {
            return mHoldingReview;
        }

        @Override
        public boolean hasData() {
            return mComment != null && mComment.length() > 0;
        }

        @Override
        public String getComment() {
            return mComment;
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
            if (mHoldingReview != null ? !mHoldingReview.equals(mdComment.mHoldingReview) :
                    mdComment.mHoldingReview != null) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            int result = mComment != null ? mComment.hashCode() : 0;
            result = 31 * result + (mHoldingReview != null ? mHoldingReview.hashCode() : 0);
            return result;
        }
    }
}
