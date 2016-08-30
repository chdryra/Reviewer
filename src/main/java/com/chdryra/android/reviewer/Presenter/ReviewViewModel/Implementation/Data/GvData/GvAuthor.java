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
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DefaultNamedAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataConverter;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.NamedAuthor;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.VhAuthor;

/**
 * {@link } version of: {@link DefaultNamedAuthor}
 * {@link ViewHolder}: {@link VhAuthor}
 * <p/>
 * <p>
 * Ignores case when comparing authors.
 * </p>
 */
public class GvAuthor extends GvDataParcelableBasic<GvAuthor> implements DataAuthor {
    public static final GvDataType<GvAuthor> TYPE =
            new GvDataType<>(GvAuthor.class, "author");
    public static final Creator<GvAuthor> CREATOR = new Creator<GvAuthor>() {
        @Override
        public GvAuthor createFromParcel(Parcel in) {
            return new GvAuthor(in);
        }

        @Override
        public GvAuthor[] newArray(int size) {
            return new GvAuthor[size];
        }
    };

    private String mName;
    private GvAuthorId mUserId;

    public GvAuthor() {
        this(null, null, null);
    }

    public GvAuthor(String name, GvAuthorId userId) {
        this(null, name, userId);
    }

    public GvAuthor(@Nullable GvReviewId id, String name, GvAuthorId userId) {
        super(GvAuthor.TYPE, id);
        mName = name;
        mUserId = userId;
    }

    public GvAuthor(GvAuthor author) {
        this(author.getGvReviewId(), author.getName(), (GvAuthorId) author.getAuthorId());
    }

    GvAuthor(Parcel in) {
        super(in);
        mName = in.readString();
        mUserId = in.readParcelable(GvAuthorId.class.getClassLoader());
    }

    @Override
    public String getName() {
        return mName;
    }

    @Override
    public AuthorId getAuthorId() {
        return mUserId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(mName);
        parcel.writeParcelable(mUserId, i);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GvAuthor)) return false;
        if (!super.equals(o)) return false;

        GvAuthor gvAuthor = (GvAuthor) o;

        if (mName != null ? !mName.equals(gvAuthor.mName) : gvAuthor.mName != null)
            return false;
        return !(mUserId != null ? !mUserId.equals(gvAuthor.mUserId) : gvAuthor.mUserId !=
                null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (mName != null ? mName.hashCode() : 0);
        result = 31 * result + (mUserId != null ? mUserId.hashCode() : 0);
        return result;
    }

    @Override
    public String getStringSummary() {
        return mName;
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
        return dataValidator.validate((NamedAuthor) this);
    }

    public static class Reference extends GvDataRef<Reference, DataAuthor, VhAuthor> {
        public static final GvDataType<GvAuthor.Reference> TYPE
                = new GvDataType<>(GvAuthor.Reference.class, GvAuthor.TYPE);

        public Reference(ReviewItemReference<DataAuthor> reference,
                         DataConverter<DataAuthor, GvAuthor, ?> converter) {
            super(TYPE, reference, converter, VhAuthor.class, new PlaceHolderFactory<DataAuthor>() {
                @Override
                public DataAuthor newPlaceHolder(String placeHolder) {
                    return new GvAuthor(placeHolder, new GvAuthorId(AuthorId.NULL_ID_STRING));
                }
            });
        }
    }
}
