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
 * Includes method for generating split comments {@link GvCommentList} from current list.
 */
public class GvCommentList extends GvDataList<GvCommentList.GvComment> {
    public static final GvType TYPE = GvType.COMMENTS;

    public GvCommentList() {
        super(TYPE);
    }

    public GvCommentList getSplitComments() {
        GvCommentList splitComments = new GvCommentList();
        for (GvComment comment : this) {
            splitComments.add(comment.getSplitComments());
        }

        return splitComments;
    }

    /**
     * {@link GvDataList.GvData} version of: {@link com.chdryra
     * .android.reviewer.MdCommentList.MdComment}
     * {@link ViewHolder}: {@link VHComment}
     * <p>
     * Methods for getting the comment headline and for splitting and unsplitting comments.
     * </p>
     */
    public static class GvComment implements GvDataList.GvData, DataComment {
        public static final Parcelable.Creator<GvComment> CREATOR = new Parcelable
                .Creator<GvComment>() {
            public GvComment createFromParcel(Parcel in) {
                return new GvComment(in);
            }

            public GvComment[] newArray(int size) {
                return new GvComment[size];
            }
        };
        private final String    mComment;
        private       GvComment mUnsplitParent;

        private GvComment(String comment, GvComment unsplitParent) {
            mComment = comment;
            mUnsplitParent = unsplitParent;
        }

        public GvComment() {
            mComment = null;
        }

        public GvComment(String comment) {
            mComment = comment;
        }

        private GvComment(Parcel in) {
            mComment = in.readString();
            mUnsplitParent = in.readParcelable(GvComment.class.getClassLoader());
        }

        @Override
        public ViewHolder newViewHolder() {
            return new VHComment();
        }

        @Override
        public boolean isValidForDisplay() {
            return DataValidator.validate(this);
        }

        @Override
        public String getComment() {
            return mComment;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GvComment)) return false;

            GvComment gvComment = (GvComment) o;

            return !(mComment != null ? !mComment.equals(gvComment.mComment) : gvComment.mComment
                    != null) && !
                    (mUnsplitParent != null ? !mUnsplitParent.equals(gvComment
                            .mUnsplitParent) : gvComment.mUnsplitParent != null);

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

        public String getCommentHeadline() {
            return CommentFormatter.getHeadline(mComment);
        }

        public GvComment getUnSplitComment() {
            return mUnsplitParent != null ? mUnsplitParent.getUnSplitComment() : this;
        }

        private GvCommentList getSplitComments() {
            GvCommentList splitComments = new GvCommentList();
            for (String comment : CommentFormatter.split(mComment)) {
                splitComments.add(new GvComment(comment, this));
            }

            return splitComments;
        }
    }
}
