/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.View.GvDataModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataComment;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataValidator;
import com.chdryra.android.reviewer.View.Utils.CommentFormatter;

/**
 * Includes method for generating split comments {@link GvCommentList} from current list.
 */
public class GvCommentList extends GvDataList<GvCommentList.GvComment> {
    //Constructors
    public GvCommentList() {
        super(GvComment.TYPE, null);
    }

    public GvCommentList(GvReviewId id) {
        super(GvComment.TYPE, id);
    }

    public GvCommentList(GvCommentList data) {
        super(data);
    }

    //public methods
    public GvCommentList getSplitComments() {
        GvCommentList splitComments = new GvCommentList(getReviewId());
        for (GvCommentList.GvComment comment : this) {
            splitComments.addList(comment.getSplitComments());
        }

        return splitComments;
    }

    public GvCommentList getHeadlines() {
        GvCommentList headlines = new GvCommentList(getReviewId());
        for (GvComment comment : this) {
            if (comment.isHeadline()) headlines.add(comment);
        }

        return headlines;
    }

//Classes

    /**
     * {@link GvData} version of: {@link com.chdryra
     * .android.reviewer.MdCommentList.MdComment}
     * {@link ViewHolder}: {@link VhComment}
     * <p>
     * Methods for getting the comment headline and for splitting and unsplitting comments.
     * </p>
     */
    public static class GvComment extends GvDataBasic<GvComment> implements DataComment {
        public static final GvDataType<GvComment> TYPE =
                new GvDataType<>(GvComment.class, "comment");
        public static final Parcelable.Creator<GvComment> CREATOR = new Parcelable
                .Creator<GvComment>() {
            //Overridden
            public GvComment createFromParcel(Parcel in) {
                return new GvComment(in);
            }

            public GvComment[] newArray(int size) {
                return new GvComment[size];
            }
        };

        private final String mComment;
        private GvComment mUnsplitParent = null;
        private boolean mIsHeadline = false;

        //Constructors
        public GvComment() {
            this(null, null, false);
        }

        public GvComment(String comment) {
            this(null, comment, false);
        }

        public GvComment(GvReviewId id, String comment) {
            this(id, comment, false);
        }

        public GvComment(String comment, boolean isHeadline) {
            this(null, comment, isHeadline);
        }

        public GvComment(GvReviewId id, String comment, boolean isHeadline) {
            super(TYPE, id);
            mComment = comment;
            mIsHeadline = isHeadline;
        }

        //Copy constructor
        public GvComment(GvComment comment) {
            this(comment.getReviewId(), comment.getComment(), comment.isHeadline());
        }

        private GvComment(GvReviewId id, String comment, GvComment unsplitParent) {
            super(GvComment.TYPE, id);
            mComment = comment;
            mUnsplitParent = unsplitParent;
        }

        //Parcel constructor
        private GvComment(Parcel in) {
            super(in);
            mComment = in.readString();
            mUnsplitParent = in.readParcelable(GvComment.class.getClassLoader());
            mIsHeadline = in.readByte() != 0;
        }

        //public methods
        public String getHeadline() {
            return CommentFormatter.getHeadline(mComment);
        }

        public GvComment getUnsplitComment() {
            return mUnsplitParent != null ? mUnsplitParent.getUnsplitComment() : this;
        }

        public GvCommentList getSplitComments() {
            GvCommentList splitComments = new GvCommentList(getReviewId());
            for (String comment : CommentFormatter.split(mComment)) {
                splitComments.add(new GvComment(getReviewId(), comment, this));
            }

            return splitComments;
        }

        public void setIsHeadline(boolean isHeadline) {
            mIsHeadline = isHeadline;
        }

        //Overridden
        @Override
        public ViewHolder getViewHolder() {
            return new VhComment();
        }

        @Override
        public boolean isValidForDisplay() {
            return DataValidator.validate(this);
        }

        @Override
        public String getStringSummary() {
            return getUnsplitComment().getHeadline();
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
        public String toString() {
            return getComment();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeString(mComment);
            parcel.writeParcelable(mUnsplitParent, i);
            parcel.writeByte((byte) (mIsHeadline ? 1 : 0));
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GvComment)) return false;
            if (!super.equals(o)) return false;

            GvComment gvComment = (GvComment) o;

            if (mIsHeadline != gvComment.mIsHeadline) return false;
            if (mComment != null ? !mComment.equals(gvComment.mComment) : gvComment.mComment !=
                    null)
                return false;
            return !(mUnsplitParent != null ? !mUnsplitParent.equals(gvComment.mUnsplitParent) :
                    gvComment.mUnsplitParent != null);

        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
            result = 31 * result + (mComment != null ? mComment.hashCode() : 0);
            result = 31 * result + (mUnsplitParent != null ? mUnsplitParent.hashCode() : 0);
            result = 31 * result + (mIsHeadline ? 1 : 0);
            return result;
        }
    }
}
