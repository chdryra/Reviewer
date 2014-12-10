/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 17 October, 2014
 */

package com.chdryra.android.reviewer;

import android.os.Parcel;
import android.os.Parcelable;

import com.chdryra.android.mygenerallibrary.VHDDualString;

/**
 * Created by: Rizwan Choudrey
 * On: 17/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * Parcelable version of {@link com.chdryra.android.mygenerallibrary.VHDDualString} to comply with
 * {@link VgDataList.GvData}
 */
class VgDualText extends VHDDualString implements VgDataList.GvData {
    public static final Parcelable.Creator<VgDualText> CREATOR = new Parcelable
            .Creator<VgDualText>() {
        public VgDualText createFromParcel(Parcel in) {
            return new VgDualText(in);
        }

        public VgDualText[] newArray(int size) {
            return new VgDualText[size];
        }
    };

    VgDualText(String upper, String lower) {
        super(upper, lower);
    }

    VgDualText(Parcel in) {
        super(in.readString(), in.readString());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(getUpper());
        parcel.writeString(getLower());
    }
}
