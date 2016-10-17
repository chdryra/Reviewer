/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData;

import android.os.Parcel;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolder;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataConverter;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefComment;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.Utils
        .CommentFormatter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .VhComment;

/**
 * {@link GvData} version of: {@link com.chdryra
 * .android.reviewer.MdCommentList.MdComment}
 * {@link ViewHolder}: {@link VhComment}
 * <p>
 * Methods for getting the comment headline and for splitting and unsplitting comments.
 * </p>
 */
public class GvComment extends GvDataParcelableBasic<GvComment> implements DataComment {
    public static final GvDataType<GvComment> TYPE =
            new GvDataType<>(GvComment.class, "comment");
    public static final Creator<GvComment> CREATOR = new Creator<GvComment>() {
        @Override
        public GvComment createFromParcel(Parcel in) {
            return new GvComment(in);
        }

        @Override
        public GvComment[] newArray(int size) {
            return new GvComment[size];
        }
    };

    private final String mComment;
    private GvComment mUnsplitParent = null;
    private boolean mIsHeadline = false;

    public GvComment() {
        this(null, "", false);
    }

    public GvComment(String comment, boolean isHeadline) {
        this(null, comment, isHeadline);
    }

    public GvComment(@Nullable GvReviewId id, String comment, boolean isHeadline) {
        super(TYPE, id);
        mComment = comment;
        mIsHeadline = isHeadline;
    }

    //Copy constructor
    public GvComment(GvComment comment) {
        this(comment.getGvReviewId(), comment.getComment(), comment.isHeadline());
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
    public String getFirstSentence() {
        return CommentFormatter.getFirstSentence(mComment);
    }

    public GvComment getUnsplitComment() {
        return mUnsplitParent != null ? mUnsplitParent.getUnsplitComment() : this;
    }

    public GvCommentList getSplitComments() {
        GvCommentList splitComments = new GvCommentList(getGvReviewId());
        for (String comment : CommentFormatter.split(mComment)) {
            splitComments.add(new GvComment(getGvReviewId(), comment, this));
        }

        return splitComments;
    }

    public String getFormattedComment() {
        return CommentFormatter.format(getComment());
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
        return mComment != null && mComment.length() > 0;
    }

    @Override
    public boolean hasData(DataValidator dataValidator) {
        return dataValidator.validate(this);
    }

    @Override
    public String getStringSummary() {
        return getUnsplitComment().getFirstSentence();
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

    public static class Reference extends GvDataRef<Reference, DataComment, VhComment> {
        public static final GvDataType<GvComment.Reference> TYPE
                = new GvDataType<>(GvComment.Reference.class, GvComment.TYPE);

        private GvComment mParcelable;

        public Reference(RefComment reference,
                         DataConverter<DataComment, GvComment, ?> converter) {
            super(TYPE, reference, converter, VhComment.class, new
                    PlaceHolderFactory<DataComment>() {
                @Override
                public DataComment newPlaceHolder(String placeHolder) {
                    return new GvComment(placeHolder, false);
                }
            });
        }

        public Reference getFullCommentReference() {
            RefComment reference = (RefComment) super.getReference();
            RefComment parent = reference.getParent();
            if (parent == null) {
                return this;
            } else {
                //TODO make type safe
                return new Reference(parent, (DataConverter<DataComment, GvComment, ?>)
                        getConverter());
            }
        }

        @Nullable
        @Override
        public GvDataParcelable getParcelable() {
            return mParcelable != null ? mParcelable : super.getParcelable();
        }

        public void setParcelable(GvComment parcelable) {
            mParcelable = parcelable;
        }
    }
}
