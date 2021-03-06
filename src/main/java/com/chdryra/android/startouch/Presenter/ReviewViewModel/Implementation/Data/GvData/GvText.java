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

import com.chdryra.android.corelibrary.Viewholder.VHDString;
import com.chdryra.android.corelibrary.Viewholder.ViewHolder;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .VhText;

import org.jetbrains.annotations.NotNull;

/**
 * Created by: Rizwan Choudrey
 * On: 17/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * Parcelable version of {@link VHDString} to comply with
 * {@link GvData}
 */
public class GvText<T extends GvText> extends VHDString implements GvDataParcelable {
    public static final GvDataType<GvText> TYPE = new GvDataType<>(GvText.class, "text");
    public static final Parcelable.Creator<GvText> CREATOR = new Parcelable
            .Creator<GvText>() {
        @Override
        public GvText createFromParcel(Parcel in) {
            return new GvText(in);
        }

        @Override
        public GvText[] newArray(int size) {
            return new GvText[size];
        }
    };

    private final GvDataType<T> mType;
    private GvReviewId mId;

    GvText(@NotNull GvDataType<T> type) {
        super();
        mType = type;
    }

    GvText(@NotNull GvDataType<T> type, String text) {
        super(text);
        mType = type;
    }

    GvText(GvDataType<T> type, @Nullable GvReviewId id, String text) {
        super(text);
        mId = id;
        mType = type;
    }

    GvText(Parcel in) {
        super(in.readString());
        mType = in.readParcelable(GvDataType.class.getClassLoader());
        mId = in.readParcelable(GvReviewId.class.getClassLoader());
    }

    @Override
    public GvReviewId getGvReviewId() {
        return mId;
    }

    @Override
    public GvDataType<T> getGvDataType() {
        return mType;
    }

    @Override
    public String toString() {
        return getString();
    }

    @Override
    public ReviewId getReviewId() {
        return mId;
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
        return new VhText();
    }

    @Override
    public boolean hasData(DataValidator dataValidator) {
        return dataValidator.validateString(getString());
    }

    @Nullable
    @Override
    public GvDataParcelable getParcelable() {
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GvText)) return false;
        if (!super.equals(o)) return false;

        GvText<?> gvText = (GvText<?>) o;

        if (!mType.equals(gvText.mType)) return false;
        return !(mId != null ? !mId.equals(gvText.mId) : gvText.mId != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + mType.hashCode();
        result = 31 * result + (mId != null ? mId.hashCode() : 0);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(getString());
        parcel.writeParcelable(mType, i);
        parcel.writeParcelable(mId, i);
    }
}
