/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 6 December, 2014
 */

package com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataComment;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DataValidator;

/**
 * Created by: Rizwan Choudrey
 * On: 06/12/2014
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * Review Data: list for {@link MdCommentList.MdComment}
 */

public class MdCommentList extends MdDataList<MdCommentList.MdComment> {

    //Constructors
    public MdCommentList(MdReviewId reviewId) {
        super(reviewId);
    }

//Classes

    /**
     * Review Data: comment
     */
    public static class MdComment implements MdData, DataComment {
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
        public String getReviewId() {
            return mReviewId.toString();
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
}
