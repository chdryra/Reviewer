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

import com.chdryra.android.mygenerallibrary.VHDString;

/**
 * Created by: Rizwan Choudrey
 * On: 17/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * Parcelable version of {@link com.chdryra.android.mygenerallibrary.VHDString} to comply with
 * {@link VgDataList.GvData}
 */
class VgText extends VHDString implements VgDataList.GvData {
    public static final Parcelable.Creator<VgText> CREATOR = new Parcelable
            .Creator<VgText>() {
        public VgText createFromParcel(Parcel in) {
            return new VgText(in);
        }

        public VgText[] newArray(int size) {
            return new VgText[size];
        }
    };

    VgText() {
        super();
    }

    VgText(String text) {
        super(text);
    }

    VgText(Parcel in) {
        super(in.readString());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(get());
    }
}
