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
 * {@link com.chdryra.android.reviewer.GVReviewDataList.GVReviewData}
 */
class GVDualText extends VHDDualString implements GVReviewDataList.GVReviewData {
    public static final Parcelable.Creator<GVDualText> CREATOR = new Parcelable
            .Creator<GVDualText>() {
        public GVDualText createFromParcel(Parcel in) {
            return new GVDualText(in);
        }

        public GVDualText[] newArray(int size) {
            return new GVDualText[size];
        }
    };

    GVDualText(String upper, String lower) {
        super(upper, lower);
    }

    GVDualText(Parcel in) {
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
