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

import java.util.Comparator;

/**
 * Includes method for generating split comments {@link GvCommentList} from current list.
 */
public class GvCommentList extends GvDataList<GvCommentList.GvComment> {
    public static final GvDataType TYPE = new GvDataType("comment");

    public GvCommentList() {
        super(GvComment.class, TYPE);
    }

    public GvCommentList getSplitComments() {
        GvCommentList splitComments = new GvCommentList();
        for (GvComment comment : this) {
            splitComments.add(comment.getSplitComments());
        }

        return splitComments;
    }

    @Override
    public void add(GvComment item) {
        if (size() == 0) item.setIsHeadline(true);
        super.add(item);
    }

    @Override
    protected Comparator<GvComment> getDefaultComparator() {
        return new Comparator<GvComment>() {
            @Override
            public int compare(GvComment lhs, GvComment rhs) {
                int comp = 0;
                if (contains(lhs) && contains(rhs)) {
                    if (lhs.isHeadline() && !rhs.isHeadline()) {
                        comp = -1;
                    } else if (!lhs.isHeadline() && rhs.isHeadline()) {
                        comp = 1;
                    }
                }

                return comp;
            }
        };
    }

    public GvCommentList getHeadlines() {
        GvCommentList headlines = new GvCommentList();
        for (GvComment image : this) {
            if (image.isHeadline()) headlines.add(image);
        }

        return headlines;
    }

    /**
     * {@link GvData} version of: {@link com.chdryra
     * .android.reviewer.MdCommentList.MdComment}
     * {@link ViewHolder}: {@link VhComment}
     * <p>
     * Methods for getting the comment headline and for splitting and unsplitting comments.
     * </p>
     */
    public static class GvComment implements GvData, DataComment {
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
        private boolean mIsHeadline = false;

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

        public GvComment(String comment, boolean isHeadline) {
            mComment = comment;
            mIsHeadline = isHeadline;
        }

        private GvComment(Parcel in) {
            mComment = in.readString();
            mUnsplitParent = in.readParcelable(GvComment.class.getClassLoader());
            mIsHeadline = in.readByte() != 0;
        }

        @Override
        public ViewHolder newViewHolder() {
            return new VhComment();
        }

        @Override
        public boolean isValidForDisplay() {
            return DataValidator.validate(this);
        }

        @Override
        public String getStringSummary() {
            return getUnSplitComment().getCommentHeadline() + "...";
        }

        @Override
        public String getComment() {
            return mComment;
        }

        @Override
        public boolean isHeadline() {
            return mIsHeadline;
        }

        public void setIsHeadline(boolean isHeadline) {
            mIsHeadline = isHeadline;
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
            result = 31 * result + (mIsHeadline ? 1 : 0);
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
            parcel.writeByte((byte) (mIsHeadline ? 1 : 0));
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
