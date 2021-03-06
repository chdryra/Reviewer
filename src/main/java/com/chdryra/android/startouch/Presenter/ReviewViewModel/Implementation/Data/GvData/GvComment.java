/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData;

import android.os.Parcel;
import android.support.annotation.Nullable;

import com.chdryra.android.corelibrary.Viewholder.ViewHolder;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.StringParser;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataConverter;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.CommentRef;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.Utils
        .DataFormatter;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
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
            new GvDataType<>(GvComment.class, TYPE_NAME);
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
        return DataFormatter.getFirstSentence(this);
    }

    public GvComment getUnsplitComment() {
        return mUnsplitParent != null ? mUnsplitParent.getUnsplitComment() : this;
    }

    public GvCommentList getSplitComments() {
        GvCommentList splitComments = new GvCommentList(getGvReviewId());
        for (String comment : DataFormatter.split(this)) {
            splitComments.add(new GvComment(getGvReviewId(), comment, this));
        }

        return splitComments;
    }

    public String getFormattedComment() {
        return DataFormatter.format(this);
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
    public String getComment() {
        return mComment;
    }

    @Override
    public boolean isHeadline() {
        return mIsHeadline;
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

    @Override
    public String toString() {
        return StringParser.parse(this);
    }

    public static class Reference extends GvDataRef<Reference, DataComment, VhComment> {
        public static final GvDataType<GvComment.Reference> TYPE
                = new GvDataType<>(GvComment.Reference.class, GvComment.TYPE);

        private GvComment mParcelable;

        public Reference(CommentRef reference,
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
            CommentRef reference = (CommentRef) super.getReference();
            CommentRef parent = reference.getParent();
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
