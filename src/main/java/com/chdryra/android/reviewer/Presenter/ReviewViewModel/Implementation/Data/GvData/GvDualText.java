/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.Viewholder.VHDDualString;
import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolder;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;

/**
 * Created by: Rizwan Choudrey
 * On: 17/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * Parcelable version of {@link VHDDualString} to comply with
 * {@link GvData}
 */
public class GvDualText extends VHDDualString implements GvDataParcelable {
    public static final Parcelable.Creator<GvDualText> CREATOR = new Parcelable
            .Creator<GvDualText>() {
        @Override
        public GvDualText createFromParcel(Parcel in) {
            return new GvDualText(in);
        }

        @Override
        public GvDualText[] newArray(int size) {
            return new GvDualText[size];
        }
    };
    private static final GvDataType<GvDualText> TYPE
            = new GvDataType<>(GvDualText.class, "DualText");
    private GvReviewId mId;

    GvDualText() {
        super("", "");
    }

    public GvDualText(String upper, String lower) {
        super(upper, lower);
    }

    GvDualText(@Nullable GvReviewId id, String upper, String lower) {
        super(upper, lower);
        mId = id;
    }

    GvDualText(Parcel in) {
        super(in.readString(), in.readString());
        mId = in.readParcelable(GvReviewId.class.getClassLoader());
    }

    @Override
    public GvReviewId getGvReviewId() {
        return mId;
    }

    @Override
    public GvDataType<? extends GvDualText> getGvDataType() {
        return TYPE;
    }

    @Override
    public String getStringSummary() {
        return "Upper: " + getUpper() + ", Lower: " + getLower();
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
    public boolean isVerboseCollection() {
        return false;
    }

    @Override
    public ViewHolder getViewHolder() {
        return super.getViewHolder();
    }

    @Override
    public boolean hasData(DataValidator dataValidator) {
        return dataValidator.validateString(getUpper()) && dataValidator.validateString(getLower());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(getUpper());
        parcel.writeString(getLower());
        parcel.writeParcelable(mId, i);
    }

    @Nullable
    @Override
    public GvDataParcelable getParcelable() {
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GvDualText)) return false;
        if (!super.equals(o)) return false;

        GvDualText that = (GvDualText) o;

        return !(mId != null ? !mId.equals(that.mId) : that.mId != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (mId != null ? mId.hashCode() : 0);
        return result;
    }
}
