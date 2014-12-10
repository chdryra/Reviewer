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
 * {@link GvDataList.GvData}
 */
class GvText extends VHDString implements GvDataList.GvData {
    public static final Parcelable.Creator<GvText> CREATOR = new Parcelable
            .Creator<GvText>() {
        public GvText createFromParcel(Parcel in) {
            return new GvText(in);
        }

        public GvText[] newArray(int size) {
            return new GvText[size];
        }
    };

    GvText() {
        super();
    }

    GvText(String text) {
        super(text);
    }

    GvText(Parcel in) {
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
