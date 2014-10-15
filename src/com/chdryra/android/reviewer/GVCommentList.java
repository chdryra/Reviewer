/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import com.chdryra.android.mygenerallibrary.GVData;
import com.chdryra.android.mygenerallibrary.ViewHolder;

/**
 * GVReviewDataList: GVComment
 * <p>
 * ViewHolder: VHCommentView
 * </p>
 * <p>
 * Includes method for generating split comments GVCommentList from current list.
 * </p>
 *
 * @see com.chdryra.android.reviewer.FragmentReviewComments
 * @see com.chdryra.android.reviewer.VHCommentView
 */
class GVCommentList extends GVReviewDataList<GVCommentList.GVComment> {

    GVCommentList() {
        super(GVType.COMMENTS);
    }

    void add(String comment) {
        add(new GVComment(comment));
    }

    void remove(String comment) {
        remove(new GVComment(comment));
    }

    boolean contains(String comment) {
        return contains(new GVComment(comment));
    }

    GVCommentList getSplitComments() {
        GVCommentList splitComments = new GVCommentList();
        for (GVComment comment : this) {
            splitComments.add(comment.getSplitComments());
        }

        return splitComments;
    }

    /**
     * GVData version of: RDComment
     * ViewHolder: VHCommentView
     * <p>
     * Methods for getting the comment headline and for splitting and unsplitting comments.
     * </p>
     *
     * @see com.chdryra.android.mygenerallibrary.GVData
     * @see com.chdryra.android.reviewer.RDComment
     * @see com.chdryra.android.reviewer.VHCommentView
     */
    class GVComment implements GVData {
        private final String    mComment;
        private       GVComment mUnsplitParent;

        private GVComment(String comment, GVComment unsplitParent) {
            mComment = comment;
            mUnsplitParent = unsplitParent;
        }

        GVComment(String comment) {
            mComment = comment;
        }

        String getComment() {
            return mComment;
        }

        GVCommentList getSplitComments() {
            GVCommentList splitComments = new GVCommentList();
            for (String comment : CommentFormatter.split(mComment)) {
                splitComments.add(new GVComment(comment, this));
            }

            return splitComments;
        }

        GVComment getUnSplitComment() {
            if (mUnsplitParent != null) {
                return mUnsplitParent.getUnSplitComment();
            } else {
                return this;
            }
        }

        String getCommentHeadline() {
            return CommentFormatter.getHeadline(mComment);
        }

        @Override
        public ViewHolder getViewHolder() {
            return new VHCommentView();
        }

        @Override
        public boolean isValidForDisplay() {
            return mComment != null && mComment.length() > 0;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            GVComment other = (GVComment) obj;
            if (!getOuterType().equals(other.getOuterType())) {
                return false;
            }
            if (mComment == null) {
                if (other.mComment != null) {
                    return false;
                }
            } else if (!mComment.equals(other.mComment)) {
                return false;
            }
            if (mUnsplitParent == null) {
                if (other.mUnsplitParent != null) {
                    return false;
                }
            } else if (!mUnsplitParent.equals(other.mUnsplitParent)) {
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + getOuterType().hashCode();
            result = prime * result
                    + ((mComment == null) ? 0 : mComment.hashCode());
            result = prime
                    * result
                    + ((mUnsplitParent == null) ? 0 : mUnsplitParent.hashCode());
            return result;
        }

        private GVCommentList getOuterType() {
            return GVCommentList.this;
        }
    }
}
