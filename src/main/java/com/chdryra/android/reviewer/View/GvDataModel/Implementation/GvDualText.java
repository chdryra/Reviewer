/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 17 October, 2014
 */

package com.chdryra.android.reviewer.View.GvDataModel.Implementation;

import android.os.Parcel;
import android.os.Parcelable;

import com.chdryra.android.mygenerallibrary.VHDDualString;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DataValidator;
import com.chdryra.android.reviewer.View.GvDataModel.Interfaces.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 17/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * Parcelable version of {@link com.chdryra.android.mygenerallibrary.VHDDualString} to comply with
 * {@link GvData}
 */
public class GvDualText extends VHDDualString implements GvData {
    public static final Parcelable.Creator<GvDualText> CREATOR = new Parcelable
            .Creator<GvDualText>() {
        //Overridden
        public GvDualText createFromParcel(Parcel in) {
            return new GvDualText(in);
        }

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

    GvDualText(String upper, String lower) {
        super(upper, lower);
    }

    GvDualText(GvReviewId id, String upper, String lower) {
        super(upper, lower);
        mId = id;
    }

    GvDualText(Parcel in) {
        super(in.readString(), in.readString());
        mId = in.readParcelable(GvReviewId.class.getClassLoader());
    }

    //Overridden
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
