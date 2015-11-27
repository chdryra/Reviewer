/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 17 October, 2014
 */

package com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data;

import android.os.Parcel;
import android.os.Parcelable;

import com.chdryra.android.mygenerallibrary.VHDString;
import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DataValidator;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.ViewHolders.VhText;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Interfaces.GvData;

import org.jetbrains.annotations.NotNull;

/**
 * Created by: Rizwan Choudrey
 * On: 17/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * Parcelable version of {@link com.chdryra.android.mygenerallibrary.VHDString} to comply with
 * {@link GvData}
 */
public class GvText<T extends GvText> extends VHDString implements GvData {
    public static final GvDataType<GvText> TYPE = new GvDataType<>(GvText.class, "text");
    public static final Parcelable.Creator<GvText> CREATOR = new Parcelable
            .Creator<GvText>() {
        //Overridden
        public GvText createFromParcel(Parcel in) {
            return new GvText(in);
        }

        public GvText[] newArray(int size) {
            return new GvText[size];
        }
    };

    private GvDataType<T> mType;
    private GvReviewId mId;

    //Constructors
    public GvText(@NotNull GvDataType<T> type) {
        super();
        mType = type;
    }

    public GvText(@NotNull GvDataType<T> type, String text) {
        super(text);
        mType = type;
    }

    public GvText(GvDataType<T> type, GvReviewId id, String text) {
        super(text);
        mId = id;
        mType = type;
    }

    public GvText(Parcel in) {
        super(in.readString());
        mType = in.readParcelable(GvDataType.class.getClassLoader());
        mId = in.readParcelable(GvReviewId.class.getClassLoader());
    }

    public GvReviewId getGvReviewId() {
        return mId;
    }

    //Overridden
    @Override
    public GvDataType<T> getGvDataType() {
        return mType;
    }

    @Override
    public String getStringSummary() {
        return getString();
    }

    @Override
    public String getReviewId() {
        return mId.toString();
    }

    @Override
    public boolean hasElements() {
        return false;
    }

    @Override
    public boolean isVerboseCollection() {
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
