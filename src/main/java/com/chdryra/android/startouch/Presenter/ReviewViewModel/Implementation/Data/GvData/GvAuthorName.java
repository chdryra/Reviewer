/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.chdryra.android.corelibrary.Viewholder.ViewHolder;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.AuthorNameDefault;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.StringParser;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorName;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataAuthorName;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataConverter;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .VhAuthor;

/**
 * {@link } version of: {@link AuthorNameDefault}
 * {@link ViewHolder}: {@link VhAuthor}
 * <p/>
 * <p>
 * Ignores case when comparing authors.
 * </p>
 */
public class GvAuthorName extends GvDataParcelableBasic<GvAuthorName> implements DataAuthorName {
    public static final GvDataType<GvAuthorName> TYPE =
            new GvDataType<>(GvAuthorName.class, AuthorName.TYPE_NAME);
    public static final Parcelable.Creator<GvAuthorName> CREATOR = new Parcelable
            .Creator<GvAuthorName>() {
        @Override
        public GvAuthorName createFromParcel(Parcel in) {
            return new GvAuthorName(in);
        }

        @Override
        public GvAuthorName[] newArray(int size) {
            return new GvAuthorName[size];
        }
    };

    private final String mName;
    private final GvAuthorId mAuthorId;

    public GvAuthorName(String name, GvAuthorId authorId) {
        this(null, name, authorId);
    }

    public GvAuthorName(@Nullable GvReviewId id, String name, GvAuthorId authorId) {
        super(GvAuthorName.TYPE, id);
        mName = name;
        mAuthorId = authorId;
    }

    private GvAuthorName() {
        this(null, "", new GvAuthorId(""));
    }

    private GvAuthorName(GvAuthorName author) {
        this(author.getGvReviewId(), author.getName(), (GvAuthorId) author.getAuthorId());
    }

    private GvAuthorName(Parcel in) {
        super(in);
        mName = in.readString();
        mAuthorId = in.readParcelable(GvAuthorId.class.getClassLoader());
    }

    @Override
    public String getName() {
        return mName;
    }

    @Override
    public AuthorId getAuthorId() {
        return mAuthorId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(mName);
        parcel.writeParcelable(mAuthorId, i);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GvAuthorName)) return false;
        if (!super.equals(o)) return false;

        GvAuthorName gvAuthor = (GvAuthorName) o;

        if (mName != null ? !mName.equals(gvAuthor.mName) : gvAuthor.mName != null)
            return false;
        return !(mAuthorId != null ? !mAuthorId.equals(gvAuthor.mAuthorId) : gvAuthor.mAuthorId !=
                null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (mName != null ? mName.hashCode() : 0);
        result = 31 * result + (mAuthorId != null ? mAuthorId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return StringParser.parse((AuthorName) this);
    }

    @Override
    public ViewHolder getViewHolder() {
        return new VhAuthor();
    }

    @Override
    public boolean isValidForDisplay() {
        return mName != null && mName.length() > 0;
    }

    @Override
    public boolean hasData(DataValidator dataValidator) {
        return dataValidator.validate((AuthorName) this);
    }

    public static class Reference extends GvDataRef<Reference, DataAuthorName, VhAuthor> {
        public static final GvDataType<Reference> TYPE
                = new GvDataType<>(GvAuthorName.Reference.class, GvAuthorName.TYPE);

        public Reference(ReviewItemReference<DataAuthorName> reference,
                         DataConverter<DataAuthorName, GvAuthorName, ?> converter) {
            super(TYPE, reference, converter, VhAuthor.class, new
                    PlaceHolderFactory<DataAuthorName>() {
                @Override
                public DataAuthorName newPlaceHolder(String placeHolder) {
                    return new GvAuthorName(placeHolder, new GvAuthorId(AuthorId.NULL_ID_STRING));
                }
            });
        }
    }
}
