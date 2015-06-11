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
import com.chdryra.android.reviewer.Controller.DataAdapterModel.DataComment;
import com.chdryra.android.reviewer.Controller.DataAdapterModel.DataValidator;
import com.chdryra.android.reviewer.View.Utils.CommentFormatter;

import java.util.Comparator;

/**
 * Includes method for generating split comments {@link GvCommentList} from current list.
 */
public class GvCommentList extends GvDataList<GvCommentList.GvComment> {
    public static final GvDataType TYPE = new GvDataType("comment");
    public static final Class<GvComment> DATA_CLASS = GvComment.class;

    public GvCommentList() {
        super(null, DATA_CLASS, TYPE);
    }

    public GvCommentList(GvReviewId id) {
        super(id, DATA_CLASS, TYPE);
    }

    public GvCommentList(GvCommentList data) {
        super(data);
    }

    public GvCommentList getSplitComments() {
        GvCommentList splitComments = new GvCommentList(getReviewId());
        for (GvComment comment : this) {
            splitComments.addList(comment.getSplitComments());
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
        GvCommentList headlines = new GvCommentList(getReviewId());
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
    public static class GvComment extends GvDataBasic implements DataComment {
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

        private GvComment(GvReviewId id, String comment, GvComment unsplitParent) {
            super(id);
            mComment = comment;
            mUnsplitParent = unsplitParent;
        }

        public GvComment() {
            mComment = null;
        }

        public GvComment(String comment) {
            mComment = comment;
        }

        public GvComment(GvReviewId id, String comment) {
            super(id);
            mComment = comment;
        }

        public GvComment(String comment, boolean isHeadline) {
            mComment = comment;
            mIsHeadline = isHeadline;
        }

        public GvComment(GvReviewId id, String comment, boolean isHeadline) {
            super(id);
            mComment = comment;
            mIsHeadline = isHeadline;
        }

        //Copy constructor
        public GvComment(GvComment comment) {
            this(comment.getReviewId(), comment.getComment(), comment.isHeadline());
        }

        //Parcel constructor
        private GvComment(Parcel in) {
            super(in);
            mComment = in.readString();
            mUnsplitParent = in.readParcelable(GvComment.class.getClassLoader());
            mIsHeadline = in.readByte() != 0;
        }

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
            return getUnsplitComment().getHeadline() + "...";
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
            super.writeToParcel(parcel, i);
            parcel.writeString(mComment);
            parcel.writeParcelable(mUnsplitParent, i);
            parcel.writeByte((byte) (mIsHeadline ? 1 : 0));
        }

        public String getHeadline() {
            return CommentFormatter.getHeadline(mComment);
        }

        public GvComment getUnsplitComment() {
            return mUnsplitParent != null ? mUnsplitParent.getUnsplitComment() : this;
        }

        private GvCommentList getSplitComments() {
            GvCommentList splitComments = new GvCommentList(getReviewId());
            for (String comment : CommentFormatter.split(mComment)) {
                splitComments.add(new GvComment(getReviewId(), comment, this));
            }

            return splitComments;
        }
    }
}
