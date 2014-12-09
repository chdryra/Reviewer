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
 * {@link GVReviewDataList.GvData}
 */
class GVText extends VHDString implements GVReviewDataList.GvData {
    public static final Parcelable.Creator<GVText> CREATOR = new Parcelable
            .Creator<GVText>() {
        public GVText createFromParcel(Parcel in) {
            return new GVText(in);
        }

        public GVText[] newArray(int size) {
            return new GVText[size];
        }
    };

    GVText() {
        super();
    }

    GVText(String text) {
        super(text);
    }

    GVText(Parcel in) {
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
