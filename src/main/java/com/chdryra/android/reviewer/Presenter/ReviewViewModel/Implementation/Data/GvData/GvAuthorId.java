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
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataConverter;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.NamedAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepository;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.VhAuthorId;


import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .VhDataRef;

/**
 * Created by: Rizwan Choudrey
 * On: 25/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvAuthorId implements GvDataParcelable, DataAuthorId {
    public static final GvDataType<GvAuthorId> TYPE =
            new GvDataType<>(GvAuthorId.class, "author");

    public static final Creator<GvAuthorId> CREATOR = new Creator<GvAuthorId>() {
        @Override
        public GvAuthorId createFromParcel(Parcel in) {
            return new GvAuthorId(in);
        }

        @Override
        public GvAuthorId[] newArray(int size) {
            return new GvAuthorId[size];
        }
    };

    private DataReference<NamedAuthor> mReference;
    private GvReviewId mReviewId;
    private final String mAuthorId;

    public GvAuthorId(String authorId) {
        mAuthorId = authorId;
    }

    public GvAuthorId(String authorId, AuthorsRepository repo) {
        this(null, authorId, repo);
    }

    public GvAuthorId(@Nullable GvReviewId reviewId, String authorId, AuthorsRepository repo) {
        mReviewId = reviewId;
        mAuthorId = authorId;
        mReference = repo.getName(this);
    }

    public GvAuthorId(Parcel in) {
        mAuthorId = in.readString();
        mReviewId = in.readParcelable(GvReviewId.class.getClassLoader());
    }

    @Nullable
    public DataReference<NamedAuthor> getReference() {
        return mReference;
    }

    @Override
    public GvReviewId getGvReviewId() {
        return mReviewId;
    }

    @Override
    public GvDataType<GvAuthorId> getGvDataType() {
        return TYPE;
    }

    @Override
    public ReviewId getReviewId() {
        return mReviewId;
    }

    @Override
    public boolean hasElements() {
        return false;
    }

    @Override
    public boolean isCollection() {
        return false;
    }

    @Override
    public ViewHolder getViewHolder() {
        return new VhAuthorId();
    }

    @Override
    public boolean isValidForDisplay() {
        return mAuthorId != null;
    }

    @Override
    public boolean hasData(DataValidator dataValidator) {
        return dataValidator.validateString(mAuthorId) && isValidForDisplay();
    }

    @Override
    public GvAuthorId getParcelable() {
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GvAuthorId)) return false;

        GvAuthorId that = (GvAuthorId) o;

        if (mReference != null ? !mReference.equals(that.mReference) : that.mReference != null)
            return false;
        if (mReviewId != null ? !mReviewId.equals(that.mReviewId) : that.mReviewId != null)
            return false;
        return mAuthorId.equals(that.mAuthorId);

    }

    @Override
    public int hashCode() {
        int result = mReference != null ? mReference.hashCode() : 0;
        result = 31 * result + (mReviewId != null ? mReviewId.hashCode() : 0);
        result = 31 * result + mAuthorId.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return mAuthorId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mAuthorId);
        parcel.writeParcelable(mReviewId, i);
    }

    public static class Reference extends GvDataRef<Reference, DataAuthorId, VhAuthorId> {
        public static final GvDataType<GvAuthorId.Reference> TYPE
                = new GvDataType<>(GvAuthorId.Reference.class, GvAuthorId.TYPE);

        public Reference(ReviewItemReference<DataAuthorId> reference,
                         DataConverter<DataAuthorId, GvAuthorId, ?> converter) {
            super(TYPE, reference, converter, VhAuthorId.class, new PlaceHolderFactory<DataAuthorId>() {
                @Override
                public DataAuthorId newPlaceHolder(String placeHolder) {
                    return new GvAuthorId(placeHolder);
                }
            });
        }

        @Nullable
        public NamedAuthor getNamedAuthor() {
            VhDataRef refHolder = (VhDataRef) getReferenceViewHolder();
            if(refHolder == null) return null;
            VhAuthorId valueHolder = (VhAuthorId) refHolder.getValueHolder();
            if(valueHolder == null) return null;
            return valueHolder.getAuthor();
        }
    }
}
