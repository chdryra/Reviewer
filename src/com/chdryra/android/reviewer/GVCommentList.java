/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.os.Parcel;
import android.os.Parcelable;

import com.chdryra.android.mygenerallibrary.ViewHolder;

/**
 * GVReviewDataList: GVComment
 * <p>
 * Includes method for generating split comments GVCommentList from current list.
 * </p>
 *
 * @see com.chdryra.android.reviewer.FragmentReviewComments
 */
class GVCommentList extends GVReviewDataList<GVCommentList.GVComment> {

    GVCommentList() {
        super(GVType.COMMENTS);
    }

    /**
     * GVReviewData version of: RDComment
     * ViewHolder: VHCommentView
     * <p>
     * Methods for getting the comment headline and for splitting and unsplitting comments.
     * </p>
     *
     * @see com.chdryra.android.reviewer.RDComment
     * @see VHComment
     */
    static class GVComment implements GVReviewDataList.GVReviewData {
        public static final Parcelable.Creator<GVComment> CREATOR = new Parcelable
                .Creator<GVComment>() {
            public GVComment createFromParcel(Parcel in) {
                return new GVComment(in);
            }

            public GVComment[] newArray(int size) {
                return new GVComment[size];
            }
        };
        private final String    mComment;
        private       GVComment mUnsplitParent;

        private GVComment(String comment, GVComment unsplitParent) {
            mComment = comment;
            mUnsplitParent = unsplitParent;
        }

        GVComment() {
            mComment = null;
        }

        GVComment(String comment) {
            mComment = comment;
        }

        GVComment(Parcel in) {
            mComment = in.readString();
            mUnsplitParent = in.readParcelable(GVComment.class.getClassLoader());
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
            return new VHComment();
        }

        @Override
        public boolean isValidForDisplay() {
            return mComment != null && mComment.length() > 0;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GVComment)) return false;

            GVComment gvComment = (GVComment) o;

            if (mComment != null ? !mComment.equals(gvComment.mComment) : gvComment.mComment !=
                    null) {
                return false;
            }
            if (mUnsplitParent != null ? !mUnsplitParent.equals(gvComment.mUnsplitParent) :
                    gvComment.mUnsplitParent != null) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            int result = mComment != null ? mComment.hashCode() : 0;
            result = 31 * result + (mUnsplitParent != null ? mUnsplitParent.hashCode() : 0);
            return result;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(mComment);
            parcel.writeParcelable(mUnsplitParent, i);
        }
    }

    void add(String comment) {
        add(new GVComment(comment));
    }

    GVCommentList getSplitComments() {
        GVCommentList splitComments = new GVCommentList();
        for (GVComment comment : this) {
            splitComments.add(comment.getSplitComments());
        }

        return splitComments;
    }
}
