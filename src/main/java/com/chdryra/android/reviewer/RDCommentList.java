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
public class RDCommentList extends RDList<RDCommentList.RDComment> {
    /**
     * Review Data: comment
     * <p>
     * {@link #hasData()}: A string at least 1 character in length.
     * </p>
     */
    public static class RDComment implements RData {
        private final String mComment;
        private       Review mHoldingReview;

        public RDComment(String comment, Review holdingReview) {
            mComment = comment;
            mHoldingReview = holdingReview;
        }

        @Override
        public Review getHoldingReview() {
            return mHoldingReview;
        }

        @Override
        public void setHoldingReview(Review review) {
            mHoldingReview = review;
        }

        @Override
        public boolean hasData() {
            return mComment != null && mComment.length() > 0;
        }

        public String getComment() {
            return mComment;
        }
    }
}
